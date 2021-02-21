package vn.com.ezmobi.framework.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by ezmobivietnam on 2021-02-20.
 */
@NoArgsConstructor
@Data
@MappedSuperclass
public class NamedEntity extends BaseEntity {
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "name", nullable = false)
    private String name;
}
