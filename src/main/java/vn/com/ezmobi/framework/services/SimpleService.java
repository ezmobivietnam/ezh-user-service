package vn.com.ezmobi.framework.services;

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
     * @param withIds          (Optional) filtering the result by the entity's ids
     * @param withFilterString (Optional) filtering the result with the given text.
     * @param pageRequest      (Required) the page request
     * @return
     */
    public CollectionModel<T> findPaginated(List<ID> withIds, String withFilterString, PageRequest pageRequest);

    /**
     * Find all data from database. The result is not paginated.
     *
     * @param withIds          (Optional) filtering the result by the level two id
     * @param withFilterString (Optional) filtering the result with the given text.
     * @return
     */
    public CollectionModel<T> findAll(List<ID> withIds, String withFilterString);

    public void deleteAllByIds(List<ID> ids);
}
