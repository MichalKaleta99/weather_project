package org.example;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class HTTPRequests {
    String url;

    static CloseableHttpClient httpclient = HttpClients.createDefault();

    public String getRequest(String url){
        String result="null";
        HttpGet request = new HttpGet(url);
        try (CloseableHttpResponse response = httpclient.execute(request)) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
               result = EntityUtils.toString(entity);
            }
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public String getAllStationsRequest(){
        String getAllStationsRequest = getRequest("https://api.gios.gov.pl/pjp-api/rest/station/findAll");
        return getAllStationsRequest;
    }

    public HTTPRequests(String url) {
        this.url = url;
    }
}
