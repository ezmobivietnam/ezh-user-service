package vn.com.ezmobi.ezhealth.ezhuserservice.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import vn.com.ezmobi.ezhealth.ezhuserservice.domain.Country;
import vn.com.ezmobi.ezhealth.ezhuserservice.repositories.CountryRepository;
import vn.com.ezmobi.ezhealth.ezhuserservice.services.exceptions.TaskExecutionException;
import vn.com.ezmobi.ezhealth.ezhuserservice.utils.assemblers.CountryAssembler;
import vn.com.ezmobi.ezhealth.ezhuserservice.utils.mappers.CountryMapper;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.controllers.CountryController;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.model.CountryDto;
import vn.com.ezmobi.framework.services.AbstractNamedEntitySimpleService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Created by ezmobivietnam on 2021-01-04.
 */
@Slf4j
@Transactional
@Service
public class CountryServiceImpl extends AbstractNamedEntitySimpleService<CountryDto, Integer> implements CountryService {

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
        super(countryRepository, countryAssembler, pagedResourcesAssembler);
        //
        this.countryRepository = countryRepository;
        this.countryAssembler = countryAssembler;
        this.countryMapper = countryMapper;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @Override
    public Optional<CountryDto> findById(Integer id) {
        Assert.notNull(id, "Country id must not be null!");
        Optional<Country> result = countryRepository.findById(id);
        return result.map(countryAssembler::toModel);
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

    //===========================================================================================
    // Implement methods from SimpleService served for the Simple Controller
    //===========================================================================================

    /**
     * Find all data from database. The result is not paginated.
     *
     * @param withIds  (Optional) filtering the result by the country's ids
     * @param withName (Optional) filtering the result with the given name.
     * @return
     */
    @Override
    public CollectionModel<CountryDto> findAll(List<Integer> withIds, String withName) {
        CollectionModel<CountryDto> collectionModel = super.findAll(withIds, withName);
        collectionModel.add(linkTo(methodOn(CountryController.class).findList(withIds, withName,
                null, null)).withSelfRel().expand());
        //
        return collectionModel;
    }

}
