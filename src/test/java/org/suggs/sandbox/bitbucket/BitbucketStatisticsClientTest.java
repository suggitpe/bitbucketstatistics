package org.suggs.sandbox.bitbucket;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.suggs.sandbox.bitbucket.domain.RepositoriesResponse;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class BitbucketStatisticsClientTest {

    private static final String JSON = "{\"pagelen\": 10, \"values\": [{\"scm\": \"git\", \"website\": \"\", \"has_wiki\": false, \"name\": \"publicrepo\", \"links\": {\"watchers\": {\"href\": \"https://api.bitbucket.org/2.0/repositories/suggitpe/publicrepo/watchers\"}, \"branches\": {\"href\": \"https://api.bitbucket.org/2.0/repositories/suggitpe/publicrepo/refs/branches\"}, \"tags\": {\"href\": \"https://api.bitbucket.org/2.0/repositories/suggitpe/publicrepo/refs/tags\"}, \"commits\": {\"href\": \"https://api.bitbucket.org/2.0/repositories/suggitpe/publicrepo/commits\"}, \"clone\": [{\"href\": \"https://bitbucket.org/suggitpe/publicrepo.git\", \"name\": \"https\"}, {\"href\": \"ssh://git@bitbucket.org/suggitpe/publicrepo.git\", \"name\": \"ssh\"}], \"self\": {\"href\": \"https://api.bitbucket.org/2.0/repositories/suggitpe/publicrepo\"}, \"html\": {\"href\": \"https://bitbucket.org/suggitpe/publicrepo\"}, \"avatar\": {\"href\": \"https://bitbucket.org/suggitpe/publicrepo/avatar/32/\"}, \"hooks\": {\"href\": \"https://api.bitbucket.org/2.0/repositories/suggitpe/publicrepo/hooks\"}, \"forks\": {\"href\": \"https://api.bitbucket.org/2.0/repositories/suggitpe/publicrepo/forks\"}, \"downloads\": {\"href\": \"https://api.bitbucket.org/2.0/repositories/suggitpe/publicrepo/downloads\"}, \"pullrequests\": {\"href\": \"https://api.bitbucket.org/2.0/repositories/suggitpe/publicrepo/pullrequests\"}}, \"fork_policy\": \"allow_forks\", \"uuid\": \"{385f69fd-d993-4b95-89df-504bcd976857}\", \"language\": \"\", \"created_on\": \"2017-06-26T13:52:51.839541+00:00\", \"mainbranch\": {\"type\": \"branch\", \"name\": \"master\"}, \"full_name\": \"suggitpe/publicrepo\", \"has_issues\": false, \"owner\": {\"username\": \"suggitpe\", \"display_name\": \"suggitpe\", \"type\": \"user\", \"uuid\": \"{35fc2752-dd4f-417e-a8ad-d5c79fddb98f}\", \"links\": {\"self\": {\"href\": \"https://api.bitbucket.org/2.0/users/suggitpe\"}, \"html\": {\"href\": \"https://bitbucket.org/suggitpe/\"}, \"avatar\": {\"href\": \"https://bitbucket.org/account/suggitpe/avatar/32/\"}}}, \"updated_on\": \"2017-06-26T13:53:00.699564+00:00\", \"size\": 62677, \"type\": \"repository\", \"slug\": \"publicrepo\", \"is_private\": false, \"description\": \"\"}], \"page\": 1, \"size\": 1}";
    private static final Logger LOG = LoggerFactory.getLogger(BitbucketStatisticsClientTest.class);
    private static URI REPOSITORIES_URI;

    private BitbucketStatisticsClient bitbucketStatisticsClient;
    private static final ClientAuthentication CLIENT_AUTH = new EnvironmentClientAuthentication();

    static {
        try {
            REPOSITORIES_URI = new URI("https://api.bitbucket.org/2.0/repositories/suggitpe");
        } catch (URISyntaxException exception) {
            LOG.error("failed to create URI for test");
        }
    }

    @Before
    public void onSetup() {
        bitbucketStatisticsClient = new BitbucketStatisticsClient();
    }

    @Test
    public void retrievesListOfRepositoriesFromURI() throws Exception {
        RepositoriesResponse repos = bitbucketStatisticsClient.retrieveListOfRepositoriesForUri(REPOSITORIES_URI, CLIENT_AUTH);
        LOG.debug(repos.toString());
        assertThat(repos.getRepositories()).isNotEmpty();
    }

    @Test
    public void retievesListOfCommitsFromRepositoryURI() throws Exception {
        bitbucketStatisticsClient.retieveListOfCommitsForRepositoriesAtUri(REPOSITORIES_URI, CLIENT_AUTH);
    }

    @Test
    public void readsJsonForRepositoryInformation() throws Exception {
        RepositoriesResponse repos = bitbucketStatisticsClient.createRepositoriesResponseFrom(JSON);
        LOG.debug(repos.toString());
        assertThat(repos.getRepositories()).isNotEmpty();
    }

    @Test
    public void countsCommitsByAuthorFromRepositoryUri() throws Exception {
        Map<AuthorDate, Integer> mapOfCommits = bitbucketStatisticsClient.retrieveMapOfCommitsByAuthorFrom(REPOSITORIES_URI, CLIENT_AUTH);
        LOG.debug("" + mapOfCommits.toString());
        assertThat(mapOfCommits.keySet().size()).isNotZero();
    }

}
