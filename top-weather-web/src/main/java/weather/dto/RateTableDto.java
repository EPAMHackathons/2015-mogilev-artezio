package weather.dto;


import weather.model.Provider;

import java.util.LinkedHashMap;
import java.util.Map;

public class RateTableDto {

    Map<Provider, RateTableRowDto> rowDtoMap = new LinkedHashMap<Provider, RateTableRowDto>();

    public Map<Provider, RateTableRowDto> getRowDtoMap() {
        return rowDtoMap;
    }

    public void setRowDtoMap(Map<Provider, RateTableRowDto> rowDtoMap) {
        this.rowDtoMap = rowDtoMap;
    }
}
