package weather.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import weather.model.BaseEntity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.List;

public abstract class AbstractHibernateDao<T extends BaseEntity> implements Dao<T> {

    private SessionFactory sessionFactory;
    private Class<T> persistentClass;

    @SuppressWarnings("unchecked")
    public AbstractHibernateDao() {
        for (Type type = getClass().getGenericSuperclass(); type != null; ) {
            if (type instanceof ParameterizedType) {
                Object parameter = ((ParameterizedType) type).getActualTypeArguments()[0];

                if (parameter instanceof Class) {
                    persistentClass = (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
                } else {
                    persistentClass = (Class<T>) ((ParameterizedType) ((TypeVariable<?>) parameter).getBounds()[0]).getRawType();
                }

                break;
            } else if (type instanceof Class) {
                type = ((Class<?>) type).getGenericSuperclass();
            }
        }
    }

    public List<T> getAll() {
        return (List<T>) getSession().createCriteria(getPersistentClass()).list();
    }

    @Autowired
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Class<T> getPersistentClass() {
        return persistentClass;
    }

    public void saveOrUpdate(T entity) {
        getSession().saveOrUpdate(entity);
    }

    public final void delete(T entity) {
        getSession().delete(entity);
    }

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

}
