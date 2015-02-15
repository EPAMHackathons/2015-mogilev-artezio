package weather.dao;

import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import weather.model.Location;

import java.util.List;

@Service
@Transactional
public class LocationDaoImpl extends AbstractHibernateDao<Location> implements LocationDao {

    public List<Location> findByName(String name) {
        List<Location> result = (List<Location>) getSession().createCriteria(getPersistentClass())
                .add( Restrictions.eq("name", name)).list();
        for (Location location : result ) {
            Hibernate.initialize(location.getRequestRules());
        }
        return result;
    }

    public List<Location> getAll() {
        List<Location> result = super.getAll();
        for (Location location : result ) {
            Hibernate.initialize(location.getRequestRules());
        }
        return result;
    }
}
