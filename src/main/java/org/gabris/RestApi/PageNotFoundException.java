package org.gabris.RestApi;

public class PageNotFoundException extends RuntimeException {
    public PageNotFoundException(Long id) {
        super("Could not find page by id '" + id + "'");
    }
}
