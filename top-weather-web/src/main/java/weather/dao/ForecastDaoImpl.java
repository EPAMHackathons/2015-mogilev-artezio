package weather.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import weather.dto.ForecastRateDto;
import weather.model.Forecast;
import weather.model.enumeration.FeatureType;
import weather.model.enumeration.OrderType;
import weather.model.enumeration.Period;
import weather.util.Utils;

import java.util.Calendar;
import java.util.List;

@Service
@Transactional
public class ForecastDaoImpl extends AbstractHibernateDao<Forecast> implements ForecastDao {

    public List<ForecastRateDto> getForecastByRate(String locationUID, FeatureType featureType, Period period, OrderType orderType) {

        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.createAlias("request", "request");
        criteria.createAlias("request.requestRule", "requestRule");
        criteria.createAlias("requestRule.location", "location");

        if (!StringUtils.isEmpty(locationUID)) {
            criteria.add(Restrictions.eq("location.uid", locationUID));
        }

        if (featureType != null) {
            criteria.add(Restrictions.eq("featureType", featureType));
        }

        Calendar calYesterday = Calendar.getInstance();
        calYesterday.add(Calendar.DATE, -1);

        criteria.add(Restrictions.le("request.forecastDate", Utils.formatEndDate(calYesterday.getTime())));

        Calendar calRatePeriod = Calendar.getInstance();
        switch (period) {
            case DAY: {
                calRatePeriod.add(Calendar.DATE, -1);
                break;
            }
            case WEEK: {
                calRatePeriod.add(Calendar.WEEK_OF_MONTH, -1);
                break;
            }
            case MONTH: {
                calRatePeriod.add(Calendar.MONTH, -1);
                break;
            }
        }
        criteria.add(Restrictions.ge("request.forecastDate",  Utils.formatStartDate(calRatePeriod.getTime())));

        ProjectionList projectionList = Projections.projectionList();
        projectionList.add(Projections.groupProperty("requestRule.provider").as("provider"));
        projectionList.add(Projections.avg("rate").as("rate"));
        criteria.setProjection(projectionList);

        addOrder(criteria, "rate", orderType);

        criteria.setResultTransformer(new AliasToBeanResultTransformer(ForecastRateDto.class));

        List<ForecastRateDto> result = criteria.list();

        for (ForecastRateDto forecastRateDto : result) {
            forecastRateDto.setFeatureType(featureType);
            forecastRateDto.setPeriod(period);
        }

        return result;
    }
}
