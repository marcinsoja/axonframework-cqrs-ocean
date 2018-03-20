package pl.marcinsoja.cms.ocean.query;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping("/pages")
@AllArgsConstructor
public class PageController {
    private final PageRepository repository;

    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    public List<PageEntry> getAll() {
        return repository.findAll();
    }
}
