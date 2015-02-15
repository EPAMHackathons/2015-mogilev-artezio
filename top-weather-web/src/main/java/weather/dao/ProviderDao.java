package weather.dao;

import weather.model.Provider;

import java.util.List;

public interface ProviderDao extends Dao<Provider> {
    List<Provider> findByName(String name);
}
