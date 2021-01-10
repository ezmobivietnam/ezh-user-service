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
    public static final String BASE_URL = "/api/v1/countries";

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping()
    public ResponseEntity<CollectionModel<CountryDto>> findAll(
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(name = "name", required = false) String name) {

        log.debug(String.format("Finding country with conditions: pageNumber=%d, pageSize=%d, name=%s", pageNumber,
                pageSize, name));

        // isRequestPaging is TRUE means pageNumber not null OR pageSize not null OR both not null
        boolean isRequestPaging = Objects.nonNull(pageNumber) || Objects.nonNull(pageSize);
        if (isRequestPaging) {
            log.debug("Start finding country with pagination");
            // Be aware that the below method call will throw RuntimeException of the ALL params are invalid
            PageRequest page = PageRequest.of(pageNumber, pageSize);
            CollectionModel<CountryDto> countryDtoPageList = countryService.findAndPaginated(name, page);
            return ResponseEntity.ok().body(countryDtoPageList);
        } else {
            //
            // RETURN ALL DATA WITHOUT PAGINATION
            //
            CollectionModel<CountryDto> countryDtoList;
            if (Objects.nonNull(name) && !name.isBlank()) {
                log.debug("Start finding country (" + name + ")");
                //Search by name
                countryDtoList = countryService.findByName(name);
            } else {
                log.debug("Start finding all country...");
                // Find all records and return all
                countryDtoList = countryService.findAll();
            }
            // Add self reference to the model list
            countryDtoList.add(linkTo(methodOn(CountryController.class)
                    .findAll(pageNumber, pageSize, name)).withSelfRel().expand());
            return ResponseEntity.ok().body(countryDtoList);
        }
    }

    @GetMapping("/{countryId}")
    public ResponseEntity<CountryDto> findById(@PathVariable @Min(1) int countryId) {
        log.debug("Start finding country (" + countryId + ")");
        Optional<CountryDto> countryDto = countryService.findById(countryId);
        return ResponseEntity.ok().body(countryDto.orElseThrow(DataNotFoundException::new));
    }

}
