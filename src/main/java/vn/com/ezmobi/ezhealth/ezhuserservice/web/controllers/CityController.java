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
public class CityController extends AbstractLevelOneController<CityDto, Integer> {

    public static final String BASE_URL = "/api/countries/{countryId}/cities";

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    /**
     * Find and return a list of data.
     * Sample usage:
     * http://localhost:8080/api/countries/2/cities/?page=0&size=2&name=a
     *
     * @param countryId (Required) null value indicates search all city without constraint to a country
     * @param name      (Optional) null value indicates search all
     * @param page      (Optional) null value indicates searching result is paginated and the page {page} is display
     * @param size      (Optional) null value indicates searching result is paginated and the size of page is {size}
     * @return
     */
    @Override
    @GetMapping()
    public ResponseEntity<CollectionModel<CityDto>> findList(
            @PathVariable Integer countryId,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size) {

        log.debug(String.format("Finding cities with conditions: countryId=%d, pageNumber=%d, pageSize=%d, name=%s",
                countryId, page, size, name));
        return super.findList(countryId, name, page, size);
    }

    @Override
    @GetMapping("/{cityId}")
    public ResponseEntity<CityDto> findById(@PathVariable Integer countryId,
                                            @PathVariable Integer cityId) {
        log.debug(String.format("Country id=%s, city id=%s", countryId, cityId));
        return super.findById(countryId, cityId);
    }

    @Override
    @PostMapping()
    public ResponseEntity<Void> addNew(@PathVariable Integer countryId, @RequestBody @Valid CityDto model) {
        log.debug(String.format("Adding to the country [%d] the new city [%s]", countryId, model));
        return super.addNew(countryId, model);
    }

    @PutMapping("/{cityId}")
    public ResponseEntity<Void> update(@PathVariable Integer countryId,
                                       @RequestBody @Valid CityDto model,
                                       @PathVariable Integer cityId) {
        log.debug(String.format("Updating city info with country id=[%d], new city data=[%s], city id=[%d]",
                countryId, model, cityId));
        return super.update(countryId, model, cityId);
    }

    @Override
    @DeleteMapping("/{cityId}")
    public ResponseEntity<Void> delete(@PathVariable Integer countryId, @PathVariable Integer cityId) {
        return super.delete(countryId, cityId);
    }

    @Override
    protected BaseLevelOneService getService() {
        return this.cityService;
    }
}
