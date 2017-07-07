package org.suggs.sandbox.bitbucket;

import java.util.Map;

public class EnvironmentClientAuthentication implements ClientAuthentication {

    private String username;
    private String password;

    public EnvironmentClientAuthentication() {
        Map<String, String> env = System.getenv();
        username = env.get("username");
        password = env.get("password");
        if (!isIntegral()) {
            throw new IllegalArgumentException("Need to set the username and password in the environment");
        }
    }

    private boolean isIntegral() {
        return notEmpty(username) && notEmpty(password);
    }

    private boolean notEmpty(String str) {
        return (str != null && !str.isEmpty());
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
