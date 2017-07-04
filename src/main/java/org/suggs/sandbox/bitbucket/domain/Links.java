package org.suggs.sandbox.bitbucket.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Links {
    @JsonProperty("commits")
    private Link commits;
    @JsonProperty("tags")
    private Link tags;

    public Link getCommits() {
        return commits;
    }

    public void setCommits(Link commits) {
        this.commits = commits;
    }

    public Link getTags() {
        return tags;
    }

    public void setTags(Link tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Links{" +
                "commits=" + commits +
                ", tags=" + tags +
                '}';
    }
}
