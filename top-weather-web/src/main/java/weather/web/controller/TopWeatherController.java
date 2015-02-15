package weather.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import weather.beans.TopWeatherBean;
import weather.dto.RateTableDto;
import weather.model.Location;
import weather.model.enumeration.FeatureType;
import weather.model.enumeration.OrderType;
import weather.model.enumeration.Period;
import weather.service.LocationService;
import weather.util.Pair;

import java.util.List;

@Controller
public class TopWeatherController {

    @Autowired
    private TopWeatherBean topWeatherBean;

    @Autowired
    private LocationService locationService;

    @RequestMapping(value = "/main.html")
    public ModelAndView main (@RequestParam(value = "locationUid", required = false, defaultValue = "1") String locationUid) {
        ModelAndView mv = new ModelAndView("main");
        List<Location> locations = locationService.getAll();
        mv.addObject("locations", locations);

        RateTableDto tableDto = topWeatherBean.getForecast(locationUid, new Pair(Period.DAY, FeatureType.TEMPERATURE_DAY), OrderType.DESC);
        mv.addObject("tableDto", tableDto);

        return mv;
    }
}
