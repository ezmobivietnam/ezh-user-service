package vn.com.ezmobi.ezhealth.ezhuserservice.utils.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import vn.com.ezmobi.ezhealth.ezhuserservice.domain.Country;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.model.CountryDto;

/**
 * Created by ezmobivietnam on 2021-01-04.
 */
@Mapper (componentModel = "spring")
public interface CountryMapper {
    Country countryDtoToCountry(CountryDto countryDto);

    CountryDto countryToCountryDto(Country country);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCountryFromCountryDto(CountryDto countryDto, @MappingTarget Country country);
}
