package pl.marcinsoja.cms.ocean.core.content;

import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.Repository;
import pl.marcinsoja.cms.ocean.api.content.article.CreateArticleCommand;
import pl.marcinsoja.cms.ocean.api.content.article.UpdateArticleCommand;

import java.time.Clock;

@RequiredArgsConstructor
public class ArticleHandler {

    private final Repository<Article> articleRepository;
    private final Clock clock;

    @CommandHandler
    public void handle(CreateArticleCommand command) throws Exception {
        articleRepository.newInstance(() -> new Article(command));
    }

    @CommandHandler
    public void handle(UpdateArticleCommand command) {
        articleRepository.load(command.getArticleId().toString())
                         .execute(article -> article.update(command, clock.instant()));
    }
}
