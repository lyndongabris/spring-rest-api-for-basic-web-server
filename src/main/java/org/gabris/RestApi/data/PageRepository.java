package org.gabris.RestApi.data;

import org.gabris.RestApi.model.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PageRepository extends JpaRepository<Page, Long> {
    List<Page> findByPageName(String pageName);
}
