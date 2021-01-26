package vn.com.ezmobi.ezhealth.ezhuserservice.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;

import java.util.Optional;

/**
 * Created by ezmobivietnam on 2021-01-16.
 */
public interface BaseLevelOneService<T extends RepresentationModel<? extends T>> extends SimpleService {

    /**
     * Finding data with pagination belong to the root object by rootId.
     *
     * @param rootId      (Required) the root ID
     * @param nameExp     (Optional) the searching item name
     * @param pageRequest (Required) the page request
     * @return
     */
    CollectionModel<T> findPaginated(Integer rootId, String nameExp, PageRequest pageRequest);

    /**
     * Finding all data (without pagination) belong to the root object by rootId.
     *
     * @param rootId (Required) the root ID
     * @return
     */
    CollectionModel<T> findAll(Integer rootId);

    /**
     * Finding an item with id is levelOneId which belong to the root/owner id is rootId.
     *
     * @param rootId     (Required) the root ID
     * @param levelOneId (Required) the id of the item to be found
     * @return
     */
    Optional<T> findById(Integer rootId, Integer levelOneId);

    /**
     * Finding items belong to a root object by name.
     *
     * @param rootId (Required) the root ID
     * @param name   (Required) the name of the item to be found
     * @return
     */
    CollectionModel<T> findByText(Integer rootId, String name);

    /**
     * Adding new items to the root object.
     *
     * @param rootId
     * @param representationModel
     * @return
     */
    T addNew(Integer rootId, T representationModel);

    /**
     * Updating an existing item belong to the root object.
     *
     * @param rootId              (Required) the root ID
     * @param representationModel (Required) the data to be updated
     * @param levelOneId          (Required) the id of the item to be updated
     * @return
     */
    T update(Integer rootId, T representationModel, Integer levelOneId);

    /**
     * Deleting an item from the root object.
     *
     * @param rootId     (Required) the root ID
     * @param levelOneId (Required) the id of the item to be deleted
     * @return the number of record be deleted
     */
    int delete(Integer rootId, Integer levelOneId);
}
