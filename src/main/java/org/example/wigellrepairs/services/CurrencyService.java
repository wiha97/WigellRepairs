package org.example.wigellrepairs.services;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    private final LoggerService loggerService;

    @Autowired
    public CurrencyService(LoggerService loggerService){
        this.loggerService = loggerService;
    }

    @Value("${conversion.api}")
    private String apiUrl;

    private String result = "";
    private String from = "SEK";
    private String convertTo = "EUR";
    private String apiUri = "https://open.er-api.com/v6/latest/";

    public Double getConversion(Double price) {

        //  Kollar ifall det redan finns data och hur gammal den är
        //  Förhindrar ratelimiting
        if(result.isEmpty()
            || getNode().findValue("time_next_update_unix").asLong() < LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)){
            result = getFromAPI();
        } else {
            loggerService.info("Using stored data, next update: "+getNode().findValue("time_next_update_unix"));
        }

        double value = price * getNode().findValue(convertTo).asDouble();
        System.out.println(String.format("Converted price = %.2f %s", value, convertTo));

        return value;
    }

    public double convert(double price, String base, String rate){
        String url = String.format("%s/%s/%s/%s", apiUrl, price, base, rate);

        RestTemplate template = new RestTemplate();
        ResponseEntity<Double> responseEntity = template.getForEntity(url, Double.class);

        return responseEntity.getBody();
    }

    private String getFromAPI(){
        String uri = apiUri + from;
        RestTemplate template = new RestTemplate();

        // Måste skicka "application/json" i en header, annars får vi SOAP svar
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<String> responseEntity = template.exchange(uri, HttpMethod.GET, entity, String.class);

        String response = responseEntity.getBody();

        return response.toString();
    }

    private JsonNode getNode() {
        JsonNode node = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            node = mapper.readTree(result.toString());
        } catch (JsonMappingException jsonMappingException) {
            loggerService.error(jsonMappingException.getMessage());
        } catch (JsonProcessingException jsonProcessingException){
            loggerService.error(jsonProcessingException.getMessage());
        }
        return node;
    }
}
