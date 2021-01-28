package vn.com.ezmobi.ezhealth.ezhuserservice.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import vn.com.ezmobi.ezhealth.ezhuserservice.domain.Country;
import vn.com.ezmobi.ezhealth.ezhuserservice.repositories.CountryRepository;
import vn.com.ezmobi.ezhealth.ezhuserservice.services.exceptions.TaskExecutionException;
import vn.com.ezmobi.ezhealth.ezhuserservice.utils.assemblers.CountryAssembler;
import vn.com.ezmobi.ezhealth.ezhuserservice.utils.mappers.CountryMapper;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.controllers.CountryController;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.model.CountryDto;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Created by ezmobivietnam on 2021-01-04.
 */
@Slf4j
@Transactional
@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;
    /**
     * The CountryAssembler used CountryAssembler to convert the Entity model to DTO then expending hypertext data
     * before turn to to Presentation layer.
     */
    private final CountryAssembler countryAssembler;

    /**
     * In service classed, the CountryMapper is only used for converting the DTO models to Entity models.
     */
    private final CountryMapper countryMapper;

    private final PagedResourcesAssembler pagedResourcesAssembler;

    public CountryServiceImpl(CountryRepository countryRepository,
                              CountryAssembler countryAssembler,
                              CountryMapper countryMapper,
                              PagedResourcesAssembler pagedResourcesAssembler) {
        //
        this.countryRepository = countryRepository;
        this.countryAssembler = countryAssembler;
        this.countryMapper = countryMapper;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @Override
    public CollectionModel<CountryDto> findPaginated(String nameExp, PageRequest pageRequest) {
        Assert.notNull(pageRequest, "PageRequest must not be null!");
        //
        Page<Country> countryEntityList;
        if (Objects.isNull(nameExp) || nameExp.isBlank()) {
            log.debug("Find all...");
            // find all
            countryEntityList = countryRepository.findAll(pageRequest);
        } else {
            log.debug("Find by name:" + nameExp);
            //find by name
            countryEntityList = countryRepository.findByNameContainingIgnoreCase(nameExp, pageRequest);
        }
        // convert Page<Country> to CollectionModel<CountryDto> then return to caller
        PagedModel<CountryDto> pageModel = pagedResourcesAssembler.toModel(countryEntityList, countryAssembler);
        //
        return pageModel;
    }

    @Override
    public CollectionModel<CountryDto> findAll() {
        log.info("Start finding country data");
        //
        List<Country> countryList = countryRepository.findAll();
        CollectionModel<CountryDto> collectionModel = countryAssembler.toCollectionModel(countryList);
        collectionModel.add(linkTo(methodOn(CountryController.class)
                .findList(null, null, null)).withSelfRel().expand());
        //
        log.info("End finding country data. Will return data to caller right now.");
        return collectionModel;
    }

    @Override
    public Optional<CountryDto> findById(Integer id) {
        Assert.notNull(id, "Country id must not be null!");
        Optional<Country> result = countryRepository.findById(id);
        return result.map(countryAssembler::toModel);
    }

    @Override
    public CollectionModel<CountryDto> findByText(String nameExp) {
        Assert.hasLength(nameExp, "Name must not be null and must not the empty!");
        List<Country> countryList = countryRepository.findByNameContainingIgnoreCase(nameExp);
        CollectionModel<CountryDto> countryDtoModelList = countryAssembler.toCollectionModel(countryList);
        countryDtoModelList.add(linkTo(methodOn(CountryController.class)
                .findList(nameExp, null, null)).withSelfRel().expand());
        return countryDtoModelList;
    }

    @Override
    public CountryDto addNew(CountryDto countryDto) {
        Assert.notNull(countryDto, "Country data must not be null!");
        Country country = countryRepository.save(countryMapper.countryDtoToCountry(countryDto));
        return countryAssembler.toModel(country);
    }

    @Override
    public CountryDto update(CountryDto countryDto, Integer countryId) {
        Assert.notNull(countryDto, "Country data must not be null!");
        Assert.notNull(countryId, "Country id must not be null!");
        Optional<Country> result = countryRepository.findById(countryId);
        Country storedCountry = result.orElseThrow(() ->
                new TaskExecutionException(String.format("Updating country [%d] failed", countryId)));
        countryMapper.updateCountryFromCountryDto(countryDto, storedCountry);
        storedCountry = countryRepository.save(storedCountry);
        return countryAssembler.toModel(storedCountry);
    }

    @Override
    public void delete(final Integer countryId) {
        Assert.notNull(countryId, "Country id must not be null!");
        countryRepository.deleteById(countryId);
    }
}
