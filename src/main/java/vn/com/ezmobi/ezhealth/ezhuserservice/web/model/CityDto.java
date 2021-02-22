package vn.com.ezmobi.ezhealth.ezhuserservice.web.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.ezmobi.framework.web.model.NamedDto;

import javax.validation.constraints.NotNull;

/**
 * Created by ezmobivietnam on 2021-01-15.
 */
@Data
@NoArgsConstructor
//@JsonIgnoreProperties(value = { "addresses" })
public class CityDto extends NamedDto<CityDto> {

    @NotNull
    private Boolean capital;
    private String description;

    @Builder
    public CityDto(Integer id, String name, Boolean capital, String description) {
        super.setId(id);
        super.setName(name);
        this.capital = capital;
        this.description = description;
    }

//    @Null
//    private Set<Address> addresses;
}
