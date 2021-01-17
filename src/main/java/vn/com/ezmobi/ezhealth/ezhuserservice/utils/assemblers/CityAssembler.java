package vn.com.ezmobi.ezhealth.ezhuserservice.utils.assemblers;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import vn.com.ezmobi.ezhealth.ezhuserservice.domain.City;
import vn.com.ezmobi.ezhealth.ezhuserservice.utils.mappers.CityMapper;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.controllers.CityController;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.controllers.CountryController;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.model.CityDto;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Created by ezmobivietnam on 2021-01-15.
 */
@Component
public class CityAssembler implements RepresentationModelAssembler<City, CityDto> {

    private final CityMapper cityMapper;

    public CityAssembler(CityMapper cityMapper) {
        this.cityMapper = cityMapper;
    }

    @Override
    public CityDto toModel(City entity) {
        CityDto cityDto = cityMapper.cityToCityDto(entity);
        // add links
        cityDto.add(linkTo(
                methodOn(CityController.class)
                        .findById(entity.getCountry().getId(), entity.getId()))
                .withSelfRel());
        cityDto.add(linkTo(
                methodOn(CountryController.class)
                        .findById(entity.getCountry().getId())).withRel("country"));
        return cityDto;
    }
}
