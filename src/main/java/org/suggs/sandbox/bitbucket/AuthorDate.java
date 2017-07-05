package org.suggs.sandbox.bitbucket;

import org.joda.time.DateTime;

public class AuthorDate {

    private final String username;
    private final DateTime month;

    public AuthorDate(String username, DateTime dateTime) {
        this.username = username;
        this.month = dateTime;
    }

    public String getUsername() {
        return username;
    }

    public DateTime getMonth() {
        return month;
    }

    @Override
    public String toString() {
        return "AuthorDate{" +
                "username='" + username + '\'' +
                ", month=" + month +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthorDate that = (AuthorDate) o;

        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        return month != null ? month.equals(that.month) : that.month == null;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (month != null ? month.hashCode() : 0);
        return result;
    }
}
