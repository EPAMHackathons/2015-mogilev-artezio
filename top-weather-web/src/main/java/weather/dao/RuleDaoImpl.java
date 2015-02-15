package weather.dao;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import weather.model.RequestRule;

@Service
@Transactional
public class RuleDaoImpl extends AbstractHibernateDao<RequestRule> implements RuleDao {
}
