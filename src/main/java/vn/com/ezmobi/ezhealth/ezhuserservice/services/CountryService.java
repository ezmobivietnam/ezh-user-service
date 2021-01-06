package vn.com.ezmobi.ezhealth.ezhuserservice.services;

import vn.com.ezmobi.ezhealth.ezhuserservice.web.model.CountryDto;

import java.util.List;
import java.util.Optional;

/**
 * Created by ezmobivietnam on 2021-01-04.
 */
public interface CountryService {

    List<CountryDto> findAll();

    Optional<CountryDto> findById(int id);

    List<CountryDto> findByName(String exp);

    CountryDto addOrUpdate(CountryDto country);

    void delete(int id);
}
