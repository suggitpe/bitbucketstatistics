package org.suggs.sandbox.bitbucket.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Author {

    private String raw;
    private User user;

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Author{" +
                "raw='" + raw + '\'' +
                ", user=" + user +
                '}';
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class User {
        private String username;
        @JsonProperty("display_name")
        private String displayName;
        private String uuid;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        @Override
        public String toString() {
            return "User{" +
                    "username='" + username + '\'' +
                    ", displayName='" + displayName + '\'' +
                    ", uuid='" + uuid + '\'' +
                    '}';
        }
    }
}
