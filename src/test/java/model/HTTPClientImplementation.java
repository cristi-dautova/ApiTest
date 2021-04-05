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

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(get)) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                body = EntityUtils.toString(entity);
            }
        }
        return new ResponseParameters(body, 200, get.getAllHeaders());
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

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(put)) {

            body = EntityUtils.toString(response.getEntity());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseParameters(body, 200, put.getAllHeaders());
    }

    @SneakyThrows
    @Override
    public <T> ResponseParameters post(String endPont, T value) {

        String objectToJson = serializeEntitiesToJson(value);

        String body = "";
        HttpPost post = new HttpPost(BASE_URL + endPont);

        post.setHeader("Accept", "application/json");
        post.setHeader("Content-type", "application/json");

        post.setEntity(new StringEntity(objectToJson));

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {

            body = EntityUtils.toString(response.getEntity());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseParameters(body, 200, post.getAllHeaders());
    }

    @Override
    public ResponseParameters delete(String endPont, String endPointParameter, Map<String, String> parameters) {

        String body = "";
        String parameterValue = null;

        for (Map.Entry entry : parameters.entrySet()) {
            parameterValue = (String) entry.getValue();
        }

        HttpDelete delete = new HttpDelete(BASE_URL + endPont + "/" + parameterValue);

        delete.setHeader("Accept", "application/json");
        delete.setHeader("Content-type", "application/json");

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(delete)) {

            body = EntityUtils.toString(response.getEntity());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseParameters(body, 200, delete.getAllHeaders());
    }
}
