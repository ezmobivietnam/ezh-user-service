package vn.com.ezmobi.ezhealth.ezhuserservice.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import vn.com.ezmobi.ezhealth.ezhuserservice.domain.Address;
import vn.com.ezmobi.ezhealth.ezhuserservice.domain.City;
import vn.com.ezmobi.ezhealth.ezhuserservice.repositories.AddressRepository;
import vn.com.ezmobi.ezhealth.ezhuserservice.repositories.CityRepository;
import vn.com.ezmobi.ezhealth.ezhuserservice.services.exceptions.TaskExecutionException;
import vn.com.ezmobi.ezhealth.ezhuserservice.utils.assemblers.AddressAssembler;
import vn.com.ezmobi.ezhealth.ezhuserservice.utils.mappers.AddressMapper;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.controllers.AddressController;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.controllers.AddressSimpleController;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.model.AddressDto;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Created by ezmobivietnam on 2021-01-29.
 */
@Slf4j
@Transactional
@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final CityRepository cityRepository;
    private final AddressMapper mapper;
    private final AddressAssembler addressAssembler;
    private final PagedResourcesAssembler pagedResourcesAssembler;

    public AddressServiceImpl(AddressRepository addressRepository,
                              CityRepository cityRepository,
                              AddressMapper mapper,
                              AddressAssembler addressAssembler,
                              PagedResourcesAssembler pagedResourcesAssembler) {
        this.addressRepository = addressRepository;
        this.cityRepository = cityRepository;
        this.mapper = mapper;
        this.addressAssembler = addressAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    /**
     * Finding data with pagination.
     *
     * @param countryId         (Required) the ID of the root object
     * @param cityId            (Required) the ID of the level one object
     * @param withAddressIdList (Optional) filtering the result with the given level two ids
     * @param withAddress       (Optional) filtering the result with given text
     * @param pageRequest       (Required) the page request
     * @return
     */
    @Override
    public CollectionModel<AddressDto> findPaginated(
            Integer countryId,
            Integer cityId,
            List<Integer> withAddressIdList,
            String withAddress,
            PageRequest pageRequest) {
        //
        Assert.notNull(countryId, "Country id must not be null!");
        Assert.notNull(cityId, "City id must not be null!");
        Assert.notNull(pageRequest, "PageRequest must not be null!");
        Page<Address> addressesPage;
        if (!CollectionUtils.isEmpty(withAddressIdList) && StringUtils.hasLength(withAddress)) {
            // query data using all parameters
            addressesPage = addressRepository.findAllByIdInAndAddressContainingIgnoreCaseAndCity_IdAndCity_Country_Id(
                    withAddressIdList, withAddress, cityId, countryId, pageRequest);
        } else if (!CollectionUtils.isEmpty(withAddressIdList)) {
            // query data using parameters countryId, cityId and withAddressIdList
            addressesPage = addressRepository.findAllByIdInAndCity_IdAndCity_Country_Id(withAddressIdList, cityId, countryId, pageRequest);
        } else if (StringUtils.hasLength(withAddress)) {
            // query data using parameters countryId, cityId and withAddress
            addressesPage = addressRepository.findAllByCity_IdAndCity_Country_IdAndAddressContainingIgnoreCase(cityId, countryId
                    , withAddress, pageRequest);
        } else {
            // query data using two mandatory parameters countryId and cityId
            addressesPage = addressRepository.findAllByCity_IdAndCity_Country_Id(cityId, countryId, pageRequest);
        }
        //
        return pagedResourcesAssembler.toModel(addressesPage, addressAssembler);
    }

    /**
     * Finding all data (without pagination).
     *
     * @param countryId         (Required) the ID of the root object
     * @param cityId            (Required) the ID of the level one object
     * @param withAddressIdList (Optional) filtering the result with the given level two ids
     * @param withAddress       (Optional) filtering the result with given text
     * @return
     */
    @Override
    public CollectionModel<AddressDto> findAll(
            Integer countryId,
            Integer cityId,
            List<Integer> withAddressIdList,
            String withAddress) {
        //
        Assert.notNull(countryId, "Country id must not be null!");
        Assert.notNull(cityId, "City id must not be null!");
        List<Address> entities;
        if (!CollectionUtils.isEmpty(withAddressIdList) && StringUtils.hasLength(withAddress)) {
            // query data using all parameters
            entities = addressRepository.findAllByIdInAndAddressContainingIgnoreCaseAndCity_IdAndCity_Country_Id(
                    withAddressIdList, withAddress, cityId, countryId);
        } else if (!CollectionUtils.isEmpty(withAddressIdList)) {
            // query data using parameters countryId, cityId and withAddressIdList
            entities = addressRepository.findAllByIdInAndCity_IdAndCity_Country_Id(withAddressIdList, cityId, countryId);
        } else if (StringUtils.hasLength(withAddress)) {
            // query data using parameters countryId, cityId and withAddress
            entities = addressRepository.findAllByCity_IdAndCity_Country_IdAndAddressContainingIgnoreCase(cityId, countryId
                    , withAddress);
        } else {
            // query data using two mandatory parameters countryId and cityId
            entities = addressRepository.findAllByCity_IdAndCity_Country_Id(cityId, countryId);
        }
        CollectionModel<AddressDto> collectionModel = addressAssembler.toCollectionModel(entities);
        collectionModel.add(linkTo(methodOn(AddressController.class).findList(countryId, cityId, withAddressIdList,
                withAddress, null, null)).withSelfRel().expand());
        //
        return collectionModel;
    }

    /**
     * Find a item at level two belong to the level one item and the root item
     *
     * @param countryId (Required) the ID of the root object
     * @param cityId    (Required) the ID of the level one object
     * @param addressId (Required) the ID of the level two object
     * @return
     */
    @Override
    public Optional<AddressDto> findById(Integer countryId, Integer cityId, Integer addressId) {
        Assert.notNull(countryId, "Country id must not be null!");
        Assert.notNull(cityId, "City id must not be null!");
        Assert.notNull(cityId, "Address id must not be null!");
        Optional<Address> addressOptional = addressRepository.findByIdAndCity_IdAndCity_Country_Id(addressId, cityId, countryId);
        return addressOptional.map(addressAssembler::toModel);
    }

    /**
     * Adding new address to the city entity.
     *
     * @param countryId  (Required) the ID of the Country entity
     * @param cityId     (Required) the ID of the City entity
     * @param addressDto (Required) the AddressDto (presentation) model
     * @return
     */
    @Override
    public AddressDto addNew(Integer countryId, Integer cityId, @Valid AddressDto addressDto) {
        Assert.notNull(countryId, "Country id must not be null!");
        Assert.notNull(cityId, "City id must not be null!");
        Assert.notNull(addressDto, "Address data must not be null!");
        //
        Optional<City> cityOptional = cityRepository.findByIdAndCountry_Id(cityId, countryId);
        City city = cityOptional.orElseThrow(() -> {
            String s = String.format("City [%] does not exist in the Country [%d]", cityId, countryId);
            return new TaskExecutionException(s);
        });
        Address address = mapper.addressDtoToAddress(addressDto);
        city.add(address);
        address = addressRepository.save(address);
        return addressAssembler.toModel(address);
    }

    /**
     * Updating an existing address entity.
     *
     * @param countryId  (Required) the ID of the Country entity
     * @param cityId     (Required) the ID of the City entity
     * @param addressDto (Required) the AddressDto (presentation) model
     * @param addressId  (Required) the ID of the existing address entity to be updated
     * @return
     */
    @Override
    public AddressDto update(Integer countryId, Integer cityId, @Valid AddressDto addressDto, Integer addressId) {
        Assert.notNull(countryId, "Country id must not be null!");
        Assert.notNull(cityId, "City id must not be null!");
        Assert.notNull(addressDto, "Address data must not be null!");
        Assert.notNull(addressId, "Address id must not be null!");
        //
        Optional<Address> addressOptional = addressRepository
                .findByIdAndCity_IdAndCity_Country_Id(addressId, cityId, countryId);
        Address address = addressOptional.orElseThrow(() -> {
            String s = String.format("Failed to search the address with (address id=[%d], city id=[%d] and country " +
                    "id=[%d])", addressId, cityId, countryId);
            return new TaskExecutionException(s);
        });
        // Use mapstruct to map the nonnull attributes from dto to entity.
        mapper.updateAddressFromAddressDto(addressDto, address);
        return addressAssembler.toModel(addressRepository.save(address));
    }

    /**
     * Deleting an item from the root object.
     *
     * @param countryId (Required) the ID of the Country entity
     * @param cityId    (Required) the ID of the City entity
     * @param addressId (Required) the ID of the existing address entity to be deleted
     */
    @Override
    public void delete(Integer countryId, Integer cityId, Integer addressId) {
        Assert.notNull(countryId, "Country id must not be null!");
        Assert.notNull(cityId, "City id must not be null!");
        Assert.notNull(cityId, "Address id must not be null!");
        addressRepository.deleteByIdAndCity_IdAndCity_Country_Id(addressId, cityId, countryId);
    }

    //===========================================================================================
    // Implement methods from SimpleService serving the Simple Controller
    //===========================================================================================
    /**
     * Finding addresses with pagination .
     * If filter parameters is not specified then find all.
     *
     * @param withAddressIds (Optional) filtering the result by the address ids
     * @param withAddress    (Optional) filtering the result with the given address.
     * @param pageRequest    (Required) the page request
     * @return
     */
    @Override
    public CollectionModel<AddressDto> findPaginated(List<Integer> withAddressIds,
                                                     String withAddress,
                                                     PageRequest pageRequest) {
        Assert.notNull(pageRequest, "PageRequest must not be null!");
        //
        Page<Address> addressPage;
        if (!CollectionUtils.isEmpty(withAddressIds) && StringUtils.hasLength(withAddress)) {
            //find all with address ids and containing given address
            addressPage = addressRepository.findAllByIdInAndAddressContainingIgnoreCase(withAddressIds, withAddress, pageRequest);
        } else if (!CollectionUtils.isEmpty(withAddressIds)) {
            // find all with address ids
            addressPage = addressRepository.findAllByIdIn(withAddressIds, pageRequest);
        } else if (StringUtils.hasLength(withAddress)) {
            // find all containing given address
            addressPage = addressRepository.findAllByAddressContainingIgnoreCase(withAddress, pageRequest);
        } else {
            // find all
            addressPage = addressRepository.findAll(pageRequest);
        }
        return pagedResourcesAssembler.toModel(addressPage, addressAssembler);
    }

    /**
     * Find all data from database. The result is not paginated.
     * If filter parameters is not specified then find all.
     *
     * @param withAddressIds (Optional) filtering the result by the level two id
     * @param withAddress    (Optional) filtering the result with the given text.
     * @return
     */
    @Override
    public CollectionModel<AddressDto> findAll(List<Integer> withAddressIds, String withAddress) {
        List<Address> addresses;
        if (!CollectionUtils.isEmpty(withAddressIds) && StringUtils.hasLength(withAddress)) {
            //find all with address ids and containing given address
            addresses = addressRepository.findAllByIdInAndAddressContainingIgnoreCase(withAddressIds, withAddress);
        } else if (!CollectionUtils.isEmpty(withAddressIds)) {
            // find all with address ids
            addresses = addressRepository.findAllByIdIn(withAddressIds);
        } else if (StringUtils.hasLength(withAddress)) {
            // find all containing given address
            addresses = addressRepository.findAllByAddressContainingIgnoreCase(withAddress);
        } else {
            // find all
            addresses = addressRepository.findAll();
        }
        CollectionModel<AddressDto> collectionModel = addressAssembler.toCollectionModel(addresses);
        collectionModel.add(linkTo(methodOn(AddressSimpleController.class).findList(withAddressIds, withAddress,
                null, null)).withSelfRel().expand());
        //
        return collectionModel;
    }

    @Override
    public void deleteAllByIds(List<Integer> addressIds) {
        Assert.notEmpty(addressIds, "List of IDs must not be null or empty");
        addressRepository.deleteAllByIdIn(addressIds);
    }
}
