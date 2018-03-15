package pl.marcinsoja.cms.ocean.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@Builder
public class PageEntry {
    @Id
    @Column(columnDefinition = "uuid")
    private UUID pageId;
    private String slug;
    private String title;
    @Lob
    private String content;

    @SuppressWarnings("unused")
    public PageEntry() {
    }
}
