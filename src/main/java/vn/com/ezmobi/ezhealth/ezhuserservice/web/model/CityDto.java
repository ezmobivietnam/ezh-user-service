package vn.com.ezmobi.ezhealth.ezhuserservice.web.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import vn.com.ezmobi.ezhealth.ezhuserservice.domain.Address;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Created by ezmobivietnam on 2021-01-15.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@JsonIgnoreProperties(value = { "addresses" })
public class CityDto extends RepresentationModel<CityDto> {

    @Null
    private Integer id;

    @NotBlank
    @Size(min = 1, max = 50)
    private String name;

    @NotNull
    private Boolean capital;

    private String description;

    @Null
    private LocalDateTime lastUpdate;

    @Null
    private LocalDateTime creationDate;

//    @Null
//    private Set<Address> addresses;
}
