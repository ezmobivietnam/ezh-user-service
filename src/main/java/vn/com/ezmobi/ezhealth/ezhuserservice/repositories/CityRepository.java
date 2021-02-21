package vn.com.ezmobi.ezhealth.ezhuserservice.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.com.ezmobi.ezhealth.ezhuserservice.domain.City;
import vn.com.ezmobi.framework.repositories.NamedEntityRepository;

import java.util.List;
import java.util.Optional;

public interface CityRepository extends NamedEntityRepository<City, Integer> {

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

    void deleteByIdAndCountry_Id(Integer cityId, Integer countryId);

}