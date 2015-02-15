package weather.service;

import org.springframework.stereotype.Component;
import weather.dao.LocationDao;
import weather.model.Location;

import java.util.List;

@Component
public class LocationServiceImpl extends AbstractSpringService<LocationDao, Location> implements LocationService {

    @Override
    public List<Location> findByName(String name) {
        return getDao().findByName(name);
    }
}
