package vn.com.ezmobi.ezhealth.ezhuserservice.domain;

import lombok.Data;
import vn.com.ezmobi.framework.domain.NamedEntity;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@Table(name = "role")
public class Role extends NamedEntity {

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;

    @ManyToMany
    @JoinTable(
            name = "roles_privileges",
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "privilege_id", referencedColumnName = "id"))
    private Collection<Privilege> privileges;
}
