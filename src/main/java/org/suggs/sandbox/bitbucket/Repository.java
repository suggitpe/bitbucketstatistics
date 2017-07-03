package org.suggs.sandbox.bitbucket;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Repository {

    private String scm;
    private String name;
    private String commits;

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

    public String getCommits() {
        return commits;
    }

    public void setCommits(String commits) {
        this.commits = commits;
    }

    @Override
    public String toString() {
        return "Repository{" +
                "scm='" + scm + '\'' +
                ", name='" + name + '\'' +
                ", commits='" + commits + '\'' +
                '}';
    }
}
