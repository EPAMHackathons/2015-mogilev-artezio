package weather.dao;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import weather.model.Request;
import weather.model.enumeration.OrderType;
import weather.model.enumeration.Period;
import weather.model.enumeration.RequestTime;
import weather.util.Utils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class RequestDaoImpl extends AbstractHibernateDao<Request> implements RequestDao {

    public List<Request> getForecastForPeriod(String locationUID, Long providerId, Period period) {

        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.createAlias("requestRule", "requestRule");
        criteria.createAlias("requestRule.location", "location");
        criteria.createAlias("requestRule.provider", "provider");

        if (!StringUtils.isEmpty(locationUID)) {
            criteria.add(Restrictions.eq("location.uid", locationUID));
        }

        if (providerId != null) {
            criteria.add(Restrictions.eq("provider.providerId", providerId));
        }

        criteria.add(Restrictions.ge("forecastDate", Utils.formatStartDate(new Date())));

        RequestTime ruleRequestTime = null;

        Calendar endForecastDate = Calendar.getInstance();
        switch (period) {
            case DAY: {
                ruleRequestTime = RequestTime.TODAY;
                break;
            }
            case WEEK: {
                endForecastDate.add(Calendar.DAY_OF_WEEK, 7);
                ruleRequestTime = RequestTime.WEEK;
                break;
            }
            case MONTH: {
                endForecastDate.add(Calendar.MONTH, 1);
                ruleRequestTime = RequestTime.MONTH;
                break;
            }
        }
        criteria.add(Restrictions.le("forecastDate", Utils.formatEndDate(endForecastDate.getTime())));


        criteria.add(Restrictions.ge("requestDate", Utils.formatStartDate(new Date())));
        criteria.add(Restrictions.le("requestDate", Utils.formatEndDate(new Date())));

        if (period != null) {
            criteria.add(Restrictions.eq("requestRule.requestTime", ruleRequestTime));
        }

        addOrder(criteria, "forecastDate", OrderType.ASC);

        List<Request>  result = criteria.list();

        for (Request request : result ) {
            Hibernate.initialize(request.getForecasts());
        }

        return result;
    }

    public List<Request> getRequestsByDate(Date requestDate, Date forecastDate, Long ruleId) {

        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.createAlias("requestRule", "requestRule");
        criteria.createAlias("requestRule.location", "location");
        criteria.createAlias("requestRule.provider", "provider");

        if (ruleId != null) {
            criteria.add(Restrictions.eq("requestRule.requestRuleId", ruleId));
        }

        if (forecastDate != null) {
            criteria.add(Restrictions.ge("forecastDate", Utils.formatStartDate(forecastDate)));
            criteria.add(Restrictions.le("forecastDate", Utils.formatEndDate(forecastDate)));
        }

        if (requestDate != null) {
            criteria.add(Restrictions.ge("requestDate", Utils.formatStartDate(requestDate)));
            criteria.add(Restrictions.le("requestDate", Utils.formatEndDate(requestDate)));
        }

        List<Request>  result = criteria.list();

        for (Request request : result ) {
            Hibernate.initialize(request.getForecasts());
            Hibernate.initialize(request.getRequestRule());
        }

        return result;
    }
}
