package pl.marcinsoja.cms.ocean.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.ProblemModule;
import org.zalando.problem.Status;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.validation.ConstraintViolationProblemModule;
import pl.marcinsoja.cms.ocean.core.page.PageAlreadyExists;

@Configuration
@EnableAutoConfiguration(exclude = ErrorMvcAutoConfiguration.class)
public class HttpErrorConfiguration {

    @ControllerAdvice
    public static class ExceptionHandling implements ProblemHandling {

        @Override
        public boolean isCausalChainsEnabled() {
            return true;
        }

        @ExceptionHandler
        public ResponseEntity<Problem> handle(PageAlreadyExists exception, NativeWebRequest request) {
            return create(Status.BAD_REQUEST, exception, request);
        }
    }

    @Autowired
    public void configure(ObjectMapper objectMapper) {
        objectMapper.registerModule(new ProblemModule().withStackTraces())
                    .registerModule(new ConstraintViolationProblemModule());
    }
}
