package vn.com.ezmobi.ezhealth.ezhuserservice.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import vn.com.ezmobi.ezhealth.ezhuserservice.domain.City;
import vn.com.ezmobi.ezhealth.ezhuserservice.repositories.CityRepository;
import vn.com.ezmobi.ezhealth.ezhuserservice.utils.assemblers.CityAssembler;
import vn.com.ezmobi.ezhealth.ezhuserservice.utils.mappers.CityMapper;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.model.CityDto;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by ezmobivietnam on 2021-01-15.
 */
@Slf4j
@Service
public class CityServiceImpl implements CityService {

    private final CityRepository repository;
    private final CityMapper mapper;
    private final CityAssembler assembler;
    private final PagedResourcesAssembler pagedResourcesAssembler;

    public CityServiceImpl(CityRepository repository,
                           CityMapper mapper,
                           CityAssembler assembler,
                           PagedResourcesAssembler pagedResourcesAssembler) {
        this.repository = repository;
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
        Assert.notNull(countryId, "Country Id must not be null!");
        Assert.notNull(pageRequest, "PageRequest must not be null!");
        //
        log.debug(String.format("Country id=%d, country name=%s, pageRequest=%s", countryId, name, pageRequest));
        Page<City> cityEntityPage;
        if (Objects.isNull(name) || name.isBlank()) {
            // find all cities belong to a country
            cityEntityPage = repository.findAllByCountry_Id(countryId, pageRequest);
        } else {
            // finding pagination by name
            cityEntityPage = repository.findAllByNameContainingIgnoreCaseAndCountry_Id(name, countryId,
                    pageRequest);
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
        List<City> cityEntityList = repository.findAllByCountry_Id(countryId);
        return assembler.toCollectionModel(cityEntityList);
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
        Optional<City> cityEntity = repository.findByIdAndCountry_Id(cityId, countryId);
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
    public CollectionModel<CityDto> findByName(Integer countryId, String name) {
        Assert.notNull(countryId, "Country id must not be null!");
        Assert.notNull(name, "City name must not be null!");
        List<City> cityEntityList = repository.findAllByNameContainingIgnoreCaseAndCountry_Id(name, countryId);
        return assembler.toCollectionModel(cityEntityList);
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
        return null;
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
        return null;
    }

    /**
     * Deleting an city from the country.
     *
     * @param rootId (Required) the root ID
     * @param cityId (Required) the id of the item to be deleted
     */
    @Override
    public void delete(Integer rootId, Integer cityId) {
        Assert.notNull(rootId, "Country id must not be null!");
        Assert.notNull(rootId, "City id must not be null!");
    }
}
