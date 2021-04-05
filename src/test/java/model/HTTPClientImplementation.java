package model;

import lombok.SneakyThrows;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;

import static utils.Serializer.serializeEntitiesToJson;
import static utils.UrlConstants.BASE_URL;

public class HTTPClientImplementation implements BaseRestClient {

    @SneakyThrows
    @Override
    public ResponseParameters get(String endPont, String endPointParameter, Map<String, String> parameters) {

        String parameterValue = null;

        for (Map.Entry entry : parameters.entrySet()) {
            parameterValue = (String) entry.getValue();
        }

        HttpGet get = new HttpGet(BASE_URL + endPont + "/" + parameterValue);

        get.setHeader("Accept", "application/json");
        get.setHeader("Content-type", "application/json");

        String body = null;
        int statusCode;

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(get)) {
            HttpEntity entity = response.getEntity();
            statusCode = response.getStatusLine().getStatusCode();
            if (entity != null) {
                body = EntityUtils.toString(entity);
            }
        }
        return new ResponseParameters(body, statusCode, get.getAllHeaders());
    }

    @SneakyThrows
    @Override
    public <T> ResponseParameters put(String endPont, T value) {

        String objectToJson = serializeEntitiesToJson(value);

        String body = "";

        HttpPut put = new HttpPut(BASE_URL + endPont);
        put.setHeader("Accept", "application/json");
        put.setHeader("Content-type", "application/json");
        put.setEntity(new StringEntity(objectToJson));

        int statusCode = 0;
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(put)) {
            statusCode = response.getStatusLine().getStatusCode();
            body = EntityUtils.toString(response.getEntity());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseParameters(body, statusCode, put.getAllHeaders());
    }

    @SneakyThrows
    @Override
    public <T> ResponseParameters post(String endPont, T value) {

        String objectToJson = serializeEntitiesToJson(value);

        String body = "";
        HttpPost post = new HttpPost(BASE_URL + endPont);
        int statusCode = 0;
        post.setHeader("Accept", "application/json");
        post.setHeader("Content-type", "application/json");

        post.setEntity(new StringEntity(objectToJson));

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {
            statusCode = response.getStatusLine().getStatusCode();
            body = EntityUtils.toString(response.getEntity());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseParameters(body, statusCode, post.getAllHeaders());
    }

    @Override
    public <T> ResponseParameters delete(String endPont, String endPointParameter, Map<String, String> parameters, T value) {

        String body = "";
        String parameterValue = null;

        for (Map.Entry entry : parameters.entrySet()) {
            parameterValue = (String) entry.getValue();
        }
        int statusCode = 0;
        HttpDelete delete = new HttpDelete(BASE_URL + endPont + "/" + parameterValue);

        delete.setHeader("Accept", "application/json");
        delete.setHeader("Content-type", "application/json");

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(delete)) {
            statusCode = response.getStatusLine().getStatusCode();
            body = EntityUtils.toString(response.getEntity());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseParameters(body, statusCode, delete.getAllHeaders());
    }
}
