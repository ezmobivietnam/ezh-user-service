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
import vn.com.ezmobi.ezhealth.ezhuserservice.repositories.AddressRepository;
import vn.com.ezmobi.ezhealth.ezhuserservice.utils.assemblers.AddressAssembler;
import vn.com.ezmobi.ezhealth.ezhuserservice.utils.mappers.AddressMapper;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.controllers.AddressController;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.model.AddressDto;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
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

    private final AddressRepository repository;
    private final AddressMapper mapper;
    private final AddressAssembler addressAssembler;
    private final PagedResourcesAssembler pagedResourcesAssembler;

    public AddressServiceImpl(AddressRepository repository, AddressMapper mapper, AddressAssembler addressAssembler, PagedResourcesAssembler pagedResourcesAssembler) {
        this.repository = repository;
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
        if(!CollectionUtils.isEmpty(withAddressIdList) && StringUtils.hasLength(withAddress)) {
            // query data using all parameters
            addressesPage = repository.findAllByIdInAndAddressContainingIgnoreCaseAndCity_IdAndCity_Country_Id(
                    withAddressIdList, withAddress, cityId, countryId, pageRequest);
        } else if (!CollectionUtils.isEmpty(withAddressIdList)) {
            // query data using parameters countryId, cityId and withAddressIdList
            addressesPage = repository.findAllByIdInAndCity_IdAndCity_Country_Id(withAddressIdList, cityId, countryId, pageRequest);
        } else if (StringUtils.hasLength(withAddress)) {
            // query data using parameters countryId, cityId and withAddress
            addressesPage = repository.findAllByCity_IdAndCity_Country_IdAndAddressContainingIgnoreCase(cityId, countryId
                    , withAddress, pageRequest);
        } else {
            // query data using two mandatory parameters countryId and cityId
            addressesPage = repository.findAllByCity_IdAndCity_Country_Id(cityId, countryId, pageRequest);
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
        if(!CollectionUtils.isEmpty(withAddressIdList) && StringUtils.hasLength(withAddress)) {
            // query data using all parameters
            entities = repository.findAllByIdInAndAddressContainingIgnoreCaseAndCity_IdAndCity_Country_Id(
                    withAddressIdList, withAddress, cityId, countryId);
        } else if (!CollectionUtils.isEmpty(withAddressIdList)) {
            // query data using parameters countryId, cityId and withAddressIdList
            entities = repository.findAllByIdInAndCity_IdAndCity_Country_Id(withAddressIdList, cityId, countryId);
        } else if (StringUtils.hasLength(withAddress)) {
            // query data using parameters countryId, cityId and withAddress
            entities = repository.findAllByCity_IdAndCity_Country_IdAndAddressContainingIgnoreCase(cityId, countryId
                    , withAddress);
        } else {
            // query data using two mandatory parameters countryId and cityId
            entities = repository.findAllByCity_IdAndCity_Country_Id(cityId, countryId);
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
        Optional<Address> addressOptional = repository.findByIdAndCity_IdAndCity_Country_Id(addressId, cityId, countryId);
        return addressOptional.map(addressAssembler::toModel);
    }

    /**
     * Finding data with pagination .
     *
     * @param whatToFind  (Optional) null value indicates find all otherwise finding by a specific criteria.
     * @param pageRequest (Required) the page request
     * @return
     */
    @Override
    public CollectionModel findPaginated(String whatToFind, PageRequest pageRequest) {
        return null;
    }

    /**
     * Find all data from database. The result is not paginated.
     *
     * @return
     */
    @Override
    public CollectionModel findAll() {
        return null;
    }

    /**
     * Find in a specific column of a table for a specific value for example name, address...
     *
     * @param whatToFind something to be found
     * @return
     */
    @Override
    public CollectionModel findByText(String whatToFind) {
        return null;
    }
}
