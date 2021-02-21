package vn.com.ezmobi.ezhealth.ezhuserservice.domain;

import lombok.*;
import vn.com.ezmobi.framework.domain.NamedEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@Data
@Entity
@Table(name = "city")
public class City extends NamedEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "is_capital", nullable = false)
    private Boolean capital;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Address> addresses;

    @Builder
    public City(int id, String name, Boolean capital, Country country) {
        super.setId(id);
        super.setName(name);
        this.capital = capital;
        this.country = country;
    }

    public Set<Address> getAddresses() {
        if (Objects.isNull(this.addresses)) {
            addresses = new HashSet<>();
        }
        return addresses;
    }

    public void add(Address address) {
        address.setCity(this);
        getAddresses().add(address);
    }
}
