package weather.service;

import weather.model.Provider;

import java.util.List;

public interface ProviderService extends Service<Provider> {
    List<Provider> findByName(String name);
}
