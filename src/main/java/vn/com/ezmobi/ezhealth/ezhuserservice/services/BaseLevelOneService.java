package vn.com.ezmobi.ezhealth.ezhuserservice.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Created by ezmobivietnam on 2021-01-16.
 */
@Validated
public interface BaseLevelOneService<T extends RepresentationModel<? extends T>, ID> extends SimpleService<T, ID> {

    /**
     * Finding data with pagination belong to the root object by rootId.
     *
     * @param rootId           (Required) the root ID
     * @param withFilterString (Optional) the string used to filter the searching result
     * @param pageRequest      (Required) the page request
     * @return
     */
    CollectionModel<T> findPaginated(ID rootId, String withFilterString, PageRequest pageRequest);

    /**
     * Finding all data (without pagination) belong to the root object by rootId.
     *
     * @param rootId (Required) the root ID
     * @return
     */
    CollectionModel<T> findAll(ID rootId);

    /**
     * Finding an item with id is levelOneId which belong to the root/owner id is rootId.
     *
     * @param rootId     (Required) the root ID
     * @param levelOneId (Required) the id of the item to be found
     * @return
     */
    Optional<T> findById(ID rootId, ID levelOneId);

    /**
     * Finding items belong to a root object by name.
     *
     * @param rootId           (Required) the root ID
     * @param withFilterString (Required) the string used to filter the searching result
     * @return
     */
    CollectionModel<T> findByText(ID rootId, String withFilterString);

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
