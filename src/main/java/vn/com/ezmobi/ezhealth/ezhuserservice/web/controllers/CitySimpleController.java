package vn.com.ezmobi.ezhealth.ezhuserservice.web.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.ezmobi.ezhealth.ezhuserservice.services.CityService;
import vn.com.ezmobi.framework.services.SimpleService;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.model.CityDto;
import vn.com.ezmobi.framework.web.controllers.AbstractSimpleController;

import java.util.List;

/**
 * Created by ezmobivietnam on 2021-01-18.
 */
@Slf4j
@RequestMapping(CitySimpleController.BASE_URL)
@RestController
public class CitySimpleController extends AbstractSimpleController<CityDto, Integer> {
    public static final String BASE_URL = "/api/cities";

    private final CityService cityService;

    public CitySimpleController(CityService cityService) {
        this.cityService = cityService;
    }

    /**
     * Find and return a list of data.
     *
     * @param withIds  (Optional) filtering the result by the entity's ids
     * @param withName (Optional) filtering the result by the given text
     * @param page     (Optional) the page number start from 0. Not null value will be used to config pagination.
     * @param size     (Optional) the size (number of items) in each page. Not null value will be used to config pagination.
     * @return
     */
    @Override
    @GetMapping
    public ResponseEntity<CollectionModel<CityDto>> findList(
            @RequestParam(name = "ids", required = false) List<Integer> withIds,
            @RequestParam(name = "name", required = false) String withName,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size) {
        log.debug(String.format("Finding cities with conditions: withIds=[%s], withName=[%s], pageNumber=%d, " +
                "pageSize=%d", withIds, withName, page, size));
        return super.findList(withIds, withName, page, size);
    }

    @Override
    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam(name = "ids", required = false) List<Integer> cityIds) {
        return super.delete(cityIds);
    }

    @Override
    protected SimpleService getService() {
        return cityService;
    }

}
