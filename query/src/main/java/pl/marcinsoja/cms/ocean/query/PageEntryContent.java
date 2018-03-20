package pl.marcinsoja.cms.ocean.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Embeddable
public class PageEntryContent {

    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(columnDefinition = "uuid")
    private UUID contentId;

    private String contentType;
}
