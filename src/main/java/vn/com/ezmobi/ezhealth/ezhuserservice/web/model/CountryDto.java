package vn.com.ezmobi.ezhealth.ezhuserservice.web.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Ref: https://www.baeldung.com/jackson-ignore-properties-on-serialization
 * <p>
 * Created by ezmobivietnam on 2021-01-04.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(value = {"cities"})
public class CountryDto extends RepresentationModel<CountryDto> {

    @Null
    private Integer id;

    @NotNull
    @Size(min = 1, max = 50)
    private String name;

    @Null
    private Set<CityDto> cities;

    @Null
    private LocalDateTime lastUpdate;
}
