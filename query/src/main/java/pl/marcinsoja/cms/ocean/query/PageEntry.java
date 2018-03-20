package pl.marcinsoja.cms.ocean.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageEntry {
    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @NotNull
    @Column(columnDefinition = "uuid")
    private UUID layoutId;

    @NotBlank
    private String slug;

    @NotBlank
    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<PageEntryContent> contents = new ArrayList<>();

}
