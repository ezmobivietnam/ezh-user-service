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
import vn.com.ezmobi.ezhealth.ezhuserservice.domain.City;
import vn.com.ezmobi.ezhealth.ezhuserservice.domain.Country;
import vn.com.ezmobi.ezhealth.ezhuserservice.repositories.CityRepository;
import vn.com.ezmobi.ezhealth.ezhuserservice.repositories.CountryRepository;
import vn.com.ezmobi.ezhealth.ezhuserservice.services.exceptions.TaskExecutionException;
import vn.com.ezmobi.ezhealth.ezhuserservice.utils.assemblers.CityAssembler;
import vn.com.ezmobi.ezhealth.ezhuserservice.utils.mappers.CityMapper;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.controllers.CityController;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.controllers.CitySimpleController;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.model.CityDto;
import vn.com.ezmobi.framework.services.AbstractNamedEntitySimpleService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Ref:
 * 1. https://www.baeldung.com/spring-data-partial-update
 * <p>
 * Created by ezmobivietnam on 2021-01-15.
 */
@Slf4j
@Transactional
@Service
public class CityServiceImpl extends AbstractNamedEntitySimpleService<CityDto, Integer> implements CityService {

    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final CityMapper mapper;
    private final CityAssembler assembler;
    private final PagedResourcesAssembler pagedResourcesAssembler;

    public CityServiceImpl(CityRepository repository,
                           CountryRepository countryRepository, CityMapper mapper,
                           CityAssembler assembler,
                           PagedResourcesAssembler pagedResourcesAssembler) {
        super(repository, assembler, pagedResourcesAssembler);
        //
        this.cityRepository = repository;
        this.countryRepository = countryRepository;
        this.mapper = mapper;
        this.assembler = assembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    /**
     * Finding data with pagination belong to the root object by rootId.
     *
     * @param countryId   (Required) the root ID
     * @param withCityIds (Optional) filtering the result with the given city ids
     * @param withName    (Optional) filtering the result with given name
     * @param pageRequest (Required) the page request
     * @return
     */
    @Override
    public CollectionModel<CityDto> findPaginated(Integer countryId, List<Integer> withCityIds, String withName,
                                                  PageRequest pageRequest) {
        Assert.notNull(countryId, "Country id must not be null!");
        Assert.notNull(pageRequest, "PageRequest must not be null!");
        Page<City> cityPage;
        if (!CollectionUtils.isEmpty(withCityIds) && StringUtils.hasLength(withName)) {
            // find all cities with given ids and name
            cityPage = cityRepository.findAllByIdInAndNameContainingIgnoreCaseAndAndCountry_Id(withCityIds, withName,
                    countryId, pageRequest);
        } else if (!CollectionUtils.isEmpty(withCityIds)) {
            // find all cities with given ids
            cityPage = cityRepository.findAllByIdInAndCountry_Id(withCityIds, countryId, pageRequest);
        } else if (StringUtils.hasLength(withName)) {
            // find all cities with given name
            cityPage = cityRepository.findAllByNameContainingIgnoreCaseAndCountry_Id(withName, countryId, pageRequest);
        } else {
            // find all cities (without any conditions)
            cityPage = cityRepository.findAllByCountry_Id(countryId, pageRequest);
        }
        return pagedResourcesAssembler.toModel(cityPage, assembler);
    }

    /**
     * Finding data with pagination belong to the root object by rootId.
     *
     * @param countryId   (Required) the root ID
     * @param withCityIds (Optional) filtering the result with the given city ids
     * @param withName    (Optional) filtering the result with given name
     * @return
     */
    @Override
    public CollectionModel<CityDto> findAll(Integer countryId, List<Integer> withCityIds, String withName) {
        Assert.notNull(countryId, "Country id must not be null!");
        List<City> cities;
        if (!CollectionUtils.isEmpty(withCityIds) && StringUtils.hasLength(withName)) {
            // find all cities with given ids and name
            cities = cityRepository.findAllByIdInAndNameContainingIgnoreCaseAndAndCountry_Id(withCityIds, withName, countryId);
        } else if (!CollectionUtils.isEmpty(withCityIds)) {
            // find all cities with given ids
            cities = cityRepository.findAllByIdInAndCountry_Id(withCityIds, countryId);
        } else if (StringUtils.hasLength(withName)) {
            // find all cities with given name
            cities = cityRepository.findAllByNameContainingIgnoreCaseAndCountry_Id(withName, countryId);
        } else {
            // find all cities (without any conditions)
            cities = cityRepository.findAllByCountry_Id(countryId);
        }
        // converting to collection model
        CollectionModel<CityDto> collectionModel = assembler.toCollectionModel(cities);
        // adding self link
        collectionModel.add(linkTo(methodOn(CityController.class).findList(countryId, withCityIds, withName, null,
                null)).withSelfRel().expand());
        return collectionModel;
    }

    /**
     * Finding an city belong to the country.
     *
     * @param countryId (Required) the country id
     * @param cityId    (Required) the id of the city to be found
     * @return
     */
    @Override
    public Optional<CityDto> findById(Integer countryId, Integer cityId) {
        Assert.notNull(countryId, "Country id must not be null!");
        Assert.notNull(cityId, "City id must not be null!");
        Optional<City> cityEntity = cityRepository.findByIdAndCountry_Id(cityId, countryId);
        return cityEntity.map(assembler::toModel);
    }

    /**
     * Adding new city to the country.
     *
     * @param countryId (Required) the country id
     * @param cityDto   (Required) the city info
     * @return
     */
    @Override
    public CityDto addNew(Integer countryId, CityDto cityDto) {
        Assert.notNull(countryId, "Country id must not be null!");
        Assert.notNull(cityDto, "City data must not be null!");
        Optional<Country> result = countryRepository.findById(countryId);
        Country country = result.orElseThrow(() -> {
            String s = String.format("Country [%] does not exist", countryId);
            return new TaskExecutionException(s);
        });
        City city = mapper.cityDtoToCity(cityDto);
        //TODO: what happens if cityDto has been set an id before persisting to DB?
        country.add(city);
        city = cityRepository.save(city);
        return assembler.toModel(city);
    }

    /**
     * Updating an existing item belong to the root object.
     *
     * @param countryId (Required) the country id
     * @param cityDto   (Required) the data of city to be updated
     * @param cityId    (Required) the id of the city to be updated
     * @return
     */
    @Override
    public CityDto update(Integer countryId, CityDto cityDto, Integer cityId) {
        Assert.notNull(countryId, "Country id must not be null!");
        Assert.notNull(cityDto, "City data must not be null!");
        Assert.notNull(cityId, "City id must not be null!");
        //
        Optional<City> result = cityRepository.findByIdAndCountry_Id(cityId, countryId);
        City city = result.orElseThrow(() -> {
            String s = String.format("Failed to search the city [%d] of the country [%d]", cityId, countryId);
            return new TaskExecutionException(s);
        });
        /**
         * Use mapstruct to map the nonnull attributes from dto to entity.
         * (https://www.baeldung.com/spring-data-partial-update)
         */
        mapper.updateCityFromCityDto(cityDto, city);
        city = cityRepository.save(city);
        //
        return assembler.toModel(city);
    }

    /**
     * Deleting an city from the country.
     *
     * @param countryId (Required) the root ID
     * @param cityId    (Required) the id of the item to be deleted
     */
    @Override
    public void delete(Integer countryId, Integer cityId) {
        Assert.notNull(countryId, "Country id must not be null!");
        Assert.notNull(cityId, "City id must not be null!");
        cityRepository.deleteByIdAndCountry_Id(cityId, countryId);
    }

    //===========================================================================================
    // Implement methods from SimpleService served for the Simple Controller
    //===========================================================================================

    /**
     * Find all data from database. The result is not paginated.
     *
     * @param withCityIds (Optional) filtering the result by the level two id
     * @param withName    (Optional) filtering the result with the given text.
     * @return
     */
    @Override
    public CollectionModel<CityDto> findAll(List<Integer> withCityIds, String withName) {
        CollectionModel<CityDto> collectionModel = super.findAll(withCityIds, withName);
        collectionModel.add(linkTo(methodOn(CitySimpleController.class).findList(withCityIds, withName,
                null, null)).withSelfRel().expand());
        //
        return collectionModel;
    }

}
