package vn.com.ezmobi.framework.web.controllers;

import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import vn.com.ezmobi.framework.services.BaseRootService;
import vn.com.ezmobi.ezhealth.ezhuserservice.services.exceptions.DataNotFoundException;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Created by ezmobivietnam on 2021-01-15.
 */
@Validated
public abstract class AbstractRootController<T extends RepresentationModel<? extends T>, ID>
        extends AbstractSimpleController<T, ID> {

    /**
     * Adding new model.
     *
     * @param model
     * @return ResponseEntity.created(URI location)
     */
    public ResponseEntity<Void> addNew(@Valid T model) {
        RepresentationModel newModel = getService().addNew(model);
        return ResponseEntity.created(newModel.getRequiredLink(IanaLinkRelations.SELF_VALUE).toUri()).build();
    }

    /**
     * Update an existing model.
     *
     * @param model
     * @param id
     * @return ResponseEntity.ok()
     */
    public ResponseEntity<Void> update(@Valid T model, ID id) {
        getService().update(model, id);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<T> findById(ID id) {
        Optional<T> model = getService().findById(id);
        return ResponseEntity.ok().body(model.orElseThrow(DataNotFoundException::new));
    }

    public ResponseEntity<Void> delete(ID id) {
        getService().delete(id);
        return ResponseEntity.noContent().build();
    }

    protected abstract BaseRootService getService();
}
