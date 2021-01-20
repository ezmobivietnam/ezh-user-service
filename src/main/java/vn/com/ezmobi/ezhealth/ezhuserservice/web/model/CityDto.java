package vn.com.ezmobi.ezhealth.ezhuserservice.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Created by ezmobivietnam on 2021-01-15.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CityDto extends RepresentationModel<CityDto> {
    private int id;

    @NotBlank
    @Size(min = 1, max = 50)
    private String name;

    private LocalDateTime lastUpdate;
}
