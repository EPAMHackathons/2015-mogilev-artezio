package weather.service;

import org.springframework.stereotype.Component;
import weather.dao.RuleDao;
import weather.model.RequestRule;

@Component
public class RuleServiceImpl extends AbstractSpringService<RuleDao, RequestRule> implements RuleService {

}
