package pl.marcinsoja.cms.ocean.rest;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class CreatePageRequest {

    private String pageId;

    @NotBlank
    private String slug;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
}
