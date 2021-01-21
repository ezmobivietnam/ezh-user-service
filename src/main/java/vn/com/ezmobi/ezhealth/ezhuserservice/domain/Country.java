package vn.com.ezmobi.ezhealth.ezhuserservice.domain;

import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@Data
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "country")
public class Country implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "country_id", nullable = false)
    @EqualsAndHashCode.Include
    private int id;

    @Column(name = "country", nullable = false)
    private String name;

    @Column(name = "last_update", nullable = false)
    @UpdateTimestamp
    private LocalDateTime lastUpdate;

    @OneToMany(mappedBy = "country")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<City> cities;

    @Builder
    public Country(int id, String name, Set<City> cities) {
        this.id = id;
        this.name = name;
        this.cities = cities;
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
