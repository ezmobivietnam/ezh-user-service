package vn.com.ezmobi.ezhealth.ezhuserservice.web.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import vn.com.ezmobi.ezhealth.ezhuserservice.services.BaseLevelTwoService;
import vn.com.ezmobi.ezhealth.ezhuserservice.services.exceptions.DataNotFoundException;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by ezmobivietnam on 2021-01-29.
 */
@Slf4j
@Validated
public abstract class AbstractLevelTwoController<T extends RepresentationModel<? extends T>> {

    public static final int DEFAULT_PAGE_NUMBER = 0;
    public static final int DEFAULT_PAGE_SIZE = 20;

    /**
     * Find and return a list of RepresentationModel.
     *
     * @param rootId          (Required) The id of the root object
     * @param levelOneId      (Required) The id of the root object
     * @param withLevelTwoIds (Optional) filtering the result by the level two id
     * @param withText        (Optional) filtering the result with searching text. Null value indicates search all
     * @param page            (Optional) null value indicates searching result is paginated and the page {page} is display
     * @param size            (Optional) null value indicates searching result is paginated and the size of page is {size}
     * @return
     */
    public ResponseEntity<CollectionModel<T>> findList(Integer rootId,
                                                       Integer levelOneId,
                                                       List<Integer> withLevelTwoIds,
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

    public ResponseEntity<T> findById(@Min(1) Integer rootId, @Min(1) Integer levelOneId, @Min(1) Integer levelTwoId) {
        Optional<T> model = getService().findById(rootId, levelOneId, levelTwoId);
        return ResponseEntity.ok(model.orElseThrow(() -> {
            String s = String.format("Failed to search data with root id=%s, level one id=%s, level two id=", rootId,
                    levelOneId, levelTwoId);
            return new DataNotFoundException(s);
        }));
    }


    protected abstract BaseLevelTwoService getService();
}
