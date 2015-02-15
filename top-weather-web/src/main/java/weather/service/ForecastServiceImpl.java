package weather.service;

import org.springframework.stereotype.Component;
import weather.dao.ForecastDao;
import weather.dto.ForecastRateDto;
import weather.dto.RateTableCellDto;
import weather.dto.RateTableDto;
import weather.dto.RateTableRowDto;
import weather.model.Forecast;
import weather.model.Provider;
import weather.model.enumeration.FeatureType;
import weather.model.enumeration.OrderType;
import weather.model.enumeration.Period;
import weather.util.Pair;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class ForecastServiceImpl extends AbstractSpringService<ForecastDao, Forecast> implements ForecastService {
    public List<ForecastRateDto> getForecastByRate(String locationUID, FeatureType featureType, Period period, OrderType orderType) {
        return getDao().getForecastByRate(locationUID, featureType, period, orderType);
    }


    public RateTableDto getForecast(String locationUID, Pair<Period, FeatureType> orderField, OrderType orderType) {
        RateTableDto rateTableDto = new RateTableDto();
        Map<Provider, RateTableRowDto> rowDtoMap = new LinkedHashMap<Provider, RateTableRowDto>();
        rateTableDto.setRowDtoMap(rowDtoMap);
        List<ForecastRateDto> allData = new ArrayList<ForecastRateDto>();
        List<FeatureType> rateTableFeatures = new ArrayList<FeatureType>();
        rateTableFeatures.add(FeatureType.TEMPERATURE_DAY);
        rateTableFeatures.add(FeatureType.PHENOMENA_DAY);

        // запрашиваем данные на все сроки. по всем показателям
        for (Period period : Period.values()) {
            for (FeatureType featureType : rateTableFeatures) {
                List<ForecastRateDto> data = getForecastByRate(locationUID, featureType, period, orderType);
                if ((new Pair(period, featureType)).equals(orderField)) {
                    //заполнение таблицы с учетом сортировки
                    for (ForecastRateDto forecastRateDto : data) {
                        rowDtoMap.put(forecastRateDto.getProvider(), new RateTableRowDto());
                    }
                }
                allData.addAll(data);
            }
        }

        // сетка таблицы
        for (RateTableRowDto rateTableRowDto : rowDtoMap.values()) {
            Map<Pair<Period, FeatureType>, RateTableCellDto> cells = rateTableRowDto.getCellDtoMap();
            for (Period period : Period.values()) {
                for (FeatureType featureType : rateTableFeatures) {
                    Pair<Period, FeatureType> cellKey = new Pair(period, featureType);
                    if (!cells.containsKey(cellKey)) {
                        cells.put(cellKey, new RateTableCellDto());
                    }
                }
            }
        }

        // конвертация из вертикального представления в горизонтальное
        for(ForecastRateDto forecastRateDto : allData) {
            if (rowDtoMap.containsKey(forecastRateDto.getProvider())) {
                RateTableRowDto row = rowDtoMap.get(forecastRateDto.getProvider());
                Map<Pair<Period, FeatureType>, RateTableCellDto> cells = row.getCellDtoMap();
                RateTableCellDto cell = new RateTableCellDto();
                cell.setRate(forecastRateDto.getRate().intValue());
                cells.put(new Pair(forecastRateDto.getPeriod(), forecastRateDto.getFeatureType()), cell);
            }
        }

        return rateTableDto;
    }


}


