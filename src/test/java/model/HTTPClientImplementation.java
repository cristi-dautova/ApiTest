package model;

import lombok.SneakyThrows;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static utils.Serializer.serializeEntitiesToJson;
import static utils.UrlConstants.BASE_URL;

public class HTTPClientImplementation implements BaseRestClient {

    @SneakyThrows
    @Override
    public <T> ResponseParameters get(String endPont, String endPointParameter, Map<String, String> parameters, T value, Map<String, String> headers) {

        String parameterValue = null;
        for (Map.Entry entry : parameters.entrySet()) {
            parameterValue = (String) entry.getValue();
        }
        HttpGet get = new HttpGet(BASE_URL + endPont + "/" + parameterValue);
        for (Map.Entry entry : headers.entrySet()) {
            get.setHeader((String)entry.getKey(), (String) entry.getValue());
        }

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(get)) {
            return new ResponseParameters(response);
        }
    }

    @SneakyThrows
    @Override
    public <T> ResponseParameters put(String endPont, T value, Map<String, String> headers) {

        String objectToJson = serializeEntitiesToJson(value);
        HttpPut put = new HttpPut(BASE_URL + endPont);
        for (Map.Entry entry : headers.entrySet()) {
            put.setHeader((String)entry.getKey(), (String) entry.getValue());
        }
        put.setEntity(new StringEntity(objectToJson));

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(put)) {
            return new ResponseParameters(response);
        }
    }

    @SneakyThrows
    @Override
    public <T> ResponseParameters post(String endPont, T value, Map<String, String> headers) {

        String objectToJson = serializeEntitiesToJson(value);
        HttpPost post = new HttpPost(BASE_URL + endPont);
        for (Map.Entry entry : headers.entrySet()) {
            post.setHeader((String)entry.getKey(), (String) entry.getValue());
        }
        post.setEntity(new StringEntity(objectToJson));

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {
            return new ResponseParameters(response);
        }
    }

    @Override
    public <T> ResponseParameters delete(String endPont, String endPointParameter, Map<String, String> parameters, T value, Map<String, String> headers) {

        String parameterValue = null;

        for (Map.Entry entry : parameters.entrySet()) {
            parameterValue = (String) entry.getValue();
        }
        HttpDelete delete = new HttpDelete(BASE_URL + endPont + "/" + parameterValue);
        for (Map.Entry entry : headers.entrySet()) {
            delete.setHeader((String)entry.getKey(), (String) entry.getValue());
        }

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(delete)) {
            return new ResponseParameters(response);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
       return null;
    }
}
