package vn.com.ezmobi.ezhealth.ezhuserservice.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Component;
import vn.com.ezmobi.ezhealth.ezhuserservice.services.CountryService;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.model.CountryDto;

/**
 * Created by ezmobivietnam on 2021-01-05.
 */
@Slf4j
@Component
public class DataLoader implements CommandLineRunner {
    private final CountryService countryService;

    public DataLoader(CountryService countryService) {
        this.countryService = countryService;
    }


    @Override
    public void run(String... args) throws Exception {
        log.info("Loading data...");
        CollectionModel<CountryDto> data = countryService.findAll();
        log.info("Number of country is " + data.getContent().size());
    }
}
