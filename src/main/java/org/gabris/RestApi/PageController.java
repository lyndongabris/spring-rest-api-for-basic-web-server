package org.gabris.RestApi;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
record PageController(PageRepository repository) {
    @GetMapping("/pages")
    List<Page> all(@RequestParam(name = "pageName") Optional<String> pageName) {
        if (pageName.isPresent() && !pageName.get().isBlank()) {
            return repository.findByPageName(pageName.get());
        }
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
        Optional<Page> oldPage = repository.findById(id);
        if (oldPage.isPresent()) {
            Page page = getPage(newPage, id, oldPage.get());
            return repository.save(page);
        } else {
            Page page = new Page(newPage.getPageName(), newPage.getVerbs(), newPage.getContentType(), newPage.getPath());
            page.setId(id);
            return repository.save(page);
        }
    }

    private static Page getPage(Page newPage, Long id, Page oldPage) {
        String pageName = newPage.getPageName() != null ? newPage.getPageName() : oldPage.getPageName();
        List<HttpVerb> verbs = newPage.getVerbs() != null ? newPage.getVerbs() : oldPage.getVerbs();
        String contentType = newPage.getContentType() != null ? newPage.getContentType() : oldPage.getContentType();
        String path = newPage.getPath() != null ? newPage.getPath() : oldPage.getPath();
        Page page = new Page(pageName, verbs, contentType, path);
        page.setId(id);
        return page;
    }

    @DeleteMapping("/pages/{id}")
    void deletePage(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
