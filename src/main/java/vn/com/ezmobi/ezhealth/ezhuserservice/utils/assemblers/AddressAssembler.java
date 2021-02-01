package vn.com.ezmobi.ezhealth.ezhuserservice.utils.assemblers;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import vn.com.ezmobi.ezhealth.ezhuserservice.domain.Address;
import vn.com.ezmobi.ezhealth.ezhuserservice.utils.mappers.AddressMapper;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.controllers.AddressController;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.controllers.CityController;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.model.AddressDto;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Created by ezmobivietnam on 2021-01-29.
 */
@Component
public class AddressAssembler implements RepresentationModelAssembler<Address, AddressDto> {
    private final AddressMapper mapper;

    public AddressAssembler(AddressMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public AddressDto toModel(Address entity) {
        AddressDto addressDto = mapper.addressToAddressDto(entity);
        // add link to self model
        addressDto.add(linkTo(methodOn(AddressController.class).findById(entity.getCity().getCountry().getId(),
                entity.getCity().getId(), entity.getId())).withSelfRel());
        //add link to city model
        addressDto.add(linkTo(
                methodOn(CityController.class).findById(entity.getCity().getCountry().getId(),
                        entity.getCity().getId())).withRel("city"));
        return addressDto;
    }
}
