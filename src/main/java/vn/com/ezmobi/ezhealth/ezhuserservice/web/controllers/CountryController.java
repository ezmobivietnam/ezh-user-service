package vn.com.ezmobi.ezhealth.ezhuserservice.web.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.ezmobi.ezhealth.ezhuserservice.domain.Country;
import vn.com.ezmobi.ezhealth.ezhuserservice.services.CountryService;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.mappers.CountryMapper;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.model.CountryDto;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ezmobivietnam on 2021-01-04.
 */
@RestController
@RequestMapping("/api/v1/user")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/listAll")
    public ResponseEntity<List<CountryDto>> findAll() {
        List<CountryDto> countryDtoList = countryService.findAll();
        return ResponseEntity.ok().body(countryDtoList);
    }
}
