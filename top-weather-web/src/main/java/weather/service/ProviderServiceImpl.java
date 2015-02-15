package weather.service;

import org.springframework.stereotype.Component;
import weather.dao.ProviderDao;
import weather.model.Provider;

import java.util.List;

@Component
public class ProviderServiceImpl extends AbstractSpringService<ProviderDao, Provider> implements ProviderService {

    public List<Provider> findByName(String name) {
        return getDao().findByName(name);
    }
}
