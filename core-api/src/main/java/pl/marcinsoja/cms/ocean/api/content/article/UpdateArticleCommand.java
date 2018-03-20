package pl.marcinsoja.cms.ocean.api.content.article;

import com.neovisionaries.i18n.LanguageCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Value
@Builder
@AllArgsConstructor
public class UpdateArticleCommand {
    @NotNull
    @TargetAggregateIdentifier
    private final UUID articleId;

    @NotBlank
    private final String name;
    @NotBlank
    private final String content;
    @NotNull
    private final LanguageCode language;
}
