package weather.dao;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import weather.model.Provider;

import java.util.List;

@Service
@Transactional
public class ProviderDaoImpl extends AbstractHibernateDao<Provider> implements ProviderDao {

    public List<Provider> findByName(String name) {
        return (List<Provider>) getSession().createCriteria(getPersistentClass())
                .add( Restrictions.eq("name", name)).list();
    }

}
