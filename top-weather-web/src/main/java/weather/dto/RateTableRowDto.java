package weather.dto;


import weather.model.enumeration.FeatureType;
import weather.model.enumeration.Period;
import weather.util.Pair;

import java.util.LinkedHashMap;
import java.util.Map;

public class RateTableRowDto {

    Map<Pair<Period, FeatureType>, RateTableCellDto> cellDtoMap = new LinkedHashMap<Pair<Period, FeatureType>, RateTableCellDto>();

    public Map<Pair<Period, FeatureType>, RateTableCellDto> getCellDtoMap() {
        return cellDtoMap;
    }

    public void setCellDtoMap(Map<Pair<Period, FeatureType>, RateTableCellDto> cellDtoMap) {
        this.cellDtoMap = cellDtoMap;
    }
}
