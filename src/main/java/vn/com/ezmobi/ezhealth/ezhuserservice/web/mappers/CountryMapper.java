package vn.com.ezmobi.ezhealth.ezhuserservice.web.mappers;

import org.mapstruct.Mapper;
import vn.com.ezmobi.ezhealth.ezhuserservice.domain.Country;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.model.CountryDto;

/**
 * Created by ezmobivietnam on 2021-01-04.
 */
@Mapper
public interface CountryMapper {
    Country countryDtoToCountry(CountryDto countryDto);
    CountryDto countryToCountryDto(Country country);
}
