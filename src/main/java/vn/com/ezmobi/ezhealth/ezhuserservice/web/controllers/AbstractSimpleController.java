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
     * @param page (Optional) the page number start from 0. Not null value will be used to config pagination.
     * @param size (Optional) the size (number of items) in each page. Not null value will be used to config pagination.
     * @param name (Optional) not null value will be used to search data by name.
     * @return
     */
    public ResponseEntity<CollectionModel<T>> findList(String name, Integer page, Integer size) {

        final String searchingName = (Objects.nonNull(name) && !name.isBlank()) ? name : null;
        boolean isRequestPaging = Objects.nonNull(page) || Objects.nonNull(size);
        if (isRequestPaging) {
            // List with pagination
            int actualPageNumber = Objects.isNull(page) ? DEFAULT_PAGE_NUMBER : page;
            int actualPageSize = Objects.isNull(size) ? DEFAULT_PAGE_SIZE : size;
            PageRequest requestPage = PageRequest.of(actualPageNumber, actualPageSize);
            CollectionModel<T> collectionModel = getService().findPaginated(searchingName, requestPage);
            return ResponseEntity.ok(collectionModel);
        } else {
            // List all without pagination
            CollectionModel<T> collectionModel;
            if (Objects.nonNull(searchingName)) {
                // list by name
                collectionModel = getService().findByText(searchingName);
            } else {
                // list all
                collectionModel = getService().findAll();
            }
            return ResponseEntity.ok(collectionModel);
        }
    }

    public ResponseEntity<Void> delete(List<ID> ids) {
//        getService().deleteAllByIds(ids);
        return ResponseEntity.noContent().build();
    }

    protected abstract SimpleService getService();
}
