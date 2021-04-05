package vn.com.ezmobi.ezhealth.ezhuserservice.domain;

import lombok.Data;
import vn.com.ezmobi.framework.domain.BaseEntity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Data
@Entity
@Table(name = "user")
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "dob")
    private Date dob;

    @Column(name = "sex")
    private Integer sex;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "locked")
    private Boolean locked;

    @Column(name = "picture")
    private byte[] picture;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = true)
    private Address address;
}
