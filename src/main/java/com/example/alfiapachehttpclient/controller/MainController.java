package com.example.alfiapachehttpclient.controller;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class MainController {

    private final Logger LOG = LoggerFactory.getLogger(getClass().getName());

    private final CloseableHttpClient closeableHttpClient;
    private CloseableHttpResponse closeableHttpResponse;

    @Autowired
    public MainController(CloseableHttpClient closeableHttpClient) {
        this.closeableHttpClient = closeableHttpClient;
    }

    @GetMapping("/")
    public @ResponseBody
    ResponseEntity<String> hello() {
        final String URI = "https://www.gremlin.com/";
        HttpGet httpGet = new HttpGet(URI);
        String responseContent = null;
        long startTime = System.currentTimeMillis();
        try {
            LOG.info(String.format("Executing GET request to %s...", URI));
            closeableHttpResponse = closeableHttpClient.execute(httpGet);
            HttpEntity httpEntity = closeableHttpResponse.getEntity();
            responseContent = EntityUtils.toString(httpEntity);
            EntityUtils.consume(httpEntity);
            LOG.info(responseContent);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            long endTime = System.currentTimeMillis();
            long duration = (endTime - startTime);
            LOG.info(String.format("GET Request took %d milliseconds", duration));
            try {
                closeableHttpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ResponseEntity<>(responseContent, HttpStatus.OK);
    }
}
