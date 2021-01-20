package vn.com.ezmobi.ezhealth.ezhuserservice.web.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import vn.com.ezmobi.ezhealth.ezhuserservice.services.BaseLevelOneService;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.exceptions.DataNotFoundException;

import javax.validation.constraints.Min;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by ezmobivietnam on 2021-01-19.
 */
@Slf4j
@Validated
public abstract class AbstractLevelOneController<T extends RepresentationModel<? extends T>> {

    public static final int DEFAULT_PAGE_NUMBER = 0;
    public static final int DEFAULT_PAGE_SIZE = 20;

    /**
     * Find and return a list of data.
     *
     * @param ownerId (Required) The id of the root/owing object
     * @param name    (Optional) null value indicates search all
     * @param page    (Optional) null value indicates searching result is paginated and the page {page} is display
     * @param size    (Optional) null value indicates searching result is paginated and the size of page is {size}
     * @return
     */
    public ResponseEntity<CollectionModel<T>> findList(Integer ownerId, String name, Integer page,
                                                       Integer size) {
        Assert.notNull(ownerId, "Country id must not be null!");

        final String searchingName = (Objects.nonNull(name) && !name.isBlank()) ? name : null;
        boolean isRequestPaging = Objects.nonNull(page) || Objects.nonNull(size);
        if (isRequestPaging) {
            // List with pagination
            int actualPageNumber = Objects.isNull(page) ? DEFAULT_PAGE_NUMBER : page;
            int actualPageSize = Objects.isNull(size) ? DEFAULT_PAGE_SIZE : size;
            PageRequest requestPage = PageRequest.of(actualPageNumber, actualPageSize);
            CollectionModel<T> collectionModel = getService().findPaginated(ownerId, searchingName, requestPage);
            return ResponseEntity.ok(collectionModel);
        } else {
            // List all without pagination
            CollectionModel<T> collectionModel;
            if (Objects.nonNull(searchingName)) {
                // list by name
                collectionModel = getService().findByName(ownerId, searchingName);
            } else {
                // list all
                collectionModel = getService().findAll(ownerId);
            }
            return ResponseEntity.ok(collectionModel);
        }
    }

    public ResponseEntity<T> findById(@Min(1) Integer ownerId, @Min(1) Integer childId) {
        Optional<T> model = getService().findById(ownerId, childId);
        return ResponseEntity.ok(model.orElseThrow(() -> {
            String s = String.format("Failed to search data with owner id=%s, chile id=%s", ownerId, childId);
            return new DataNotFoundException(s);
        }));
    }

    public ResponseEntity<Void> delete(@Min(1) Integer ownerId, @Min(1) Integer childId) {
        getService().delete(ownerId, childId);
        return ResponseEntity.noContent().build();
    }

    protected abstract BaseLevelOneService getService();
}
