package vn.com.ezmobi.ezhealth.ezhuserservice.utils.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import vn.com.ezmobi.ezhealth.ezhuserservice.domain.Address;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.model.AddressDto;

/**
 * Created by ezmobivietnam on 2021-01-29.
 */
@Mapper(componentModel = "spring")
public interface AddressMapper {

    Address addressDtoToAddress(AddressDto addressDto);

    AddressDto addressToAddressDto(Address address);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateAddressFromAddressDto(AddressDto addressDto, @MappingTarget Address address);
}
