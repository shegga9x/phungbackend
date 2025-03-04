package com.example.backend.thirtParty.openSearch;

import org.opensearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.opensearch.client.RestClient;
import org.opensearch.client.RestClientBuilder;

@Configuration
public class OpenSearchConfig {

    // @Value("${opensearch.host}")
    // private String host;

    // @Value("${opensearch.port}")
    // private int port;

    // @Value("${opensearch.username}")
    // private String username;

    // @Value("${opensearch.password}")
    // private String password;

    @Bean
    public RestHighLevelClient openSearchClient() {
        // Basic authentication credentials
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("phungvip", "PhungVip25082000@"));

        // Build REST client
        RestClientBuilder builder = RestClient
                .builder(new HttpHost("https://search-phungvip-jotjb2sqk537m7dwirgv4svajm.aos.ap-southeast-1.on.aws",
                        443, "https"))
                .setHttpClientConfigCallback(
                        httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));

        return new RestHighLevelClient(builder);
    }
}