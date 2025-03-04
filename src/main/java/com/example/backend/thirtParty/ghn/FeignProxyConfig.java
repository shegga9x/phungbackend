package com.example.backend.thirtParty.ghn;

import feign.Client;
import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.Route;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.backend.config.ApplicationProperties;

import java.net.InetSocketAddress;
import java.net.Proxy;

@Configuration
@RequiredArgsConstructor
public class FeignProxyConfig {

  private final ApplicationProperties applicationProperties;

  private static final String API_TOKEN = GHNConfig.token; // Replace with actual API token

  @Bean
  public Client feignClient() {
    // Configure Proxy
    String proxyHost = applicationProperties.getProxyHost();
    int proxyPort = applicationProperties.getProxyPort();
    String proxyUser = applicationProperties.getProxyUser();
    String proxyPass = applicationProperties.getProxyPass();

    Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));

    // Configure Proxy Authentication
    Authenticator proxyAuthenticator = (Route route, okhttp3.Response response) -> {
      String credential = Credentials.basic(proxyUser, proxyPass);
      return response.request().newBuilder()
        .header("Proxy-Authorization", credential)
        .build();
    };

    // Build OkHttpClient with Proxy
    OkHttpClient okHttpClient = new OkHttpClient.Builder()
        .proxy(proxy)
        .proxyAuthenticator(proxyAuthenticator)
        .build();

    return new feign.okhttp.OkHttpClient(okHttpClient);
  }

  @Bean
  public RequestInterceptor requestInterceptor() {
    return template -> {
      template.header("Token", API_TOKEN); // Automatically add Token header
      template.header("Content-Type", "application/json"); // Optional
    };
  }
}
