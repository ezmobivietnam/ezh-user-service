package vn.com.ezmobi.ezhealth.ezhuserservice.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.com.ezmobi.ezhealth.ezhuserservice.domain.Address;
import vn.com.ezmobi.framework.repositories.BaseEntityRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends BaseEntityRepository<Address, Integer> {

    List<Address> findAllByCity_IdAndCity_Country_Id(Integer cityId, Integer countryId);

    Page<Address> findAllByCity_IdAndCity_Country_Id(Integer cityId, Integer countryId, Pageable pageRequest);

    List<Address> findAllByIdInAndCity_IdAndCity_Country_Id(List<Integer> addressIds, Integer cityId,
                                                            Integer countryId);

    Page<Address> findAllByIdInAndCity_IdAndCity_Country_Id(List<Integer> addressIds, Integer cityId,
                                                            Integer countryId, Pageable pageRequest);

    List<Address> findAllByIdInAndAddressContainingIgnoreCaseAndCity_IdAndCity_Country_Id(List<Integer> addressIds,
                                                                                          String containingAddress,
                                                                                          Integer cityId,
                                                                                          Integer countryId);

    Page<Address> findAllByIdInAndAddressContainingIgnoreCaseAndCity_IdAndCity_Country_Id(List<Integer> addressIds,
                                                                                          String containingAddress,
                                                                                          Integer cityId,
                                                                                          Integer countryId,
                                                                                          Pageable pageRequest);

    List<Address> findAllByCity_IdAndCity_Country_IdAndAddressContainingIgnoreCase(Integer cityId, Integer countryId
            , String findingAddress);

    Page<Address> findAllByCity_IdAndCity_Country_IdAndAddressContainingIgnoreCase(Integer cityId, Integer countryId
            , String findingAddress, Pageable pageRequest);

    Optional<Address> findByIdAndCity_IdAndCity_Country_Id(Integer addressId, Integer cityId, Integer countryId);

    void deleteByIdAndCity_IdAndCity_Country_Id(Integer addressId, Integer cityId, Integer countryId);

    //=================================================================================================================
    // JPA APIs for SimpleService
    //=================================================================================================================
    List<Address> findAllByIdInAndAddressContainingIgnoreCase(List<Integer> addressIds, String containingAddress);

    Page<Address> findAllByIdInAndAddressContainingIgnoreCase(List<Integer> addressIds, String containingAddress,
                                                              Pageable pageRequest);


    List<Address> findAllByAddressContainingIgnoreCase(String havingAddress);

    Page<Address> findAllByAddressContainingIgnoreCase(String havingAddress, Pageable pageRequest);

}