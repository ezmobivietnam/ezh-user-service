package vn.com.ezmobi.ezhealth.ezhuserservice.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;

import java.util.Optional;

/**
 * Created by ezmobivietnam on 2021-01-15.
 */
public interface BaseRootService<T extends RepresentationModel<? extends T>> {

    CollectionModel<T> findPaginated(String nameExp, PageRequest pageRequest);

    CollectionModel<T> findAll();

    Optional<T> findById(int id);

    CollectionModel<T> findByName(String exp);

    T addNew(T representationModel);

    T update(T representationModel, int countryId);

    void delete(int id);
}
