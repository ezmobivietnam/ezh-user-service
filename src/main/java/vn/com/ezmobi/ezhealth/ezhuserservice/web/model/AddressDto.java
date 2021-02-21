package vn.com.ezmobi.ezhealth.ezhuserservice.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Created by ezmobivietnam on 2021-01-29.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDto extends RepresentationModel<AddressDto> {

    @Null
    private Integer id;

    @NotBlank
    @Size(min = 1, max = 50)
    private String address;

    private String address2;

    @NotBlank
    @Size(min = 1, max = 20)
    private String district;

    @Size(max = 10)
    private String postalCode;

//    @NotBlank
//    @Size(max = 20)
    private String phone;

    @Null
    private LocalDateTime lastUpdate;

    @Null
    private LocalDateTime creationDate;
}
