package vn.com.ezmobi.ezhealth.ezhuserservice.web.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.ezmobi.ezhealth.ezhuserservice.services.AddressService;
import vn.com.ezmobi.ezhealth.ezhuserservice.services.SimpleService;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.model.AddressDto;

import java.util.List;

/**
 * Created by ezmobivietnam on 2021-02-01.
 */

@Slf4j
@RequestMapping(AddressSimpleController.BASE_URL)
@RestController
public class AddressSimpleController extends AbstractSimpleController<AddressDto, Integer> {

    public static final String BASE_URL = "/api/addresses";

    private final AddressService addressService;

    public AddressSimpleController(AddressService addressService) {
        this.addressService = addressService;
    }

    /**
     * Find and return a list of data.
     *
     * @param withAddress (Optional) not null value will be used to search data by name.
     * @param page        (Optional) the page number start from 0. Not null value will be used to config pagination.
     * @param size        (Optional) the size (number of items) in each page. Not null value will be used to config pagination.
     * @return
     */
    @Override
    @GetMapping
    public ResponseEntity<CollectionModel<AddressDto>> findList(
            @RequestParam(name = "withAddress", required = false) String withAddress,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size) {
        return super.findList(withAddress, page, size);
    }

    @Override
    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam(name = "ids", required = false) List<Integer> addressIds) {
        return super.delete(addressIds);
    }

    @Override
    protected SimpleService getService() {
        return addressService;
    }
}
