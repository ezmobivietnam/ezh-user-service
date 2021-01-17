package vn.com.ezmobi.ezhealth.ezhuserservice.web.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * Created by ezmobivietnam on 2021-01-04.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(value = { "cities" })
public class CountryDto extends RepresentationModel<CountryDto> {

    private int id;

    @NotBlank
    @Size(min = 1, max = 50)
    private String name;

    private Set<CityDto> cities;
}
