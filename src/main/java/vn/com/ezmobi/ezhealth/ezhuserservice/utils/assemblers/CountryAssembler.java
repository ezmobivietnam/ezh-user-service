package vn.com.ezmobi.ezhealth.ezhuserservice.utils.assemblers;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import vn.com.ezmobi.ezhealth.ezhuserservice.domain.Country;
import vn.com.ezmobi.ezhealth.ezhuserservice.utils.mappers.CountryMapper;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.controllers.CountryController;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.model.CountryDto;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Created by ezmobivietnam on 2021-01-09.
 */
@Component
public class CountryAssembler implements RepresentationModelAssembler<Country, CountryDto> {

    private final CountryMapper countryMapper;

    public CountryAssembler(CountryMapper countryMapper) {
        this.countryMapper = countryMapper;
    }

    @Override
    public CountryDto toModel(Country entity) {
        CountryDto dto = countryMapper.countryToCountryDto(entity);
        dto.add(linkTo(methodOn(CountryController.class).findById(entity.getId())).withSelfRel());
        return dto;
    }
}
