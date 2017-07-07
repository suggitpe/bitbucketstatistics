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
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.suggs.sandbox.bitbucket.domain.Commit;
import org.suggs.sandbox.bitbucket.domain.CommitResponse;
import org.suggs.sandbox.bitbucket.domain.RepositoriesResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BitbucketStatisticsClient {

    private static final Logger LOG = LoggerFactory.getLogger(BitbucketStatisticsClient.class);

    public Map<AuthorDate, Integer> retrieveMapOfCommitsByAuthorFrom(URI repositoriesURI) throws IOException {
        List<Commit> commits = retieveListOfCommitsForRepositoriesAtUri(repositoriesURI);
        Map<AuthorDate, Integer> commitsByAuthorMap = new HashMap<>();
        for (Commit commit : commits) {
            AuthorDate key = new AuthorDate(commit.getAuthor().getUser().getUsername(), new DateTime(commit.getDate()).monthOfYear().roundFloorCopy());
            if (commitsByAuthorMap.containsKey(key)) {
                commitsByAuthorMap.put(key, commitsByAuthorMap.get(key) + 1);
            } else {
                commitsByAuthorMap.put(key, 1);
            }
        }
        return commitsByAuthorMap;
    }

    public List<Commit> retieveListOfCommitsForRepositoriesAtUri(URI repositoriesUri) throws IOException {
        RepositoriesResponse repositories = retrieveListOfRepositoriesForUri(repositoriesUri);
        LOG.debug("Extracting commits from " + repositories.getSize() + " repositories");
        List<URI> commitUris = repositories.extractCommitsUris();
        List<Commit> commits = new ArrayList<>();
        for (URI commitUri : commitUris) {
            CommitResponse commitResponse = createCommitResponseFrom(retrieveJsonFrom(commitUri));
//            LOG.debug(commitResponse.toString());
            commits.addAll(commitResponse.getCommits());
        }
        return commits;
    }

    public RepositoriesResponse retrieveListOfRepositoriesForUri(URI repositoriesURI) throws IOException {
        return createRepositoriesResponseFrom(retrieveJsonFrom(repositoriesURI));
    }

    protected RepositoriesResponse createRepositoriesResponseFrom(String json) throws IOException {
        return new ObjectMapper().readValue(json, RepositoriesResponse.class);
    }

    private CommitResponse createCommitResponseFrom(String json) throws IOException {
        return new ObjectMapper().readValue(json, CommitResponse.class);
    }

    private String retrieveJsonFrom(URI aUri) throws IOException {
        HttpRequestFactory requestFactory = createNewHttpRequestFactory();
        HttpRequest request = requestFactory.buildGetRequest(new GenericUrl(aUri));
        HttpResponse response = request.execute();
        String json = stringifyStream(response.getContent());
//        LOG.debug(json);
        return json;
    }

    private HttpRequestFactory createNewHttpRequestFactory() {
        ClientAuthentication auth = new ClientAuthentication();
        LOG.debug("Accessing repositories with user [" + auth.getUsername() + "]");
        return new NetHttpTransport().createRequestFactory(request -> {
            request.getHeaders().setBasicAuthentication(auth.getUsername(), auth.getPassword());
            request.setParser(new JsonObjectParser(new JacksonFactory()));
        });
    }

    private String stringifyStream(InputStream is) {
        try {
            return CharStreams.toString(new InputStreamReader(is, Charsets.UTF_8));
        } catch (IOException ioexception) {
            LOG.error("Failed to stringify JSON", ioexception);
        }
        return null;
    }
}
