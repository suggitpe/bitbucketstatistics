package org.suggs.sandbox.bitbucket.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RepositoriesResponse {

    private static final Logger LOG = LoggerFactory.getLogger(RepositoriesResponse.class);
    private int pagelen;
    private int page;
    private int size;
    @JsonProperty("values")
    private List<Repository> repositories;

    public List<URI> extractCommitsUris() {
        List<URI> commitUris = new ArrayList<>();
        for (Repository repo : getRepositories()) {
            try {
                commitUris.add(new URI(repo.getLinks().getCommits().getHref()));
            } catch (URISyntaxException exception) {
                LOG.error("Commits URI has syntax errors [" + repo.getLinks().getCommits().getHref() + "]");
            }
        }
        return commitUris;
    }

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
        return "RepositoriesResponse{" +
                "pagelen=" + pagelen +
                ", page=" + page +
                ", size=" + size +
                ", repositories=" + repositories +
                '}';
    }
}
