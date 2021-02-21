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
@Table(name = "country")
public class Country extends NamedEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "description")
    private String description;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<City> cities;

    @Builder
    public Country(int id, String name, Set<City> cities) {
        super.setId(id);
        ;
        super.setName(name);
        this.cities = cities;
    }

    public Set<City> getCities() {
        if (Objects.isNull(this.cities)) {
            this.cities = new HashSet<City>();
        }
        return cities;
    }

    /**
     * Adding new city to country.
     *
     * @param city
     */
    public void add(City city) {
        city.setCountry(this);
        getCities().add(city);
    }
}
