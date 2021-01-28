package vn.com.ezmobi.ezhealth.ezhuserservice.services;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Created by ezmobivietnam on 2021-01-15.
 */
@Validated
public interface BaseRootService<T extends RepresentationModel<? extends T>> extends SimpleService {

    Optional<T> findById(Integer id);

    T addNew(@Valid T representationModel);

    T update(@Valid T representationModel, Integer id);

    void delete(Integer id);
}
