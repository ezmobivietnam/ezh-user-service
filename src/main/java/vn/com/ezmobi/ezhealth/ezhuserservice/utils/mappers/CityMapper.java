package vn.com.ezmobi.ezhealth.ezhuserservice.utils.mappers;

import org.mapstruct.Mapper;
import vn.com.ezmobi.ezhealth.ezhuserservice.domain.City;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.model.CityDto;

/**
 * Created by ezmobivietnam on 2021-01-15.
 */
@Mapper
public interface CityMapper {
    /**
     * Convert CityDto object to City entity
     * @param cityDto
     * @return
     */
    City cityDtoToCity(CityDto cityDto);

    /**
     * Convert City entity to CityDto
     * @param city
     * @return
     */
    CityDto cityToCityDto(City city);
}
