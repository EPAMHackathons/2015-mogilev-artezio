package weather.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import weather.dao.Dao;
import weather.model.BaseEntity;

import java.util.List;

public abstract class AbstractSpringService <T extends Dao, E extends BaseEntity> implements Service<E> {

    private T dao;

    protected T getDao() {
        return dao;
    }

    @Autowired
    protected void setDao(T dao) {
        this.dao = dao;
    }

    @Transactional
    public E save(E entity) {
        dao.saveOrUpdate(entity);
        return entity;
    }

    @Transactional
    public void delete(E entity){
        dao.delete(entity);
    }

    @Transactional
    public List<E> getAll() {
        return dao.getAll();
    }

}
