package com.example.alfiapachehttpclient.config;

import com.gremlin.GremlinService;
import com.gremlin.http.client.GremlinApacheHttpRequestInterceptor;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApacheClientConfig {

    private final GremlinService gremlinService;
    private static final int CONNECTION_TIMEOUT = 1000;
    private static final int SOCKET_TIMEOUT = 3000;

    @Autowired
    public ApacheClientConfig(GremlinService gremlinService) {
        this.gremlinService = gremlinService;
    }

    @Bean
    public CloseableHttpClient closableHttpClient() {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(CONNECTION_TIMEOUT)
                .setSocketTimeout(SOCKET_TIMEOUT)
                .build();

        final GremlinApacheHttpRequestInterceptor gremlinInterceptor =
                new GremlinApacheHttpRequestInterceptor(gremlinService, "alfi-client-demo");
        final HttpClientBuilder clientBuilder = HttpClientBuilder
                .create()
                .addInterceptorFirst(gremlinInterceptor)
                .setDefaultRequestConfig(requestConfig);

        return clientBuilder.build();
    }


}
