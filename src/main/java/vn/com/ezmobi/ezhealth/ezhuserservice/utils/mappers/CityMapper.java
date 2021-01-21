package vn.com.ezmobi.ezhealth.ezhuserservice.utils.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import vn.com.ezmobi.ezhealth.ezhuserservice.domain.City;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.model.CityDto;

/**
 * Ref: https://www.baeldung.com/spring-data-partial-update
 *
 * Created by ezmobivietnam on 2021-01-15.
 */
@Mapper (componentModel = "spring")
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

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCityFromCityDto(CityDto cityDto, @MappingTarget City city);
}
