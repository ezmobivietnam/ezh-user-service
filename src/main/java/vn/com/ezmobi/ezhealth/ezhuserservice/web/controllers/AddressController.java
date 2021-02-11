package vn.com.ezmobi.ezhealth.ezhuserservice.web.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.com.ezmobi.ezhealth.ezhuserservice.services.AddressService;
import vn.com.ezmobi.ezhealth.ezhuserservice.services.BaseLevelTwoService;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.model.AddressDto;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by ezmobivietnam on 2021-01-29.
 */
@Slf4j
@Validated
@RequestMapping(AddressController.BASE_URL)
@RestController
public class AddressController extends AbstractLevelTwoController<AddressDto, Integer> {

    public static final String BASE_URL = "/api/countries/{countryId}/cities/{cityId}/addresses";

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    /**
     * Find and return a list of RepresentationModel.
     *
     * @param countryId      (Required) The id of the country
     * @param cityId         (Required) The id of the city
     * @param withAddressIds (Optional) filtering the result by the level two id
     * @param withAddress    (Optional) filtering the result with searching text. Null value indicates search all
     * @param page           (Optional) null value indicates searching result is paginated and the page {page} is display
     * @param size           (Optional) null value indicates searching result is paginated and the size of page is {size}
     * @return
     */
    @Override
    @GetMapping
    public ResponseEntity<CollectionModel<AddressDto>> findList(
            @PathVariable Integer countryId,
            @PathVariable Integer cityId,
            @RequestParam(name = "ids", required = false) List<Integer> withAddressIds,
            @RequestParam(name = "address", required = false) String withAddress,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size) {
        return super.findList(countryId, cityId, withAddressIds, withAddress, page, size);
    }

    @Override
    @GetMapping("/{addressId}")
    public ResponseEntity<AddressDto> findById(
            @PathVariable Integer countryId,
            @PathVariable Integer cityId,
            @PathVariable Integer addressId) {

        return super.findById(countryId, cityId, addressId);
    }

    @Override
    @PostMapping
    public ResponseEntity<Void> addNew(@PathVariable Integer countryId,
                                       @PathVariable Integer cityId,
                                       @RequestBody @Valid AddressDto model) {
        return super.addNew(countryId, cityId, model);
    }

    @Override
    @PutMapping("/{addressId}")
    public ResponseEntity<Void> update(@PathVariable Integer countryId,
                                       @PathVariable Integer cityId,
                                       @RequestBody @Valid AddressDto model,
                                       @PathVariable Integer addressId) {
        return super.update(countryId, cityId, model, addressId);
    }

    @Override
    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> delete(@PathVariable Integer countryId,
                                       @PathVariable Integer cityId,
                                       @PathVariable Integer addressId) {
        return super.delete(countryId, cityId, addressId);
    }

    @Override
    protected BaseLevelTwoService getService() {
        return addressService;
    }
}
