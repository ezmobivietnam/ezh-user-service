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
import vn.com.ezmobi.ezhealth.ezhuserservice.web.model.CountryDto;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public CollectionModel<CountryDto> findAndPaginated(String nameExp, PageRequest pageRequest) {
        Assert.notNull(pageRequest, "Name expression must not be null!");
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
    public CollectionModel<CountryDto> findByName(String exp) {
        List<Country> countryList = countryRepository.findByNameContainingIgnoreCase(exp);
        return CollectionModel.of(countryList.stream()
                .map(countryAssembler::toModel)
                .collect(Collectors.toList()));
    }

    @Override
    @Transactional
    public CountryDto add(CountryDto countryDto) {
        Country country = countryRepository.save(countryMapper.countryDtoToCountry(countryDto));
        return countryMapper.countryToCountryDto(country);
    }

    @Override
    public CountryDto update(int countryId, CountryDto country) {
        return null;
    }

    @Override
    @Transactional
    public void delete(int id) {
        countryRepository.deleteById(id);
    }
}
