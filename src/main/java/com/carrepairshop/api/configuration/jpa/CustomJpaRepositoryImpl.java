package com.carrepairshop.api.configuration.jpa;

import java.io.Serializable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

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
    public void clear() {
        entityManager.clear();
    }
}
