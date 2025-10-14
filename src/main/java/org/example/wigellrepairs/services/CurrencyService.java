
package org.example.wigellrepairs.services;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CurrencyService {

    ///
    ///    Rates By Exchange Rate API
    /// https://www.exchangerate-api.com
    ///

    private String result = "";
    private String apiUri = "https://open.er-api.com/v6/latest/";

    public double getConversion(double price, String base, String convertTo) {

        //  Kollar ifall det redan finns data och hur gammal den är
        //  Förhindrar ratelimiting
        if(!result.isEmpty())
            System.out.println(getNode().findValue("base_code").toString().replace("\"",""));
        if(result.isEmpty()
            || getNode().findValue("time_next_update_unix").asLong() < LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
            || !getNode().findValue("base_code").toString().replace("\"", "").equals(base)){
            result = getFromAPI(base);
            System.out.println("Rates By Exchange Rate API: https://www.exchangerate-api.com");
        } else {
            System.out.println("Using cached data");
        }

        double rate = getNode().findValue(convertTo).asDouble();
        double value = price * rate;

        value = Math.round(value * 100.0) / 100.0;
        System.out.println("Converting from " + base + " to " + convertTo);
        System.out.println(String.format("Converted price = %.2f %s", value, convertTo));

        return value;
    }

    private String getFromAPI(String base){
        String uri = apiUri + base;
        RestTemplate template = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<String> responseEntity = template.exchange(uri, HttpMethod.GET, entity, String.class);

        String response = responseEntity.getBody();

        // System.out.println(response);

        return response.toString();
    }

    private JsonNode getNode() {
        JsonNode node = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            node = mapper.readTree(result.toString());
        } catch (JsonMappingException jsonMappingException) {
            System.out.println(jsonMappingException);
        } catch (JsonProcessingException jsonProcessingException){
            System.out.println(jsonProcessingException);
        }
        return node;
    }
}
