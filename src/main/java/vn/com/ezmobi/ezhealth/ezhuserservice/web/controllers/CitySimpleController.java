package vn.com.ezmobi.ezhealth.ezhuserservice.web.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.com.ezmobi.ezhealth.ezhuserservice.services.CityService;
import vn.com.ezmobi.ezhealth.ezhuserservice.services.SimpleService;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.model.CityDto;

/**
 * Created by ezmobivietnam on 2021-01-18.
 */
@Slf4j
@RequestMapping(CitySimpleController.BASE_URL)
@RestController
public class CitySimpleController extends AbstractSimpleController<CityDto> {
    public static final String BASE_URL = "/api/cities";

    private final CityService cityService;

    public CitySimpleController(CityService cityService) {
        this.cityService = cityService;
    }

    @Override
    @GetMapping()
    public ResponseEntity<CollectionModel<CityDto>> findList(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size) {

        log.debug(String.format("Finding cities with conditions: pageNumber=%d, pageSize=%d, name=%s",
                page, size, name));
        return super.findList(name, page, size);
    }

    @Override
    protected SimpleService getService() {
        return cityService;
    }

}