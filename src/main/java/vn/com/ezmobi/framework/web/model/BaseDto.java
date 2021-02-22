package vn.com.ezmobi.framework.web.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Null;
import java.time.LocalDateTime;

/**
 * Created by ezmobivietnam on 2021-02-21.
 */
@NoArgsConstructor
@Data
public class BaseDto<T extends RepresentationModel<? extends T>> extends RepresentationModel<T> {

    @Null
    private Integer id;

    @Null
    private LocalDateTime lastUpdate;

    @Null
    private LocalDateTime creationDate;
}
