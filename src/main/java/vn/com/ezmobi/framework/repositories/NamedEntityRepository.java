package vn.com.ezmobi.framework.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

/**
 * Created by ezmobivietnam on 2021-02-20.
 */
@NoRepositoryBean
public interface NamedEntityRepository<NamedEntity, ID> extends BaseEntityRepository<NamedEntity, ID> {
    Page<NamedEntity> findAllByIdInAndNameContainingIgnoreCase(List<ID> withIds, String withName,
                                                               Pageable pageRequest);

    List<NamedEntity> findAllByIdInAndNameContainingIgnoreCase(List<ID> withIds, String withName);

    Page<NamedEntity> findAllByNameContainingIgnoreCase(String name, Pageable pageRequest);

    List<NamedEntity> findAllByNameContainingIgnoreCase(String name);
}
