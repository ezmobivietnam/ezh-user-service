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
import vn.com.ezmobi.ezhealth.ezhuserservice.utils.assemblers.CountryAssembler;
import vn.com.ezmobi.ezhealth.ezhuserservice.utils.mappers.CountryMapper;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.controllers.CountryController;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.exceptions.DataNotFoundException;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.model.CountryDto;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Created by ezmobivietnam on 2021-01-04.
 */
@Slf4j
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
        log.info("Start loading country data");
        //
        List<Country> countryList = countryRepository.findAll();
        List<CountryDto> countryDtoModelList = countryList.stream()
                .map(countryAssembler::toModel)
                .collect(Collectors.toList());
        // wrap the result by CollectionModel
        CollectionModel<CountryDto> collectionModel = CollectionModel.of(countryDtoModelList);
        collectionModel.add(linkTo(methodOn(CountryController.class)
                .findAll(null, null, null)).withSelfRel().expand());
        //
        log.info("End loading country data. Will return data to caller right now.");
        return collectionModel;
    }

    @Override
    public Optional<CountryDto> findById(int id) {
        Optional<Country> result = countryRepository.findById(id);
        return result.map(countryAssembler::toModel);
    }

    @Override
    public CollectionModel<CountryDto> findByName(String nameExp) {
        List<Country> countryList = countryRepository.findByNameContainingIgnoreCase(nameExp);
        CollectionModel<CountryDto> countryDtoModelList = CollectionModel.of(countryList.stream()
                .map(countryAssembler::toModel)
                .collect(Collectors.toList()));
        countryDtoModelList.add(linkTo(methodOn(CountryController.class)
                    .findAll(null, null, nameExp)).withSelfRel().expand());
        return countryDtoModelList;
    }

    @Override
    @Transactional
    public CountryDto add(CountryDto countryDto) {
        Country country = countryRepository.save(countryMapper.countryDtoToCountry(countryDto));
        return countryAssembler.toModel(country);
    }

    @Override
    @Transactional
    public CountryDto update(CountryDto countryDto, final int countryId) {
        Optional<Country> storedCountry = countryRepository.findById(countryId);
        storedCountry.orElseThrow(() -> new DataNotFoundException(String.format("Updating country [%d] failed",
                countryId)));
        countryDto.setId(countryId);
        Country countryEntity = countryRepository.save(countryMapper.countryDtoToCountry(countryDto));
        return countryAssembler.toModel(countryEntity);
    }

    @Override
    @Transactional
    public void delete(final int id) {
        countryRepository.deleteById(id);
    }
}
