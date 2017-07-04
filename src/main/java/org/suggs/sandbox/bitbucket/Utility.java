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
import org.suggs.sandbox.bitbucket.domain.CommitResponse;
import org.suggs.sandbox.bitbucket.domain.RepositoriesResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.List;

public class Utility {

    private static final Logger LOG = LoggerFactory.getLogger(Utility.class);

    public RepositoriesResponse retrieveListOfRepositoriesForUri(URI repositoriesURI) throws IOException {
        return createRepositoriesResponseFrom(retrieveJsonFrom(repositoriesURI));
    }

    protected RepositoriesResponse createRepositoriesResponseFrom(String json) throws IOException {
        return new ObjectMapper().readValue(json, RepositoriesResponse.class);
    }

    public List<URI> retieveListOfCommitsForRepositoriesAtUri(URI repositoriesUri) throws IOException {
        RepositoriesResponse repositories = retrieveListOfRepositoriesForUri(repositoriesUri);
        LOG.debug("Extracting commits from "+ repositories.getSize()+" repositories");
        List<URI> commitUris = repositories.extractCommitsUri();
        for (URI commitUri : commitUris) {
            LOG.debug("Extracting commits from [" + commitUri + "]");
            CommitResponse commitResponse = createCommitResponseFrom(retrieveJsonFrom(commitUri));
            LOG.debug("" + commitResponse);
        }
        return commitUris;
    }

    private CommitResponse createCommitResponseFrom(String json) throws IOException {
        return new ObjectMapper().readValue(json, CommitResponse.class);
    }

    private String retrieveJsonFrom(URI aUri) throws IOException {
        HttpRequestFactory requestFactory = createNewHttpRequestFactory();
        HttpRequest request = requestFactory.buildGetRequest(new GenericUrl(aUri));
        HttpResponse response = request.execute();
        String json = stringifyStream(response.getContent());
        LOG.debug(json);
        return json;
    }

    private String stringifyStream(InputStream is) {
        try {
            return CharStreams.toString(new InputStreamReader(is, Charsets.UTF_8));
        } catch (IOException ioexception) {
            LOG.error("Failed to stringify JSON", ioexception);
        }
        return null;
    }

    private HttpRequestFactory createNewHttpRequestFactory() {
        return new NetHttpTransport().createRequestFactory(request -> request.setParser(new JsonObjectParser(new JacksonFactory())));
    }
}
