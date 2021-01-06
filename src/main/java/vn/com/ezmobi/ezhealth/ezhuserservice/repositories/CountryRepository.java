package vn.com.ezmobi.ezhealth.ezhuserservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.com.ezmobi.ezhealth.ezhuserservice.domain.Country;

import java.util.List;

public interface CountryRepository
        extends JpaRepository<Country, Integer>, JpaSpecificationExecutor<Country> {

    List<Country> findByNameIsLike(String pattern);
}