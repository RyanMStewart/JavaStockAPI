package com.mycompany.app;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import javax.json.Json;
import javax.json.stream.JsonParser;
import java.io.StringReader;
import java.math.BigDecimal;
import java.net.URI;

/**
 * Hello world!
 *
 */
public class App {
public static void main(String[] args)
{
    HttpClient httpclient = HttpClients.createDefault();


    try
    {
        URIBuilder builder = new URIBuilder("https://services.last10k.com/v1/company/siri/balancesheet");

        builder.setParameter("formType", "10-K");
        builder.setParameter("filingOrder", "0");

        URI uri = builder.build();
        HttpGet request = new HttpGet(uri);
        request.setHeader("Ocp-Apim-Subscription-Key", "e231cfa87f6448e5958d05603007efb6");


        // Request body
//        StringEntity reqEntity = new StringEntity("{body}");
//                request.setEntity(reqEntity);

        HttpResponse response = httpclient.execute(request);
        HttpEntity entity = response.getEntity();
        System.out.println(EntityUtils.getContentMimeType(entity));

        // CREATE A JSON PARSER, CONVERT HTTP-ENTITY TO STRING, READ THROUGH THE DATA FINDING KEYS AND
        // KEY VALUES.

        final JsonParser parser = Json.createParser(new StringReader(EntityUtils.toString(entity)));
        String key = null;
        String value = null;
        while (parser.hasNext()) {
            final JsonParser.Event event = parser.next();
            switch (event) {
                case KEY_NAME:
                    key = parser.getString();
                    System.out.println(key);
                    break;
                case VALUE_STRING:
                    String string = parser.getString();
                    System.out.println(string);
                    break;
                case VALUE_NUMBER:
                    BigDecimal number = parser.getBigDecimal();
                    System.out.println(number);
                    break;
                case VALUE_TRUE:
                    System.out.println(true);
                    break;
                case VALUE_FALSE:
                    System.out.println(false);
                    break;
            }
        }
        parser.close();

//
//        JSONArray json = new JSONArray(entity);
//        System.out.println(json.get(0));


//        if (entity != null)
//        {
//            System.out.println(EntityUtils.toString(entity));
//        }
    }
    catch (Exception e)
    {
        System.out.println(e.getMessage());
    }
}
}

