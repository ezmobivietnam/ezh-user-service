package vn.com.ezmobi.ezhealth.ezhuserservice.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Created by ezmobivietnam on 2021-01-16.
 */
@Validated
public interface BaseLevelOneService<T extends RepresentationModel<? extends T>, ID> extends SimpleService<T, ID> {

    /**
     * Finding data with pagination belong to the root object by rootId.
     *
     * @param rootId             (Required) the root ID
     * @param withLevelOneIdList (Optional) filtering the result with the given level one ids
     * @param withText           (Optional) filtering the result with given text
     * @param pageRequest        (Required) the page request
     * @return
     */
    CollectionModel<T> findPaginated(ID rootId, List<ID> withLevelOneIdList, String withText, PageRequest pageRequest);

    /**
     * Finding data with pagination belong to the root object by rootId.
     *
     * @param rootId             (Required) the root ID
     * @param withLevelOneIdList (Optional) filtering the result with the given level one ids
     * @param withText           (Optional) filtering the result with given text
     * @return
     */
    CollectionModel<T> findAll(ID rootId, List<ID> withLevelOneIdList, String withText);

    /**
     * Finding an item with id is levelOneId which belong to the root/owner id is rootId.
     *
     * @param rootId     (Required) the root ID
     * @param levelOneId (Required) the id of the item to be found
     * @return
     */
    Optional<T> findById(ID rootId, ID levelOneId);

    /**
     * Adding new items to the root object.
     *
     * @param rootId              (Required) the root ID
     * @param representationModel (Required) the data to be added
     * @return
     */
    T addNew(ID rootId, @Valid T representationModel);

    /**
     * Updating an existing item belong to the root object.
     *
     * @param rootId              (Required) the root ID
     * @param representationModel (Required) the data to be updated
     * @param levelOneId          (Required) the id of the item to be updated
     * @return
     */
    T update(ID rootId, @Valid T representationModel, ID levelOneId);

    /**
     * Deleting an item from the root object.
     *
     * @param rootId     (Required) the root ID
     * @param levelOneId (Required) the id of the item to be deleted
     */
    void delete(ID rootId, ID levelOneId);
}
