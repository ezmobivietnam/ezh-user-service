package vn.com.ezmobi.ezhealth.ezhuserservice.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

/**
 * Created by ezmobivietnam on 2021-01-18.
 */
public interface SimpleService<T extends RepresentationModel<? extends T>, ID> {

    /**
     * Finding data with pagination .
     *
     * @param withFilterString (Optional) the string used to filter the searching result
     * @param pageRequest      (Required) the page request
     * @return
     */
    public CollectionModel<T> findPaginated(String withFilterString, PageRequest pageRequest);

    /**
     * Find all data from database. The result is not paginated.
     *
     * @return
     */
    public CollectionModel<T> findAll();

    /**
     * Find in a specific column of a table for a specific value for example name, address...
     *
     * @param withFilterString (Required) the string used to filter the searching result
     * @return
     */
    public CollectionModel<T> findByText(String withFilterString);

    public void deleteAllByIds(List<ID> ids);
}
