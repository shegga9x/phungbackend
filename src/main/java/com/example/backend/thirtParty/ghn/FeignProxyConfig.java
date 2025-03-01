package com.example.backend.thirtParty.ghn;

import feign.Client;
import feign.RequestInterceptor;
import okhttp3.OkHttpClient;
import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.Route;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;
import java.net.Proxy;

@Configuration
public class FeignProxyConfig {

  private static final String PROXY_HOST = "103.156.91.191";
  private static final int PROXY_PORT = 2021;
  private static final String PROXY_USER = "ipzn6e1o";
  private static final String PROXY_PASS = "iPZN6e1O";

  private static final String API_TOKEN = GHNConfig.token; // Replace with actual API token

  @Bean
  public Client feignClient() {
    // Configure Proxy
    Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(PROXY_HOST, PROXY_PORT));

    // Configure Proxy Authentication
    Authenticator proxyAuthenticator = (Route route, okhttp3.Response response) -> {
      String credential = Credentials.basic(PROXY_USER, PROXY_PASS);
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
