package vn.com.ezmobi.ezhealth.ezhuserservice.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.com.ezmobi.ezhealth.ezhuserservice.domain.City;
import vn.com.ezmobi.ezhealth.ezhuserservice.domain.Country;

import java.util.List;

public interface CountryRepository
        extends JpaRepository<Country, Integer>, JpaSpecificationExecutor<Country> {

//    Page<Country> findByNameContainingIgnoreCase(String pattern, Pageable pageRequest);

//    List<Country> findByNameContainingIgnoreCase(String pattern);

//    void deleteAllByIdIn(List<Integer> ids);

    Page<Country> findAllByIdInAndNameContainingIgnoreCase(List<Integer> withCountryIds, String withName,
                                                       Pageable pageRequest);
    List<Country> findAllByIdInAndNameContainingIgnoreCase(List<Integer> withCountryIds, String withName);

    Page<Country> findAllByIdIn(List<Integer> withCountryIds, Pageable pageRequest);
    List<Country> findAllByIdIn(List<Integer> withCountryIds);

    Page<Country> findAllByNameContainingIgnoreCase(String name, Pageable pageRequest);
    List<Country> findAllByNameContainingIgnoreCase(String name);

    void deleteAllByIdIn(List<Integer> ids);
}