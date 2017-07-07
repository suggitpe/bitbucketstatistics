package org.suggs.sandbox.bitbucket;


import java.util.Map;

public class ClientAuthentication {
    private String username;
    private String password;

    public ClientAuthentication() {
        Map<String, String> env = System.getenv();
        username = env.get("username");
        password = env.get("password");

        if (isEmpty(username) || isEmpty(password)) {
            throw new IllegalArgumentException("You need to set username and password into your system environment");
        }
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    private boolean isEmpty(String aString) {
        return (aString == null || aString.isEmpty());
    }

}
