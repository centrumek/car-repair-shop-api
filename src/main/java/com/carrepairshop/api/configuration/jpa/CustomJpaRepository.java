package com.carrepairshop.api.configuration.jpa;

import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CustomJpaRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

    <S extends T> S apply(S entity);
    void clear();
}
