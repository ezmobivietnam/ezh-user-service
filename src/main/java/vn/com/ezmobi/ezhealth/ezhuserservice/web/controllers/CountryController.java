package vn.com.ezmobi.ezhealth.ezhuserservice.web.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.com.ezmobi.ezhealth.ezhuserservice.services.BaseRootService;
import vn.com.ezmobi.ezhealth.ezhuserservice.services.CountryService;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.model.CountryDto;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * Created by ezmobivietnam on 2021-01-04.
 */
@Slf4j
@Validated
@RequestMapping(CountryController.BASE_URL)
@RestController
public class CountryController extends AbstractRootController<CountryDto> {

    public static final String BASE_URL = "/api/countries";
    public static final int DEFAULT_PAGE_NUMBER = 0;
    public static final int DEFAULT_PAGE_SIZE = 20;

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping()
    public ResponseEntity<CollectionModel<CountryDto>> findList(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size) {

        log.debug(String.format("Finding country with conditions: pageNumber=%d, pageSize=%d, name=%s", page,
                size, name));
        return super.findList(name, page, size);
    }

    @GetMapping("/{countryId}")
    public ResponseEntity<CountryDto> findById(@PathVariable @Min(1) Integer countryId) {
        log.debug("Start finding country (" + countryId + ")");
        return super.findById(countryId);
    }

    @Override
    @PostMapping()
    public ResponseEntity<Void> addNew(@Valid @RequestBody CountryDto country) {
        log.debug("Starting adding new country:", country);
       return super.addNew(country);
    }

    @Override
    @PutMapping("/{countryId}")
    public ResponseEntity<Void> update(@RequestBody @Valid CountryDto country,
                                             @PathVariable @Min(1) Integer countryId) {
        log.debug(String.format("Start updating country with id: %d with new data: %s", countryId, country));
        return super.update(country, countryId);
    }

    @Override
    @DeleteMapping("/{countryId}")
    public ResponseEntity<Void> delete(@PathVariable @Min(1) Integer countryId) {
        log.debug(String.format("Start deleting country with id [%d]", countryId));
        return super.delete(countryId);
    }

    @Override
    protected BaseRootService getService() {
        return countryService;
    }
}
