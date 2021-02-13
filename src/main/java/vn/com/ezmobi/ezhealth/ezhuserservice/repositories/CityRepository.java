package vn.com.ezmobi.ezhealth.ezhuserservice.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.com.ezmobi.ezhealth.ezhuserservice.domain.City;

import java.util.List;
import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Integer>, JpaSpecificationExecutor<City> {

    Page<City> findAllByIdInAndNameContainingIgnoreCaseAndAndCountry_Id(List<Integer> cityIds, String name,
                                                                        Integer countryId, Pageable pageable);
    List<City> findAllByIdInAndNameContainingIgnoreCaseAndAndCountry_Id(List<Integer> cityIds, String name,
                                                                        Integer countryId);

    Page<City> findAllByIdInAndCountry_Id(List<Integer> cityIds, Integer countryId, Pageable pageable);
    List<City> findAllByIdInAndCountry_Id(List<Integer> cityIds, Integer countryId);

    Page<City> findAllByNameContainingIgnoreCaseAndCountry_Id(String cityName,
                                                                     Integer countryId,
                                                                     Pageable pageRequest);
    List<City> findAllByNameContainingIgnoreCaseAndCountry_Id(String name, Integer countryId);

    Page<City> findAllByCountry_Id(Integer countryId, Pageable pageRequest);
    List<City> findAllByCountry_Id(Integer countryId);

    Optional<City> findByIdAndCountry_Id(Integer cityId, Integer countryId);

    void  deleteByIdAndCountry_Id(Integer cityId, Integer countryId);

    Page<City> findAllByIdInAndNameContainingIgnoreCase(List<Integer> withCityIds, String withName, Pageable pageRequest);
    List<City> findAllByIdInAndNameContainingIgnoreCase(List<Integer> withCityIds, String withName);

    Page<City> findAllByIdIn(List<Integer> withCityIds, Pageable pageRequest);
    List<City> findAllByIdIn(List<Integer> withCityIds);

    Page<City> findAllByNameContainingIgnoreCase(String name, Pageable pageRequest);
    List<City> findAllByNameContainingIgnoreCase(String name);

    void deleteAllByIdIn(List<Integer> ids);
}