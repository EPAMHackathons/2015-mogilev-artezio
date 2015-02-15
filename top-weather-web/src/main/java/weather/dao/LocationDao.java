package weather.dao;

import weather.model.Location;

import java.util.List;

public interface LocationDao extends Dao<Location> {
    List<Location> findByName(String name);
}
