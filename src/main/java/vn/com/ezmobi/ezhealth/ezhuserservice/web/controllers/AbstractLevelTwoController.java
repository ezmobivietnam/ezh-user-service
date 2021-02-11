package vn.com.ezmobi.ezhealth.ezhuserservice.web.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import vn.com.ezmobi.ezhealth.ezhuserservice.services.BaseLevelTwoService;
import vn.com.ezmobi.ezhealth.ezhuserservice.services.exceptions.DataNotFoundException;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.model.CityDto;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by ezmobivietnam on 2021-01-29.
 */
@Slf4j
@Validated
public abstract class AbstractLevelTwoController<T extends RepresentationModel<? extends T>, ID> {

    public static final int DEFAULT_PAGE_NUMBER = 0;
    public static final int DEFAULT_PAGE_SIZE = 20;

    /**
     * Find and return a list of RepresentationModel.
     *
     * @param rootId          (Required) The id of the root object
     * @param levelOneId      (Required) The id of the root object
     * @param withLevelTwoIds (Optional) filtering the result by the level two id
     * @param withText        (Optional) filtering the result with the given text.
     * @param page            (Optional) null value indicates searching result is paginated and the page {page} is display
     * @param size            (Optional) null value indicates searching result is paginated and the size of page is {size}
     * @return
     */
    public ResponseEntity<CollectionModel<T>> findList(ID rootId,
                                                       ID levelOneId,
                                                       List<ID> withLevelTwoIds,
                                                       String withText,
                                                       Integer page,
                                                       Integer size) {

        Assert.notNull(rootId, "Root id must not be null!");
        Assert.notNull(rootId, "Level one id must not be null!");

        boolean isRequestPaging = Objects.nonNull(page) || Objects.nonNull(size);
        if (isRequestPaging) {
            // List with pagination
            int actualPageNumber = Objects.isNull(page) ? DEFAULT_PAGE_NUMBER : page;
            int actualPageSize = Objects.isNull(size) ? DEFAULT_PAGE_SIZE : size;
            PageRequest requestPage = PageRequest.of(actualPageNumber, actualPageSize);
            CollectionModel<T> collectionModel = getService().findPaginated(rootId, levelOneId, withLevelTwoIds,
                    withText, requestPage);
            return ResponseEntity.ok(collectionModel);
        } else {
            // List all without pagination
            CollectionModel<T> collectionModel = getService().findAll(rootId, levelOneId, withLevelTwoIds, withText);
            if (collectionModel.getContent().isEmpty()) {
                throw new DataNotFoundException();
            }
            return ResponseEntity.ok(collectionModel);
        }
    }

    public ResponseEntity<T> findById(ID rootId, ID levelOneId, ID levelTwoId) {
        Optional<T> model = getService().findById(rootId, levelOneId, levelTwoId);
        return ResponseEntity.ok(model.orElseThrow(() -> {
            String s = String.format("Failed to search data with root id=%s, level one id=%s, level two id=", rootId,
                    levelOneId, levelTwoId);
            return new DataNotFoundException(s);
        }));
    }

    public ResponseEntity<Void> addNew(ID rootId, ID levelOneId, @Valid T model) {
        RepresentationModel newModel = getService().addNew(rootId, levelOneId, model);
        return ResponseEntity.created(newModel.getRequiredLink(IanaLinkRelations.SELF_VALUE).toUri()).build();
    }

    public ResponseEntity<Void> update(ID countryId, ID cityId, @Valid T model, ID addressId) {
        //
        getService().update(countryId, cityId, model, addressId);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> delete(ID rootId, ID levelOneId, ID levelTwoId) {
        getService().delete(rootId, levelOneId, levelTwoId);
        return ResponseEntity.noContent().build();
    }

    protected abstract BaseLevelTwoService getService();
}
