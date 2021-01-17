package vn.com.ezmobi.ezhealth.ezhuserservice.web.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.com.ezmobi.ezhealth.ezhuserservice.services.CountryService;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.exceptions.DataNotFoundException;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.model.CountryDto;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Created by ezmobivietnam on 2021-01-04.
 */
@Slf4j
@Validated
@RequestMapping(CountryController.BASE_URL)
@RestController
public class CountryController {
    public static final String BASE_URL = "/api/countries";
    public static final int DEFAULT_PAGE_NUMBER = 0;
    public static final int DEFAULT_PAGE_SIZE = 20;

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping()
    public ResponseEntity<CollectionModel<CountryDto>> findAll(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(name = "name", required = false) String name) {

        log.debug(String.format("Finding country with conditions: pageNumber=%d, pageSize=%d, name=%s", page,
                size, name));
        final String searchingName = (Objects.nonNull(name) && !name.isBlank()) ? name : null;
        boolean isRequestPaging = Objects.nonNull(page) || Objects.nonNull(size);
        if (isRequestPaging) {
            // Support pagination if at least one of two params pageNumber or pageSize is not null.
            log.debug("Start finding country with pagination");
            int actualPageNumber = Objects.isNull(page) ? DEFAULT_PAGE_NUMBER : page;
            int actualPageSize = Objects.isNull(size) ? DEFAULT_PAGE_SIZE : size;
            PageRequest requestPage = PageRequest.of(actualPageNumber, actualPageSize);
            CollectionModel<CountryDto> countryDtoPageList = countryService.findPaginated(searchingName, requestPage);
            return ResponseEntity.ok().body(countryDtoPageList);
        } else {
            //
            // RETURN ALL DATA WITHOUT PAGINATION
            //
            CollectionModel<CountryDto> countryDtoList;
            if (Objects.nonNull(searchingName)) {
                log.debug("Start finding country (" + searchingName + ")");
                //Search by name
                countryDtoList = countryService.findByName(name);
            } else {
                log.debug("Start finding all country...");
                // Find all records and return all
                countryDtoList = countryService.findAll();
            }
            //
            return ResponseEntity.ok().body(countryDtoList);
        }
    }

    @GetMapping("/{countryId}")
    public ResponseEntity<CountryDto> findById(@PathVariable @Min(1) int countryId) {
        log.debug("Start finding country (" + countryId + ")");
        Optional<CountryDto> countryDto = countryService.findById(countryId);
        return ResponseEntity.ok().body(countryDto.orElseThrow(DataNotFoundException::new));
    }

    @PostMapping()
    public ResponseEntity<CountryDto> add(@Valid @RequestBody CountryDto country) {
        log.debug("Starting adding new country:", country);
        CountryDto newCountryDto = countryService.add(country);
        return ResponseEntity.created(
                linkTo(methodOn(CountryController.class).findById(newCountryDto.getId())).toUri())
                .body(newCountryDto);
    }

    @PutMapping("/{countryId}")
    public ResponseEntity<CountryDto> update(@RequestBody CountryDto country, @PathVariable int countryId) {
        log.debug(String.format("Start updating country with id: %d with new data: %s", countryId, country));
        return ResponseEntity.ok().body(countryService.update(country, countryId));
    }

    @DeleteMapping("/{countryId}")
    public ResponseEntity<Void> delete(@PathVariable int countryId) {
        log.debug(String.format("Start deleting country with id [%d]", countryId));
        countryService.delete(countryId);
        return ResponseEntity.noContent().build();
    }
}
