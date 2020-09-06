package com.calling.localhost;

import com.fasterxml.jackson.databind.JsonNode;
import feign.Client;
import feign.httpclient.ApacheHttpClient;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(url = "https://cat-fact.herokuapp.com/", name = "catApi", configuration = CatFactApi.CatFactApiConfig.class)
public interface CatFactApi {

    @GetMapping(value = "/facts")
    JsonNode getCatFacts();


    class CatFactApiConfig {

        @Bean
        public Client getClient() {

            // Create the Apache HTTP Client
            HttpClientBuilder builder = HttpClientBuilder.create();

            // Add our custom interceptor
            builder.addInterceptorFirst((HttpRequestInterceptor) (httpRequest, httpContext) -> {
                System.out.println("Sending Request to " + httpRequest.getRequestLine().getUri());
            });

            // Add proxy configuration
            HttpHost proxy = new HttpHost("proxy.com", 80, "http");
            DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
            builder.setRoutePlanner(routePlanner);

            // Wrap the client in the OpenFeign Client
            return new ApacheHttpClient(builder.build());

        }
    }

}
