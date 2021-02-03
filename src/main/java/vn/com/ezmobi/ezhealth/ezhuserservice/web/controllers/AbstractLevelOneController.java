package vn.com.ezmobi.ezhealth.ezhuserservice.web.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import vn.com.ezmobi.ezhealth.ezhuserservice.services.BaseLevelOneService;
import vn.com.ezmobi.ezhealth.ezhuserservice.services.exceptions.DataNotFoundException;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by ezmobivietnam on 2021-01-19.
 */
@Slf4j
@Validated
public abstract class AbstractLevelOneController<T extends RepresentationModel<? extends T>, ID> {

    public static final int DEFAULT_PAGE_NUMBER = 0;
    public static final int DEFAULT_PAGE_SIZE = 20;

    /**
     * Find and return a list of RepresentationModel.
     *
     * @param rootId (Required) The id of the root/owing object
     * @param name   (Optional) null value indicates search all
     * @param page   (Optional) null value indicates searching result is paginated and the page {page} is display
     * @param size   (Optional) null value indicates searching result is paginated and the size of page is {size}
     * @return
     */
    public ResponseEntity<CollectionModel<T>> findList(ID rootId, String name, Integer page,
                                                       Integer size) {
        Assert.notNull(rootId, "Root id must not be null!");

        final String searchingName = (Objects.nonNull(name) && !name.isBlank()) ? name : null;
        boolean isRequestPaging = Objects.nonNull(page) || Objects.nonNull(size);
        if (isRequestPaging) {
            // List with pagination
            int actualPageNumber = Objects.isNull(page) ? DEFAULT_PAGE_NUMBER : page;
            int actualPageSize = Objects.isNull(size) ? DEFAULT_PAGE_SIZE : size;
            PageRequest requestPage = PageRequest.of(actualPageNumber, actualPageSize);
            CollectionModel<T> collectionModel = getService().findPaginated(rootId, searchingName, requestPage);
            return ResponseEntity.ok(collectionModel);
        } else {
            // List all without pagination
            CollectionModel<T> collectionModel;
            if (Objects.nonNull(searchingName)) {
                // list by name
                collectionModel = getService().findByText(rootId, searchingName);
            } else {
                // list all
                collectionModel = getService().findAll(rootId);
            }
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
