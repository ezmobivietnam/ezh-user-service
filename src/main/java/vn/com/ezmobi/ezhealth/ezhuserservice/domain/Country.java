package vn.com.ezmobi.ezhealth.ezhuserservice.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "country")
public class Country implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "country_id", nullable = false)
    private int countryId;

    @Column(name = "country", nullable = false)
    private String name;

    @Column(name = "last_update", nullable = false)
    private LocalDateTime lastUpdate;

}
