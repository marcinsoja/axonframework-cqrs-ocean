package pl.marcinsoja.cms.ocean.api.content.article;

import com.neovisionaries.i18n.LanguageCode;
import lombok.Value;

import java.time.Instant;
import java.util.UUID;

@Value
public class ArticleUpdatedEvent {
    private final UUID articleId;
    private final String name;
    private final String content;
    private final LanguageCode language;
    private final Instant at;
}
