package vn.com.ezmobi.ezhealth.ezhuserservice.web.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.com.ezmobi.ezhealth.ezhuserservice.services.CountryService;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.exceptions.DataNotFoundException;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.model.CountryDto;

import javax.validation.constraints.Min;
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
    public static final String BASE_URL = "/api/v1/countries";

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping()
    public ResponseEntity<List<CountryDto>> findAll(
            @RequestParam(name = "name", required = false) String name) {
        if (Objects.isNull(name) || name.isBlank()) {
            log.debug("Start finding all country...");
            List<CountryDto> countryDtoList = countryService.findAll();
            return ResponseEntity.ok().body(countryDtoList);
        } else {
            log.debug("Start finding country (" + name + ")");
            List<CountryDto> countryDtoList = countryService.findByName(name);
            if (Objects.isNull(countryDtoList) || countryDtoList.isEmpty()) {
                throw new DataNotFoundException();
            }
            return ResponseEntity.ok().body(countryDtoList);
        }
    }

    @GetMapping("/{countryId}")
    public ResponseEntity<CountryDto> findById(@PathVariable @Min(0) int countryId) {
        log.debug("Start finding country (" + countryId + ")");
        Optional<CountryDto> countryDto = countryService.findById(countryId);
        return ResponseEntity.ok().body(countryDto.orElseThrow(DataNotFoundException::new));
    }

}
