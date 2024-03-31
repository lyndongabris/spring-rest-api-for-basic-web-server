package org.gabris.RestApi;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PageController {
    private final PageRepository repository;

    public PageController(PageRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/pages")
    List<Page> all() {
        return repository.findAll();
    }

    @PostMapping("/pages")
    Page newPage(@RequestBody Page newPage) {
        return repository.save(newPage);
    }

    @GetMapping("/pages/{id}")
    Page one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new PageNotFoundException(id));
    }

    @PutMapping("/pages/{id}")
    Page replacePage(@RequestBody Page newPage, @PathVariable Long id) {
        return repository.findById(id)
                .map(page -> {
                    page.setPageName(newPage.getPageName());
                    page.setPath(newPage.getPath());
                    page.setVerbs(newPage.getVerbs());
                    page.setContentType(newPage.getContentType());
                    return repository.save(page);
                })
                .orElseGet(() -> {
                    newPage.setId(id);
                    return repository.save(newPage);
                });
    }

    @DeleteMapping("/pages/{id}")
    void deletePage(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
