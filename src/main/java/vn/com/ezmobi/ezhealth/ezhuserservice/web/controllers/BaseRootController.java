package vn.com.ezmobi.ezhealth.ezhuserservice.web.controllers;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import javax.validation.constraints.Min;

/**
 * Created by ezmobivietnam on 2021-01-15.
 */
public abstract class BaseRootController<T extends RepresentationModel<? extends T>> {

    /**
     * Find and return a list of data.
     *
     * @param page (Optional) the page number start from 0. Not null value will be used to config pagination.
     * @param size (Optional) the size (number of items) in each page. Not null value will be used to config pagination.
     * @param name (Optional) not null value will be used to search data by name.
     * @return
     */
    public abstract ResponseEntity<CollectionModel<T>> findList(
            /*@RequestParam(value = "page", required = false)*/ Integer page,
            /*@RequestParam(value = "size", required = false)*/ Integer size,
            /*@RequestParam(name = "name", required = false)*/ String name);

    public abstract ResponseEntity<T> findById(@Min(1) int id);

    /**
     * Adding new model.
     *
     * @param model
     * @return ResponseEntity.created(URI location)
     */
    public abstract ResponseEntity<Void> addNew(@Valid T model);

    /**
     * Update an existing model.
     *
     * @param model
     * @param id
     * @return ResponseEntity.ok()
     */
    public abstract ResponseEntity<Void> update(@Valid T model, @Min(1) int id);

    public abstract ResponseEntity<Void> delete(@Min(1) int id);
}
