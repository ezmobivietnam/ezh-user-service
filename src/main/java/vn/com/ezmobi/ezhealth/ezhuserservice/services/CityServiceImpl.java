package vn.com.ezmobi.ezhealth.ezhuserservice.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import vn.com.ezmobi.ezhealth.ezhuserservice.domain.City;
import vn.com.ezmobi.ezhealth.ezhuserservice.domain.Country;
import vn.com.ezmobi.ezhealth.ezhuserservice.repositories.CityRepository;
import vn.com.ezmobi.ezhealth.ezhuserservice.repositories.CountryRepository;
import vn.com.ezmobi.ezhealth.ezhuserservice.services.exceptions.TaskExecutionException;
import vn.com.ezmobi.ezhealth.ezhuserservice.utils.assemblers.CityAssembler;
import vn.com.ezmobi.ezhealth.ezhuserservice.utils.mappers.CityMapper;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.controllers.CityController;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.controllers.CitySimpleController;
import vn.com.ezmobi.ezhealth.ezhuserservice.services.exceptions.DataNotFoundException;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.model.CityDto;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
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
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final CityMapper mapper;
    private final CityAssembler assembler;
    private final PagedResourcesAssembler pagedResourcesAssembler;

    public CityServiceImpl(CityRepository repository,
                           CountryRepository countryRepository, CityMapper mapper,
                           CityAssembler assembler,
                           PagedResourcesAssembler pagedResourcesAssembler) {
        this.cityRepository = repository;
        this.countryRepository = countryRepository;
        this.mapper = mapper;
        this.assembler = assembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }


    /**
     * Finding cities with pagination belong to the country by countryId.
     *
     * @param countryId   (Required) the country id
     * @param name        (Optional) the searching city name
     * @param pageRequest (Required) the page request
     * @return
     */
    @Override
    public CollectionModel<CityDto> findPaginated(Integer countryId, String name, PageRequest pageRequest) {
        Assert.notNull(countryId, "Country id must not be null!");
        Assert.notNull(pageRequest, "PageRequest must not be null!");
        //
        log.debug(String.format("Country id=%d, country name=%s, pageRequest=%s", countryId, name, pageRequest));
        Page<City> cityEntityPage;
        if (Objects.isNull(name) || name.isBlank()) {
            // find all cities belong to a country
            cityEntityPage = cityRepository.findAllByCountry_Id(countryId, pageRequest);
        } else {
            // finding pagination by name
            cityEntityPage = cityRepository.findAllByNameContainingIgnoreCaseAndCountry_Id(name, countryId,
                    pageRequest);
        }
        return pagedResourcesAssembler.toModel(cityEntityPage, assembler);
    }

    /**
     * Finding cities with pagination WITHOUT constraint to a countryId.
     *
     * @param name
     * @param pageRequest (Required) the page request
     * @return
     */
    @Override
    public CollectionModel<CityDto> findPaginated(String name, PageRequest pageRequest) {
        Assert.notNull(pageRequest, "PageRequest must not be null!");
        //
        log.debug(String.format("Country name=%s, pageRequest=%s", name, pageRequest));
        Page<City> cityEntityPage;
        if (Objects.isNull(name) || name.isBlank()) {
            // find all cities
            cityEntityPage = cityRepository.findAll(pageRequest);
        } else {
            // finding pagination by name
            cityEntityPage = cityRepository.findAllByNameContainingIgnoreCase(name, pageRequest);
        }
        return pagedResourcesAssembler.toModel(cityEntityPage, assembler);
    }

    /**
     * Finding all cities (without pagination) belong to the country by countryId.
     *
     * @param countryId (Required) the country id
     * @return
     */
    @Override
    public CollectionModel<CityDto> findAll(Integer countryId) {
        Assert.notNull(countryId, "Country id must not be null!");
        List<City> cityEntityList = cityRepository.findAllByCountry_Id(countryId);
        CollectionModel<CityDto> collectionModel = assembler.toCollectionModel(cityEntityList);
        collectionModel.add(
                linkTo(methodOn(CityController.class)
                        .findList(countryId, null, null, null)).withSelfRel().expand());
        return collectionModel;
    }

    /**
     * Find and return all cities from DB.
     *
     * @return
     */
    @Override
    public CollectionModel<CityDto> findAll() {
        List<City> cityEntityList = cityRepository.findAll();
        CollectionModel<CityDto> collectionModel = assembler.toCollectionModel(cityEntityList);
        collectionModel.add(
                linkTo(methodOn(CitySimpleController.class)
                        .findList(null, null, null)).withSelfRel().expand());
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
     * Finding cities belong to a country by name.
     *
     * @param countryId (Required) the country id
     * @param name      (Required) the name of the city to be found
     * @return
     */
    @Override
    public CollectionModel<CityDto> findByText(Integer countryId, String name) {
        Assert.notNull(countryId, "Country id must not be null!");
        Assert.notNull(name, "City name must not be null!");
        List<City> cityEntityList = cityRepository.findAllByNameContainingIgnoreCaseAndCountry_Id(name, countryId);
        CollectionModel<CityDto> collectionModel = assembler.toCollectionModel(cityEntityList);
        collectionModel.add(
                linkTo(methodOn(CityController.class)
                        .findList(countryId, name, null, null)).withSelfRel().expand());
        return collectionModel;
    }

    /**
     * Find all cities by the given name without constraint to a country id
     *
     * @param name (Required) searching name
     * @return
     */
    @Override
    public CollectionModel<CityDto> findByText(String name) {
        Assert.notNull(name, "Name must not be null!");
        List<City> cityEntityList = cityRepository.findAllByNameContainingIgnoreCase(name);
        CollectionModel<CityDto> collectionModel = assembler.toCollectionModel(cityEntityList);
        collectionModel.add(
                linkTo(methodOn(CitySimpleController.class)
                        .findList(name, null, null)).withSelfRel().expand());
        return collectionModel;
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
    public int delete(Integer countryId, Integer cityId) {
        Assert.notNull(countryId, "Country id must not be null!");
        Assert.notNull(cityId, "City id must not be null!");
        int deletedRecords = cityRepository.deleteByIdAndCountry_Id(cityId, countryId);
        if (deletedRecords < 1) {
            String s = String.format("Failed to delete the city [%d] of the country [%d]", cityId, countryId);
            throw new TaskExecutionException(s);
        }
        return deletedRecords;
    }
}
