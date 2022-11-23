package org.example;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;


import java.io.IOException;
import java.util.*;

public class Main {
    static CloseableHttpClient httpclient = HttpClients.createDefault();
    static ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);


    public static void main(String[] args) throws IOException {

        String allStations = getRequest("https://api.gios.gov.pl/pjp-api/rest/station/findAll");
        System.out.println("Oto pełna lista stacji pogodowych: ");
       // displayJsonData(allStations);

        System.out.println(getKeysInJsonUsingJsonParser(allStations));

        int providedId = askUserForStationId();
        System.out.println("Oto dane dla stacji o numerze ID "+providedId);
        String userStation = getRequest("https://api.gios.gov.pl/pjp-api/rest/aqindex/getIndex/"+providedId);
      //  displayJsonData(userStation);


    }

    private static int askUserForStationId() {
        System.out.println("Podaj ID stacji, dla której chcesz wyświetlić dane: ");
        Scanner console = new Scanner(System.in);

        if (console.hasNext("stop")) {
            System.out.println("***Terminated***");
            System.exit(0);
        }
        while (!console.hasNextInt()) {
            System.out.println("ID może składać się tylko z cyfr! ");
            console.next();
        }
        int providedId = console.nextInt();
        while (providedId <= 0) {
            System.out.println("ID powinno być większe od 0! ");
            providedId = console.nextInt();
        }
        return providedId;
    }

    public static String getRequest(String url){
        HttpGet request = new HttpGet(url);
        try (CloseableHttpResponse response = httpclient.execute(request)) {

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);
                return result;
            }
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

//    public static List<String> displayJsonData(String json, ObjectMapper mapper) throws JsonMappingException, JsonProcessingException {
//        JSONObject jsonObject = new JSONObject();
//
//        List<String> keys = new ArrayList<>();
//        JsonNode jsonNode = mapper.readTree(json);
//
//        Iterator<String> iterator = json.fieldNames();
//        iterator.forEachRemaining(e -> keys.add(e));
//        System.out.println(keys);
//        return keys;

    public static Set<String> getKeysInJsonUsingJsonParser(String json) throws JsonParseException, IOException {

        Set<String> keys = new HashSet<String>();
        JsonFactory factory = new JsonFactory();
        JsonParser jsonParser = factory.createParser(json);
        while (!jsonParser.isClosed()) {
            if (jsonParser.nextToken() == JsonToken.FIELD_NAME) {
                keys.add((jsonParser.getCurrentName()));
            }
        }
        JsonNode jsonNode = mapper.readTree(json);
        System.out.println(jsonNode.get(0).get("stationName"));
        return keys;
    }

      //  try{
        //    System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json));
      //  }
      //  catch (IOException e) {
      //      throw new RuntimeException(e);
      //  }

}

