package org.suggs.sandbox.bitbucket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Charsets;
import com.google.common.io.CharStreams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Utility {

    private static final Logger LOG = LoggerFactory.getLogger(Utility.class);

    public RepositoryResponse retrieveListOfProjectsForUrl() throws IOException {

        HttpRequestFactory requestFactory = createNewHttpRequestFactory();
        HttpRequest request = requestFactory.buildGetRequest(new GenericUrl("https://api.bitbucket.org/2.0/repositories/suggitpe"));
        HttpResponse response = request.execute();
        return createRepositoryResponseFrom(stringifyStream(response.getContent()));
    }

    protected RepositoryResponse createRepositoryResponseFrom(String json) throws IOException {
        LOG.debug(json);
        return new ObjectMapper().readValue(json, RepositoryResponse.class);
    }

    private HttpRequestFactory createNewHttpRequestFactory() {
        return new NetHttpTransport().createRequestFactory(request -> request.setParser(new JsonObjectParser(new JacksonFactory())));
    }

    private String stringifyStream(InputStream is) {
        try {
            return CharStreams.toString(new InputStreamReader(is, Charsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
