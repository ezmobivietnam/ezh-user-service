package vn.com.ezmobi.ezhealth.ezhuserservice.web.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.ezmobi.ezhealth.ezhuserservice.services.CountryService;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.model.CountryDto;

import java.util.List;

/**
 * Created by ezmobivietnam on 2021-01-04.
 */
@Slf4j
@RequestMapping("/api/v1/country")
@RestController
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("findAll")
    public ResponseEntity<List<CountryDto>> findAll() {
        log.info("Start find all county...");
        List<CountryDto> countryDtoList = countryService.findAll();
        log.info("End find all county. Return the data to Client now.");
        return ResponseEntity.ok().body(countryDtoList);
    }
}
