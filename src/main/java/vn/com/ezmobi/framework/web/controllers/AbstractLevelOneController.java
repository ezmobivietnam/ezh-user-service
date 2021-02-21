package vn.com.ezmobi.framework.web.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import vn.com.ezmobi.ezhealth.ezhuserservice.services.exceptions.DataNotFoundException;
import vn.com.ezmobi.framework.services.BaseLevelOneService;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by ezmobivietnam on 2021-01-19.
 */
@Slf4j
@Validated
public abstract class AbstractLevelOneController<T extends RepresentationModel<? extends T>, ID>
        extends AbstractController {

    /**
     * Find and return a list of RepresentationModel.
     *
     * @param rootId             (Required) The id of the root/owing object
     * @param withLevelOneIdList (Optional) filtering the result with the given level one ids
     * @param withText           (Optional) filtering the result with given text
     * @param page               (Optional) Non-null value indicates searching result is paginated and the page {page}
     *                           is display
     * @param size               (Optional) Non-null value indicates searching result is paginated and the size of page
     *                           is {size}
     * @return
     */
    public ResponseEntity<CollectionModel<T>> findList(ID rootId,
                                                       List<ID> withLevelOneIdList,
                                                       String withText,
                                                       Integer page,
                                                       Integer size) {
        Assert.notNull(rootId, "Root id must not be null!");

        boolean isRequestPaging = Objects.nonNull(page) || Objects.nonNull(size);
        if (isRequestPaging) {
            // List with pagination
            PageRequest requestPage = getPageRequest(page, size);
            CollectionModel<T> collectionModel = getService().findPaginated(rootId, withLevelOneIdList, withText,
                    requestPage);
            return ResponseEntity.ok(collectionModel);
        } else {
            // List all without pagination
            CollectionModel<T> collectionModel = getService().findAll(rootId, withLevelOneIdList, withText);
            if (collectionModel.getContent().isEmpty()) {
                throw new DataNotFoundException();
            }
            return ResponseEntity.ok(collectionModel);
        }
    }

    public ResponseEntity<T> findById(ID ownerId, ID childId) {
        Optional<T> model = getService().findById(ownerId, childId);
        return ResponseEntity.ok(model.orElseThrow(() -> {
            String s = String.format("Failed to search data with root id=%s, level one id=%s", ownerId, childId);
            return new DataNotFoundException(s);
        }));
    }

    public ResponseEntity<Void> delete(ID ownerId, ID childId) {
        getService().delete(ownerId, childId);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Void> addNew(ID ownerId, @Valid T model) {
        RepresentationModel newModel = getService().addNew(ownerId, model);
        return ResponseEntity.created(newModel.getRequiredLink(IanaLinkRelations.SELF_VALUE).toUri()).build();
    }

    public ResponseEntity<Void> update(ID ownerId, @Valid T model, ID childId) {
        //
        getService().update(ownerId, model, childId);
        return ResponseEntity.ok().build();
    }

    protected abstract BaseLevelOneService getService();
}
