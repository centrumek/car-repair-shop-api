package com.carrepairshop.api.configuration.jpa;

import java.io.Serializable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.search.jpa.Search;

@Slf4j
public class CustomJpaRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements CustomJpaRepository<T, ID> {

    private final EntityManager entityManager;

    public CustomJpaRepositoryImpl(final JpaEntityInformation entityInformation,
                                   final EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public <S extends T> S apply(S entity) {
        S result = saveAndFlush(entity);
        entityManager.refresh(result);
        entityManager.clear();
        return result;
    }

    @Override
    public <S extends T> Page<S> search(String terms, Pageable pageable, Class<S> clazz, String ... fields) {
        final var fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

        // TODO - status i order
        try {
            fullTextEntityManager.createIndexer().startAndWait();
        } catch (InterruptedException e) {
            log.warn("Problem with indexing", e);
        }

        final var queryBuilder = fullTextEntityManager.getSearchFactory()
                                                      .buildQueryBuilder()
                                                      .forEntity(clazz)
                                                      .get();

        final var luceneQuery = queryBuilder
            .keyword()
            .wildcard()
            .onFields(fields)
            .matching(terms + "*")
            .createQuery();

        final var jpaQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, clazz);

        jpaQuery.setMaxResults(pageable.getPageSize());
        jpaQuery.setFirstResult((int) pageable.getOffset());

        // execute search
        final var result = jpaQuery.getResultList();

        return new PageImpl<>(result, pageable, result.size());
    }

    @Override
    public void clear() {
        entityManager.clear();
    }
}
