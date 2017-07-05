package org.suggs.sandbox.bitbucket.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Commit {
    private String hash;
    private Author author;
    private Date date;

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Commit{" +
                "hash='" + hash + '\'' +
                ", author=" + author +
                ", date=" + date +
                '}';
    }
}
