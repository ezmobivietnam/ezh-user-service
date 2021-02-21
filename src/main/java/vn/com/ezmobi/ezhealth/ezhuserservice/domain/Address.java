package vn.com.ezmobi.ezhealth.ezhuserservice.domain;

import lombok.Data;
import vn.com.ezmobi.framework.domain.BaseEntity;

import javax.persistence.*;

@Data
@Entity
@Table(name = "address")
public class Address extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "address2")
    private String address2;

    @Column(name = "district", nullable = false)
    private String district;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "phone")
    private String phone;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;
}
