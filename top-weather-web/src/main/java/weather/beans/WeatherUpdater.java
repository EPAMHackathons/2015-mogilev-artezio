package weather.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import weather.model.Request;
import weather.model.RequestRule;
import weather.provider.WeatherForecastProvider;
import weather.service.RequestService;
import weather.service.RuleService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author vosipenko
 *         on 15.02.2015.
 */
@Component
public class WeatherUpdater {
    @Autowired
    private RuleService ruleService;

    @Autowired
    private RequestService requestService;

    @Autowired
    private ApplicationContext context;

    public void updateWeather() {
        Set<String> beans = new HashSet<String>();
        beans.addAll(Arrays.asList(context.getBeanDefinitionNames()));
        for (RequestRule rule : ruleService.getAll()) {
            if (beans.contains(rule.getProvider().getName())) {
                WeatherForecastProvider provider = (WeatherForecastProvider) context.getBean(rule.getProvider().getName());
                for (Request request : provider.getWeather(rule)) {
                    requestService.save(request);
                }
            }
        }

    }
}
