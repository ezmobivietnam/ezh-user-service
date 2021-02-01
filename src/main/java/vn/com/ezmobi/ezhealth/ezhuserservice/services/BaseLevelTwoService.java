package vn.com.ezmobi.ezhealth.ezhuserservice.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.Optional;

/**
 * Created by ezmobivietnam on 2021-01-29.
 */
public interface BaseLevelTwoService<T extends RepresentationModel<? extends T>> extends SimpleService {

    /**
     * Finding data with pagination.
     *
     * @param rootId             (Required) the ID of the root object
     * @param levelOneId         (Required) the ID of the level one object
     * @param withLevelTwoIdList (Optional) filtering the result with the given level two ids
     * @param withText           (Optional) filtering the result with given text
     * @param pageRequest        (Required) the page request
     * @return
     */
    CollectionModel<T> findPaginated(Integer rootId, Integer levelOneId, List<Integer> withLevelTwoIdList,
                                     String withText, PageRequest pageRequest);

    /**
     * Finding all data (without pagination).
     *
     * @param rootId             (Required) the ID of the root object
     * @param levelOneId         (Required) the ID of the level one object
     * @param withLevelTwoIdList (Optional) filtering the result with the given level two ids
     * @param withText           (Optional) filtering the result with given text
     * @return
     */
    CollectionModel<T> findAll(Integer rootId, Integer levelOneId, List<Integer> withLevelTwoIdList, String withText);

    /**
     * Find a item at level two belong to the level one item and the root item
     *
     * @param rootId     (Required) the ID of the root object
     * @param levelOneId (Required) the ID of the level one object
     * @param levelTwoId (Required) the ID of the level two object
     * @return
     */
    Optional<T> findById(Integer rootId, Integer levelOneId, Integer levelTwoId);

}
