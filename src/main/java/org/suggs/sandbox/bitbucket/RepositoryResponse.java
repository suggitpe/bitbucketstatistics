package org.suggs.sandbox.bitbucket;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RepositoryResponse {

    private int pagelen;
    private int page;
    private int size;
    @JsonProperty("values")
    private List<Repository> repositories;

    public int getPagelen() {
        return pagelen;
    }

    public void setPagelen(int pagelen) {
        this.pagelen = pagelen;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }


    public List<Repository> getRepositories() {
        return repositories;
    }

    public void setRepositories(List<Repository> repositories) {
        this.repositories = repositories;
    }

    @Override
    public String toString() {
        return "RepositoryResponse{" +
                "pagelen=" + pagelen +
                ", page=" + page +
                ", size=" + size +
                ", repositories=" + repositories +
                '}';
    }
}
