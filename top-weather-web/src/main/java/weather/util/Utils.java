package weather.util;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import weather.dto.RequestDto;
import weather.model.Request;

import java.text.SimpleDateFormat;
import java.util.*;

public class Utils {

    public static Date formatStartDate(Date date) {
        return DateUtils.truncate(date, Calendar.DATE);
    }

    public static Date formatEndDate(Date date) {
        return DateUtils.addMilliseconds(DateUtils.ceiling(date, Calendar.DATE), -1);
    }

    public static List<RequestDto> requestConverter(List<Request> requests) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EE", Locale.getDefault());
        List<RequestDto> requestsDto = new ArrayList<RequestDto>(requests.size());
        for (Request request : requests) {
            RequestDto requestDto = new RequestDto();
            requestDto.setForecastDate(request.getForecastDate());
            requestDto.setRequestDate(request.getRequestDate());
            requestDto.setForecastDayOfWeek(simpleDateFormat.format(request.getForecastDate()));
            requestDto.setRequestDayOfWeek(simpleDateFormat.format(request.getRequestDate()));
            requestDto.setForecasts(request.getForecasts());
            requestDto.setRequestRule(request.getRequestRule());
            requestsDto.add(requestDto);
        }
        return requestsDto;
    }

    /*
    * Compare rated with real
    * */
    public static Integer calcTempRate(String ratedValue, String realValue) {
        if (StringUtils.isEmpty(ratedValue) || StringUtils.isEmpty(realValue) )
            return null;

        if (ratedValue.equals(realValue))
            return 100;

        return 50;
    }
}


