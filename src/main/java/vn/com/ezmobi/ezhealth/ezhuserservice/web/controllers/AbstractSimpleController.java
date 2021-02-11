package vn.com.ezmobi.ezhealth.ezhuserservice.web.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import vn.com.ezmobi.ezhealth.ezhuserservice.services.SimpleService;

import java.util.List;
import java.util.Objects;

/**
 * Created by ezmobivietnam on 2021-01-19.
 */
@Slf4j
public abstract class AbstractSimpleController<T extends RepresentationModel<? extends T>, ID> {
    public static final int DEFAULT_PAGE_NUMBER = 0;
    public static final int DEFAULT_PAGE_SIZE = 20;

    /**
     * Find and return a list of data.
     *
     * @param withIds  (Optional) filtering the result by the entity's ids
     * @param withText (Optional) filtering the result by the given text
     * @param page     (Optional) the page number start from 0. Not null value will be used to config pagination.
     * @param size     (Optional) the size (number of items) in each page. Not null value will be used to config pagination.
     * @return
     */
    public ResponseEntity<CollectionModel<T>> findList(List<ID> withIds, String withText, Integer page, Integer size) {
        boolean isRequestPaging = Objects.nonNull(page) || Objects.nonNull(size);
        if (isRequestPaging) {
            // List with pagination
            int actualPageNumber = Objects.isNull(page) ? DEFAULT_PAGE_NUMBER : page;
            int actualPageSize = Objects.isNull(size) ? DEFAULT_PAGE_SIZE : size;
            PageRequest requestPage = PageRequest.of(actualPageNumber, actualPageSize);
            CollectionModel<T> collectionModel = getService().findPaginated(withIds, withText, requestPage);
            return ResponseEntity.ok(collectionModel);
        } else {
            // List all without pagination
            CollectionModel<T> collectionModel = getService().findAll(withIds, withText);
            return ResponseEntity.ok(collectionModel);
        }
    }

    public ResponseEntity<Void> delete(List<ID> ids) {
        getService().deleteAllByIds(ids);
        return ResponseEntity.noContent().build();
    }

    protected abstract SimpleService getService();
}
