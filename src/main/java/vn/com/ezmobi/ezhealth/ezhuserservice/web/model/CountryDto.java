package vn.com.ezmobi.ezhealth.ezhuserservice.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

/**
 * Created by ezmobivietnam on 2021-01-04.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CountryDto extends RepresentationModel<CountryDto> {
    private int id;
    private String name;
    private LocalDateTime lastUpdate;
}
