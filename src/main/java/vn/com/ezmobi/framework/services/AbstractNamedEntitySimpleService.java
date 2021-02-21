package vn.com.ezmobi.framework.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import vn.com.ezmobi.framework.repositories.NamedEntityRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by ezmobivietnam on 2021-02-20.
 */
@Transactional
public abstract class AbstractNamedEntitySimpleService<T extends RepresentationModel<? extends T>, ID>
        implements SimpleService<T, ID> {

    private final NamedEntityRepository repository;
    private final RepresentationModelAssembler modelAssembler;
    private final PagedResourcesAssembler pagedResourcesAssembler;

    public AbstractNamedEntitySimpleService(NamedEntityRepository repository,
                                            RepresentationModelAssembler modelAssembler,
                                            PagedResourcesAssembler pagedResourcesAssembler) {
        this.repository = repository;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.modelAssembler = modelAssembler;
    }

    /**
     * Finding data with pagination .
     *
     * @param withIds     (Optional) filtering the result by the entity's ids
     * @param withName    (Optional) filtering the result with the given name.
     * @param pageRequest (Required) the page request
     * @return
     */
    @Override
    public CollectionModel<T> findPaginated(List<ID> withIds, String withName, PageRequest pageRequest) {
        Assert.notNull(pageRequest, "PageRequest must not be null!");
        Page<T> modelPage;
        if (!CollectionUtils.isEmpty(withIds) && StringUtils.hasLength(withName)) {
            //find all with address ids and containing given address
            modelPage = repository.findAllByIdInAndNameContainingIgnoreCase(withIds, withName, pageRequest);
        } else if (!CollectionUtils.isEmpty(withIds)) {
            // find all with address ids
            modelPage = repository.findAllByIdIn(withIds, pageRequest);
        } else if (StringUtils.hasLength(withName)) {
            // find all containing given address
            modelPage = repository.findAllByNameContainingIgnoreCase(withName, pageRequest);
        } else {
            // find all
            modelPage = repository.findAll(pageRequest);
        }
        return pagedResourcesAssembler.toModel(modelPage, modelAssembler);
    }

    /**
     * Find all data from database. The result is not paginated.
     *
     * @param withIds  (Optional) filtering the result by the level two id
     * @param withName (Optional) filtering the result with the given name.
     * @return
     */
    @Override
    public CollectionModel<T> findAll(List<ID> withIds, String withName) {
        List<T> models;
        if (!CollectionUtils.isEmpty(withIds) && StringUtils.hasLength(withName)) {
            //find all with address ids and containing given address
            models = repository.findAllByIdInAndNameContainingIgnoreCase(withIds, withName);
        } else if (!CollectionUtils.isEmpty(withIds)) {
            // find all with address ids
            models = repository.findAllByIdIn(withIds);
        } else if (StringUtils.hasLength(withName)) {
            // find all containing given address
            models = repository.findAllByNameContainingIgnoreCase(withName);
        } else {
            // find all
            models = repository.findAll();
        }
        //
        return modelAssembler.toCollectionModel(models);
    }

    @Override
    public void deleteAllByIds(List<ID> ids) {
        Assert.notEmpty(ids, "List of IDs must not be null or empty");
        repository.deleteAllByIdIn(ids);
    }

}
