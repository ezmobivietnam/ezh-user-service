package vn.com.ezmobi.ezhealth.ezhuserservice.services;

import org.springframework.hateoas.RepresentationModel;

import java.util.Optional;

/**
 * Created by ezmobivietnam on 2021-01-15.
 */
public interface BaseRootService<T extends RepresentationModel<? extends T>> extends SimpleService {

    Optional<T> findById(Integer id);

    T addNew(T representationModel);

    T update(T representationModel, int countryId);

    void delete(Integer id);
}
