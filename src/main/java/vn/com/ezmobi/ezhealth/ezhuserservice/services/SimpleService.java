package vn.com.ezmobi.ezhealth.ezhuserservice.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;

/**
 * Created by ezmobivietnam on 2021-01-18.
 */
public interface SimpleService<T extends RepresentationModel<? extends T>> {

    /**
     * Finding data with pagination .
     *
     * @param whatToFind  (Optional) null value indicates find all otherwise finding by a specific criteria.
     * @param pageRequest (Required) the page request
     * @return
     */
    public CollectionModel<T> findPaginated(String whatToFind, PageRequest pageRequest);

    /**
     * Find all data from database. The result is not paginated.
     *
     * @return
     */
    public CollectionModel<T> findAll();

    /**
     * Find in a specific column of a table for a specific value for example name, address...
     *
     * @param whatToFind something to be found
     * @return
     */
    public CollectionModel<T> findByColumn(String whatToFind);

}
