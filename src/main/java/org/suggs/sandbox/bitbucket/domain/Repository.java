package org.suggs.sandbox.bitbucket.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Repository {

    private String scm;
    private String name;
    private Links links;
    @JsonProperty("is_private")
    private boolean isPrivate;

    public String getScm() {
        return scm;
    }

    public void setScm(String scm) {
        this.scm = scm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    @Override
    public String toString() {
        return "Repository{" +
                "scm='" + scm + '\'' +
                ", name='" + name + '\'' +
                ", links=" + links +
                ", isPrivate=" + isPrivate +
                '}';
    }
}
