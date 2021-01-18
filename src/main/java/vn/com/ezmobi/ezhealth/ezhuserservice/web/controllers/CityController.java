package vn.com.ezmobi.ezhealth.ezhuserservice.web.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.com.ezmobi.ezhealth.ezhuserservice.services.CityService;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.model.CityDto;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Objects;

/**
 * Created by ezmobivietnam on 2021-01-15.
 */
@Slf4j
@Validated
@RequestMapping(CityController.BASE_URL)
@RestController
public class CityController {

    public static final String BASE_URL = "/api/countries/{countryId}/cities";
    public static final int DEFAULT_PAGE_NUMBER = 0;
    public static final int DEFAULT_PAGE_SIZE = 20;
    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping()
    public ResponseEntity<CollectionModel<CityDto>> findList(
            @PathVariable Integer countryId,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size) {

        log.debug(String.format("Finding cities with conditions: countryId=%d, pageNumber=%d, pageSize=%d, name=%s",
                countryId, page, size, name));
        final String searchingName = (Objects.nonNull(name) && !name.isBlank()) ? name : null;
        boolean isRequestPaging = Objects.nonNull(page) || Objects.nonNull(size);
        if (isRequestPaging) {
            // List with pagination
            int actualPageNumber = Objects.isNull(page) ? DEFAULT_PAGE_NUMBER : page;
            int actualPageSize = Objects.isNull(size) ? DEFAULT_PAGE_SIZE : size;
            PageRequest requestPage = PageRequest.of(actualPageNumber, actualPageSize);
            CollectionModel<CityDto> collectionModel = cityService.findPaginated(countryId, searchingName, requestPage);
            return ResponseEntity.ok(collectionModel);
        } else {
            // List all without pagination
            CollectionModel<CityDto> collectionModel;
            if (Objects.nonNull(searchingName)) {
                // list by name
                collectionModel = cityService.findByName(countryId, searchingName);
            } else {
                // list all
                collectionModel = cityService.findAll(countryId);
            }
            return ResponseEntity.ok(collectionModel);
        }
    }

    @GetMapping("/{cityId}")
    public ResponseEntity<CityDto> findById(@PathVariable @Min(1) Integer countryId,
                                            @PathVariable @Min(1) Integer cityId) {
        return null;
    }

    @PostMapping()
    public ResponseEntity<Void> addNew(@PathVariable @Min(1) Integer countryId, @RequestBody @Valid CityDto model) {
        return null;
    }

    @PutMapping("/{cityId}")
    public ResponseEntity<Void> update(@PathVariable @Min(1) Integer countryId,
                                          @RequestBody @Valid CityDto model,
                                          @PathVariable @Min(1) int cityId) {
        return null;
    }

    @DeleteMapping("/{cityId}")
    public ResponseEntity<Void> delete(@PathVariable @Min(1) Integer countryId, @PathVariable @Min(1) int cityId) {
        return null;
    }
}
