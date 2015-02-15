package weather.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import weather.beans.TopWeatherBean;
import weather.model.Location;
import weather.service.LocationService;

import java.util.List;

@Controller
public class TopWeatherController {

    @Autowired
    private TopWeatherBean topWeatherBean;

    @Autowired
    private LocationService locationService;

    @RequestMapping(value = "/main.html")
    public ModelAndView main () {
        ModelAndView mv = new ModelAndView("index");
        List<Location> locations = locationService.getAll();
        mv.addObject("locations", locations);
        return mv;
    }
}
