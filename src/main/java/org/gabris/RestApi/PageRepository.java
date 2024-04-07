package org.gabris.RestApi;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PageRepository extends JpaRepository<Page, Long> {
    List<Page> findByPageName(String pageName);
}
