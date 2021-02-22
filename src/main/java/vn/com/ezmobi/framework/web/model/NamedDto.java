package vn.com.ezmobi.framework.web.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by ezmobivietnam on 2021-02-21.
 */
@NoArgsConstructor
@Data
public class NamedDto<T extends RepresentationModel<? extends T>> extends BaseDto<T> {
    @NotNull
    @Size(min = 1, max = 50)
    private String name;
}
