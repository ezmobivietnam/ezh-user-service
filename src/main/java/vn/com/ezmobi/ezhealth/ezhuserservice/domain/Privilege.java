package vn.com.ezmobi.ezhealth.ezhuserservice.domain;

import lombok.Data;
import vn.com.ezmobi.framework.domain.NamedEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Collection;

@Data
@Entity
@Table(name = "privilege")
public class Privilege extends NamedEntity {

    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;
}
