package weather.dao;

import weather.model.BaseEntity;

import java.util.List;

public interface Dao<T extends BaseEntity> {

    void saveOrUpdate(T entity);

    void delete(T entity);

    List<T> getAll();

}
