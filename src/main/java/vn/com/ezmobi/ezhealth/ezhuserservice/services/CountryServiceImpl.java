package vn.com.ezmobi.ezhealth.ezhuserservice.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.com.ezmobi.ezhealth.ezhuserservice.domain.Country;
import vn.com.ezmobi.ezhealth.ezhuserservice.repositories.CountryRepository;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.mappers.CountryMapper;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.model.CountryDto;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by ezmobivietnam on 2021-01-04.
 */
@Slf4j
@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    public CountryServiceImpl(CountryRepository countryRepository, CountryMapper countryMapper) {
        this.countryRepository = countryRepository;
        this.countryMapper = countryMapper;
    }

    @Override
    public List<CountryDto> findAll() {
        log.info("Start loading country data");
        //
        List<Country> countryList = countryRepository.findAll();
        List<CountryDto> countryDtoList = countryList.stream()
                .map(countryMapper::countryToCountryDto)
                .collect(Collectors.toList());
        //
        log.info("End loading country data. Will return data to caller right now.");
        return countryDtoList;
    }

    @Override
    public Optional<CountryDto> findById(int id) {
        Optional<Country> result = countryRepository.findById(id);
        return result.map(countryMapper::countryToCountryDto);
    }

    @Override
    public List<CountryDto> findByName(String exp) {
        List<Country> countryList = countryRepository.findByNameIsLike(exp);
        return countryList
                .stream()
                .map(countryMapper::countryToCountryDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CountryDto addOrUpdate(CountryDto countryDto) {
        Country country = countryRepository.save(countryMapper.countryDtoToCountry(countryDto));
        return countryMapper.countryToCountryDto(country);
    }

    @Override
    @Transactional
    public void delete(int id) {
        countryRepository.deleteById(id);
    }
}
