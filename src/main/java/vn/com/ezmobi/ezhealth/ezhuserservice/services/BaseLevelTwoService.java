package vn.com.ezmobi.ezhealth.ezhuserservice.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Created by ezmobivietnam on 2021-01-29.
 */
public interface BaseLevelTwoService<T extends RepresentationModel<? extends T>, ID> extends SimpleService<T, ID> {

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
    CollectionModel<T> findPaginated(ID rootId, ID levelOneId, List<ID> withLevelTwoIdList,
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
    CollectionModel<T> findAll(ID rootId, ID levelOneId, List<ID> withLevelTwoIdList, String withText);

    /**
     * Find a item at level two belong to the level one item and the root item
     *
     * @param rootId     (Required) the ID of the root object
     * @param levelOneId (Required) the ID of the level one object
     * @param levelTwoId (Required) the ID of the level two object
     * @return
     */
    Optional<T> findById(ID rootId, ID levelOneId, ID levelTwoId);

    /**
     * Adding new item to the level one object.
     *
     * @param rootId              (Required) the ID of the root object
     * @param levelOneId          (Required) the ID of the level one object
     * @param representationModel (Required) the presentation model
     * @return
     */
    T addNew(ID rootId, ID levelOneId, @Valid T representationModel);

    /**
     * Updating an existing item belong to the root object.
     *
     * @param rootId              (Required) the root ID
     * @param levelOneId          (Required) the ID of the level one object
     * @param representationModel (Required) the presentation model to be updated
     * @param levelTwoId          (Required) the id of the level two item to be updated
     * @return
     */
    T update(ID rootId, ID levelOneId, @Valid T representationModel, ID levelTwoId);

    /**
     * Deleting an item from the root object.
     *
     * @param rootId     (Required) the root ID which contains the level one object
     * @param levelOneId (Required) the ID of the level one object which contains the level two object
     * @param levelTwoId (Required) the id of the level two item to be deleted
     */
    void delete(ID rootId, ID levelOneId, ID levelTwoId);

}
