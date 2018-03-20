package pl.marcinsoja.cms.ocean.rest.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePageElementRequest {
    private UUID elementId;

    @NotNull
    private UUID contentId;
    @NotBlank
    private String contentType;
    @NotNull
    private Integer order;
}
