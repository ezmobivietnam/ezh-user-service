package vn.com.ezmobi.framework.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

/**
 * Created by ezmobivietnam on 2021-02-20.
 */
@NoRepositoryBean
public interface BaseEntityRepository<BaseEntity, ID>
        extends JpaRepository<BaseEntity, ID>, JpaSpecificationExecutor<BaseEntity> {
    Page<BaseEntity> findAllByIdIn(List<ID> withIds, Pageable pageRequest);

    List<BaseEntity> findAllByIdIn(List<ID> withIds);

    void deleteAllByIdIn(List<ID> ids);
}
