package vn.com.ezmobi.ezhealth.ezhuserservice.web.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.ezmobi.framework.web.model.NamedDto;

import javax.validation.constraints.Null;
import java.util.Set;

/**
 * Ref: https://www.baeldung.com/jackson-ignore-properties-on-serialization
 * <p>
 * Created by ezmobivietnam on 2021-01-04.
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(value = {"cities"})
public class CountryDto extends NamedDto<CountryDto> {

    private String description;
    @Null
    private Set<CityDto> cities;

    @Builder
    public CountryDto(Integer id, String name, String description, Set<CityDto> cities) {
        super.setId(id);
        super.setName(name);
        this.description = description;
        this.cities = cities;
    }
}
