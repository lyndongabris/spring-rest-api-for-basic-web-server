package org.gabris.RestApi.model;

import org.assertj.core.api.SoftAssertions;
import org.gabris.RestApi.util.HttpVerb;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PageTest {
    private static final long ID = 1L;
    private static final String PAGE_NAME = "page_name";
    private static final List<HttpVerb> VERBS = List.of(HttpVerb.GET);
    private static final String CONTENT_TYPE = "content_type";
    private static final String PATH = "path";

    private void multiplePageAssert(Page page, Long id, String pageName, List<HttpVerb> verbs, String contentType, String path) {
        SoftAssertions pageBundle = new SoftAssertions();
        pageBundle.assertThat(page.getId()).isEqualTo(id);
        pageBundle.assertThat(page.getPageName()).isEqualTo(pageName);
        pageBundle.assertThat(page.getVerbs()).isEqualTo(verbs);
        pageBundle.assertThat(page.getContentType()).isEqualTo(contentType);
        pageBundle.assertThat(page.getPath()).isEqualTo(path);
        pageBundle.assertAll();
    }

    private Page createPage() {
        return new Page(PAGE_NAME, VERBS, CONTENT_TYPE, PATH);
    }

    @Test
    void emptyConstructor() {
        Page page = new Page();
        multiplePageAssert(page, null, null, null, null, null);
    }

    @Test
    void completeConstructor() {
        Page page = createPage();
        multiplePageAssert(page, null, PAGE_NAME, VERBS, CONTENT_TYPE, PATH);
    }

    @Test
    void setId() {
        Page page = createPage();
        page.setId(ID);
        assertThat(page.getId()).isEqualTo(ID);
    }

    @Test
    void setVerbs() {
        Page page = createPage();
        page.setVerbs(List.of(HttpVerb.POST));
        assertThat(page.getVerbs()).isEqualTo(List.of(HttpVerb.POST));
    }

    @Test
    void setContentType() {
        Page page = createPage();
        String newContentType = "newContentType";
        page.setContentType(newContentType);
        assertThat(page.getContentType()).isEqualTo(newContentType);
    }

    @Test
    void setPath() {
        Page page = createPage();
        String newPath = "newPath";
        page.setPath(newPath);
        assertThat(page.getPath()).isEqualTo(newPath);
    }

    @Test
    void equalsCheckOnId() {
        Page page1 = new Page();
        page1.setId(1L);
        Page page2 = new Page();
        page2.setId(1L);
        assertThat(page1.equals(page2)).isTrue();
    }

    @Test
    void notEqualsCheckOnId() {
        Page page1 = new Page();
        page1.setId(1L);
        Page page2 = new Page();
        page2.setId(2L);
        assertThat(page1.equals(page2)).isFalse();
    }

    @Test
    void equalsCheckOnPageName() {
        Page page1 = new Page();
        page1.setPageName("pageName");
        Page page2 = new Page();
        page2.setPageName("pageName");
        assertThat(page1.equals(page2)).isTrue();
    }

    @Test
    void notEqualsCheckOnPageName() {
        Page page1 = new Page();
        page1.setPageName("pageName");
        Page page2 = new Page();
        page2.setPageName("otherPageName");
        assertThat(page1.equals(page2)).isFalse();
    }

    @Test
    void equalsCheckOnVerbs() {
        Page page1 = new Page();
        page1.setVerbs(List.of(HttpVerb.GET));
        Page page2 = new Page();
        page2.setVerbs(List.of(HttpVerb.GET));
        assertThat(page1.equals(page2)).isTrue();
    }

    @Test
    void notEqualsCheckOnVerbs() {
        Page page1 = new Page();
        page1.setVerbs(List.of(HttpVerb.GET));
        Page page2 = new Page();
        page2.setVerbs(List.of(HttpVerb.PUT));
        assertThat(page1.equals(page2)).isFalse();
    }

    @Test
    void equalsCheckOnContentType() {
        Page page1 = new Page();
        page1.setContentType("contentType");
        Page page2 = new Page();
        page2.setContentType("contentType");
        assertThat(page1.equals(page2)).isTrue();
    }

    @Test
    void notEqualsCheckOnContentType() {
        Page page1 = new Page();
        page1.setContentType("contentType");
        Page page2 = new Page();
        page2.setContentType("otherContentType");
        assertThat(page1.equals(page2)).isFalse();
    }

    @Test
    void equalsCheckOnPath() {
        Page page1 = new Page();
        page1.setPath("path");
        Page page2 = new Page();
        page2.setPath("path");
        assertThat(page1.equals(page2)).isTrue();
    }

    @Test
    void notEqualsCheckOnPath() {
        Page page1 = new Page();
        page1.setPath("path");
        Page page2 = new Page();
        page2.setPath("otherPath");
        assertThat(page1.equals(page2)).isFalse();
    }

    @Test
    void hashCodeEquals() {
        Page page1 = createPage();
        Page page2 = createPage();
        assertThat(page1.hashCode()).isEqualTo(page2.hashCode());
    }

    @Test
    void hashCodeNotEqualsOnId() {
        Page page1 = createPage();
        Page page2 = createPage();
        page2.setId(2L);
        assertThat(page1.hashCode()).isNotEqualTo(page2.hashCode());
    }

    @Test
    void hashCodeNotEqualsOnPageName() {
        Page page1 = createPage();
        Page page2 = createPage();
        page2.setPageName("new_page_name");
        assertThat(page1.hashCode()).isNotEqualTo(page2.hashCode());
    }

    @Test
    void hashCodeNotEqualsOnVerbs() {
        Page page1 = createPage();
        Page page2 = createPage();
        page2.setVerbs(List.of(HttpVerb.POST));
        assertThat(page1.hashCode()).isNotEqualTo(page2.hashCode());
    }

    @Test
    void hashCodeNotEqualsOnContentType() {
        Page page1 = createPage();
        Page page2 = createPage();
        page2.setContentType("new_content_type");
        assertThat(page1.hashCode()).isNotEqualTo(page2.hashCode());
    }

    @Test
    void hashCodeNotEqualsOnPath() {
        Page page1 = createPage();
        Page page2 = createPage();
        page2.setPath("new_path");
        assertThat(page1.hashCode()).isNotEqualTo(page2.hashCode());
    }

    @Test
    void toStringCheck() {
        Page page = createPage();
        page.setId(ID);
        assertThat(page.toString()).isEqualTo("Page{" +
                "id=" + ID +
                ", pageName='" + PAGE_NAME + '\'' +
                ", verbs=" + VERBS +
                ", contentType='" + CONTENT_TYPE + '\'' +
                ", path='" + PATH + '\'' +
                '}');
    }
}
