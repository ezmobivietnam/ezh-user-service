package vn.com.ezmobi.ezhealth.ezhuserservice.web.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.com.ezmobi.ezhealth.ezhuserservice.services.BaseLevelOneService;
import vn.com.ezmobi.ezhealth.ezhuserservice.services.CityService;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.model.CityDto;

import javax.validation.Valid;
import javax.validation.constraints.Min;

/**
 * Ref:
 * 1. https://www.baeldung.com/spring-pathvariable
 * 2. https://programmer.group/spring-method-level-data-validation-validated-method-validation-postprocessor.html
 * <p>
 * Created by ezmobivietnam on 2021-01-15.
 */
@Slf4j
@Validated
@RequestMapping(CityController.BASE_URL)
@RestController
public class CityController extends AbstractLevelOneController<CityDto> {

    public static final String BASE_URL = "/api/countries/{ownerId}/cities";

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    /**
     * Find and return a list of data.
     * Sample usage:
     * http://localhost:8080/api/countries/2/cities/?page=0&size=2&name=a
     *
     * @param ownerId (Required) null value indicates search all city without constraint to a country
     * @param name    (Optional) null value indicates search all
     * @param page    (Optional) null value indicates searching result is paginated and the page {page} is display
     * @param size    (Optional) null value indicates searching result is paginated and the size of page is {size}
     * @return
     */
    @Override
    @GetMapping()
    public ResponseEntity<CollectionModel<CityDto>> findList(
            @PathVariable Integer ownerId,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size) {

        log.debug(String.format("Finding cities with conditions: countryId=%d, pageNumber=%d, pageSize=%d, name=%s",
                ownerId, page, size, name));
        return super.findList(ownerId, name, page, size);
    }

    @Override
    @GetMapping("/{childId}")
    public ResponseEntity<CityDto> findById(@PathVariable @Min(1) Integer ownerId,
                                            @PathVariable @Min(1) Integer childId) {
        log.debug(String.format("Country id=%s, city id=%s", ownerId, childId));
        return super.findById(ownerId, childId);
    }

    @Override
    @PostMapping()
    public ResponseEntity<Void> addNew(@PathVariable @Min(1) Integer ownerId, @RequestBody @Valid CityDto model) {
        log.debug(String.format("Adding to the country [%d] the new city [%s]", ownerId, model));
        return super.addNew(ownerId, model);
    }

    @PutMapping("/{childId}")
    public ResponseEntity<Void> update(@PathVariable @Min(1) Integer ownerId,
                                       @RequestBody @Valid CityDto model,
                                       @PathVariable @Min(1) int childId) {
        log.debug(String.format("Updating city info with country id=[%d], new city data=[%s], city id=[%d]",
                ownerId, model, childId));
        return super.update(ownerId, model, childId);
    }

    @Override
    @DeleteMapping("/{childId}")
    public ResponseEntity<Void> delete(@PathVariable @Min(1) Integer ownerId, @PathVariable @Min(1) Integer childId) {
        return super.delete(ownerId, childId);
    }

    @Override
    protected BaseLevelOneService getService() {
        return this.cityService;
    }
}
