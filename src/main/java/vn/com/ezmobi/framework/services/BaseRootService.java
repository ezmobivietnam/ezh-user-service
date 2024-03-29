package vn.com.ezmobi.framework.services;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Created by ezmobivietnam on 2021-01-15.
 */
@Validated
public interface BaseRootService<T extends RepresentationModel<? extends T>, ID> extends SimpleService<T, ID> {

    Optional<T> findById(ID id);

    T addNew(@Valid T representationModel);

    T update(@Valid T representationModel, ID id);

    void delete(ID id);
}
