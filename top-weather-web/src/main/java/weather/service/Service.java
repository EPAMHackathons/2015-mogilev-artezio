package weather.service;

import weather.model.BaseEntity;

import java.util.List;

public interface Service<T extends BaseEntity> {

    T save(T entity);

    void delete(T entity);

    List<T> getAll();

}
