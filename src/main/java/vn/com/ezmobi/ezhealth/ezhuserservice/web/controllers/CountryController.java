package vn.com.ezmobi.ezhealth.ezhuserservice.web.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.com.ezmobi.ezhealth.ezhuserservice.services.CountryService;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.model.CountryDto;
import vn.com.ezmobi.framework.services.BaseRootService;
import vn.com.ezmobi.framework.web.controllers.AbstractRootController;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by ezmobivietnam on 2021-01-04.
 */
@Slf4j
@Validated
@RequestMapping(CountryController.BASE_URL)
@RestController
public class CountryController extends AbstractRootController<CountryDto, Integer> {

    public static final String BASE_URL = "/api/countries";

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    /**
     * Find and return a list of data.
     *
     * @param withIds  (Optional) filtering the result by the entity's ids
     * @param withText (Optional) filtering the result by the given text
     * @param page     (Optional) the page number start from 0. Not null value will be used to config pagination.
     * @param size     (Optional) the size (number of items) in each page. Not null value will be used to config pagination.
     * @return
     */
    @Override
    @GetMapping()
    @PreAuthorize("hasAuthority('country:read')")
    public ResponseEntity<CollectionModel<CountryDto>> findList(
            @RequestParam(name = "ids", required = false) List<Integer> withIds,
            @RequestParam(name = "name", required = false) String withText,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size) {
        log.debug(String.format("Finding country with conditions: ids=[%s], name=[%s], pageNumber=[%d], " +
                "pageSize=[%d]", withIds, withText, page, size));
        return super.findList(withIds, withText, page, size);
    }

    @GetMapping("/{countryId}")
    @PreAuthorize("hasAuthority('country:read')")
    public ResponseEntity<CountryDto> findById(@PathVariable Integer countryId) {
        log.debug("Start finding country (" + countryId + ")");
        return super.findById(countryId);
    }

    @Override
    @PostMapping()
    @PreAuthorize("hasAuthority('country:write')")
    public ResponseEntity<Void> addNew(@Valid @RequestBody CountryDto country) {
        log.debug("Starting adding new country:", country);
        return super.addNew(country);
    }

    @Override
    @PutMapping("/{countryId}")
    @PreAuthorize("hasAuthority('country:write')")
    public ResponseEntity<Void> update(@RequestBody @Valid CountryDto country,
                                       @PathVariable Integer countryId) {
        log.debug(String.format("Start updating country with id: %d with new data: %s", countryId, country));
        return super.update(country, countryId);
    }

    @Override
    @DeleteMapping("/{countryId}")
    @PreAuthorize("hasAuthority('country:delete')")
    public ResponseEntity<Void> delete(@PathVariable Integer countryId) {
        log.debug(String.format("Start deleting country with id [%d]", countryId));
        return super.delete(countryId);
    }

    @Override
    protected BaseRootService getService() {
        return countryService;
    }
}
