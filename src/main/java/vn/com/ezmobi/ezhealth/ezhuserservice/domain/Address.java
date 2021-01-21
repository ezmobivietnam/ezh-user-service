package vn.com.ezmobi.ezhealth.ezhuserservice.domain;

import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "address")
@Data
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id", nullable = false)
    private Integer addressId;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "address2")
    private String address2;

    @Column(name = "district", nullable = false)
    private String district;

    @Column(name = "city_id", nullable = false)
    private Integer cityId;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "last_update", nullable = false)
    @UpdateTimestamp
    private LocalDateTime lastUpdate;

}