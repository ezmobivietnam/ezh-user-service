package vn.com.ezmobi.ezhealth.ezhuserservice.web.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.ezmobi.ezhealth.ezhuserservice.services.CountryService;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.exceptions.DataNotFoundException;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.model.CountryDto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by ezmobivietnam on 2021-01-04.
 */
@Slf4j
@Validated
@RequestMapping(CountryController.BASE_URL)
@RestController
public class CountryController {
    public static final String BASE_URL = "/api/v1/country";

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("findAll")
    public ResponseEntity<List<CountryDto>> findAll() {
        log.debug("Start finding all country...");
        List<CountryDto> countryDtoList = countryService.findAll();
        log.debug("End finding all country. Return the data to Client now.");
        return ResponseEntity.ok().body(countryDtoList);
    }

    @GetMapping("/{countryId}")
    public ResponseEntity<CountryDto> findById(@PathVariable @Min(0) int countryId) {
        log.debug("Start finding country (" + countryId + ")");
        Optional<CountryDto> countryDto = countryService.findById(countryId);
        log.debug("End finding country. Return the data to Client now.");
        return ResponseEntity.ok().body(countryDto.orElseThrow(DataNotFoundException::new));
    }

    @GetMapping("/findByName/{countryName}")
    public ResponseEntity<List<CountryDto>> findByName(@PathVariable @NotBlank String countryName) {
        log.debug("Start finding country (" + countryName + ")");
        List<CountryDto> countryDtoList = countryService.findByName(countryName);
        if (Objects.isNull(countryDtoList) || countryDtoList.isEmpty()) {
            throw new DataNotFoundException();
        }
        log.debug("End finding country. Return the data to Client now.");
        return ResponseEntity.ok().body(countryDtoList);
    }
}
