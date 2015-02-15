package weather.service;

import weather.model.Location;

import java.util.List;

public interface LocationService extends Service<Location> {
    List<Location> findByName(String name);
}
