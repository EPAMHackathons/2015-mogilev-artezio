package weather.dto;


import weather.model.enumeration.FeatureType;
import weather.model.enumeration.Period;
import weather.util.Pair;

import java.util.*;

public class RateTableRowDto {

    List<RequestDto> requests = new ArrayList<RequestDto>();

    Map<Pair<Period, FeatureType>, RateTableCellDto> cellDtoMap = new LinkedHashMap<Pair<Period, FeatureType>, RateTableCellDto>();

    public Map<Pair<Period, FeatureType>, RateTableCellDto> getCellDtoMap() {
        return cellDtoMap;
    }

    public void setCellDtoMap(Map<Pair<Period, FeatureType>, RateTableCellDto> cellDtoMap) {
        this.cellDtoMap = cellDtoMap;
    }

    public List<RequestDto> getRequests() {
        return requests;
    }

    public void setRequests(List<RequestDto> requests) {
        this.requests = requests;
    }
}
