package org.gabris.RestApi;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "pages")
class Page {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String pageName;
    private List<HttpVerb> verbs;
    private String contentType;
    private String path;

    public Page() {
    }

    public Page(String pageName, List<HttpVerb> verbs, String contentType, String path) {
        this.pageName = pageName;
        this.verbs = verbs;
        this.contentType = contentType;
        this.path = path;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Long getId() {
        return id;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public List<HttpVerb> getVerbs() {
        return verbs;
    }

    public void setVerbs(List<HttpVerb> verbs) {
        this.verbs = verbs;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Page page = (Page) o;
        return Objects.equals(id, page.id) && Objects.equals(pageName, page.pageName) && Objects.equals(verbs, page.verbs) && Objects.equals(contentType, page.contentType) && Objects.equals(path, page.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pageName, verbs, contentType, path);
    }

    @Override
    public String toString() {
        return "Page{" +
                "id=" + id +
                ", pageName='" + pageName + '\'' +
                ", verbs=" + verbs +
                ", contentType='" + contentType + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
