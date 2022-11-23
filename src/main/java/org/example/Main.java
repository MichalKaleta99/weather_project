package org.example;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class Main {
    static CloseableHttpClient httpclient = HttpClients.createDefault();
    static ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);


    public static void main(String[] args) throws IOException, DocumentException, URISyntaxException {

//        String allStations = getRequest("https://api.gios.gov.pl/pjp-api/rest/station/findAll");
//        System.out.println("Oto pełna lista stacji pogodowych: ");
//        displayStationsInfo(allStations);
//
//
//        System.out.println("\nU góry wyświetlono listę wszystkich stacji. Podaj ID stacji, dla której chcesz wyświetlić szczegółowe dane: ");
//        int providedId = askUserForStationId();
//        System.out.println("Szczegółowe informacje dla stacji o numerze ID "+providedId + ": ");
//        String userStation = getRequest("https://api.gios.gov.pl/pjp-api/rest/aqindex/getIndex/"+providedId);
//        displaySingleStationInfo(userStation);
          createPdf();

    }

    private static int askUserForStationId() {
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

    public static void createPdf() throws DocumentException, IOException, URISyntaxException {

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("iTextTable.pdf"));

        document.open();

        PdfPTable table = new PdfPTable(3);
        addTableHeader(table);
        addRows(table);

        document.add(table);
        document.close();
    }

    private static void addTableHeader(PdfPTable table) {
        Stream.of("Wskaźnik", "1. Data wykonania obliczeń indeksu dla wskaźnika\n2. Data danych źródłowych, z których policzono wartość indeksu dla wskaźnika", "Wartość wskaźnika")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    private static void addRows(PdfPTable table) {
        table.addCell("row 1, col 2"+"test");
        table.addCell("row 1, col 1");
        table.addCell("row 1, col 11");
        table.addCell("row 4, col 4");
        table.addCell("row 5, col 5");
    }



//    public static Set<String> getKeysInJsonUsingJsonParser(String json) throws JsonParseException, IOException {
//
//        Set<String> keys = new HashSet<String>();
//        JsonFactory factory = new JsonFactory();
//        JsonParser jsonParser = factory.createParser(json);
//        while (!jsonParser.isClosed()) {
//            if (jsonParser.nextToken() == JsonToken.FIELD_NAME) {
//                keys.add((jsonParser.getCurrentName()));
//            }
//        }
//        int idx = 0;
//        JsonNode jsonNode = mapper.readTree(json);
//        System.out.println("Znaleziono " + jsonNode.size() + " stacji. Oto ich pełna lista: ");
//        for (JsonNode stations : jsonNode){
//            System.out.println(idx+1+".");
//            System.out.println("ID stacji - " + stations.get("id"));
//            System.out.println("Nazwa stacji - " + stations.get("stationName"));
//            System.out.println("Szerokość geograficzna - " + stations.get("gegrLat"));
//            System.out.println("Długość geograficzna - " + stations.get("gegrLon"));
//            System.out.println("Miasto - " + stations.get("city").get("name"));
//            System.out.println("Gmina - " + stations.get("city").get("commune").get("communeName"));
//            System.out.println("Powiat - " + stations.get("city").get("commune").get("districtName"));
//            System.out.println("Województwo - " + stations.get("city").get("commune").get("provinceName"));
//            System.out.println("Ulica - " + stations.get("addressStreet"));
//            idx++;
//        }
//        return keys;
//    }

    public static void displayStationsInfo(String json) throws JsonProcessingException {
        int idx = 0;
        JsonNode jsonNode = mapper.readTree(json);
        System.out.println("Znaleziono " + jsonNode.size() + " stacji. Oto ich pełna lista: ");
        for (JsonNode stations : jsonNode){
            System.out.println(idx+1+".");
            System.out.println("ID stacji - " + stations.get("id"));
            System.out.println("Nazwa stacji - " + stations.get("stationName"));
            System.out.println("Szerokość geograficzna - " + stations.get("gegrLat"));
            System.out.println("Długość geograficzna - " + stations.get("gegrLon"));
            System.out.println("Miasto - " + stations.get("city").get("name"));
            System.out.println("Gmina - " + stations.get("city").get("commune").get("communeName"));
            System.out.println("Powiat - " + stations.get("city").get("commune").get("districtName"));
            System.out.println("Województwo - " + stations.get("city").get("commune").get("provinceName"));
            System.out.println("Ulica - " + stations.get("addressStreet"));
            idx++;
        }
    }

    public static void displaySingleStationInfo(String json) throws JsonProcessingException {
        int idx = 0;
        JsonNode jsonNode = mapper.readTree(json);
        System.out.println("Wartość indeksu dla stacji (wartość  najgorszego z indeksów cząstkowych zanieczyszczeń) - " + jsonNode.get("stIndexLevel").get("indexLevelName") + "(" + jsonNode.get("stIndexLevel").get("id") + ")");
        System.out.println("Data danych źródłowych, z których policzono wartość indeksu - " + jsonNode.get("stSourceDataDate"));
        System.out.println("\nData wykonania obliczeń indeksu dla wskaźnika SO2 - " + jsonNode.get("so2CalcDate"));
        if (jsonNode.get("so2IndexLevel").get("indexLevelName")==null){
            System.out.println("Na tej stacji nie są wykonywane pomiary dla wskaźnika SO2");
        }
        else{
            System.out.println("Wartość indeksu dla wskaźnika SO2 - " + jsonNode.get("so2IndexLevel").get("indexLevelName") + "(" + jsonNode.get("stIndexLevel").get("id") + ")");
            System.out.println("Data danych źródłowych, z których policzono wartość indeksu dla wskaźnika SO2- " + jsonNode.get("so2SourceDataDate"));
        }

        System.out.println("\nData wykonania obliczeń indeksu dla wskaźnika NO2 - " + jsonNode.get("no2CalcDate"));
        if (jsonNode.get("no2IndexLevel").get("indexLevelName")==null){
            System.out.println("Na tej stacji nie są wykonywane pomiary dla wskaźnika NO2");
        }
        else{
            System.out.println("Wartość indeksu dla wskaźnika NO2 - " + jsonNode.get("no2IndexLevel").get("indexLevelName") + "(" + jsonNode.get("stIndexLevel").get("id") + ")");
            System.out.println("Data danych źródłowych, z których policzono wartość indeksu dla wskaźnika NO2- " + jsonNode.get("no2SourceDataDate"));
        }

        System.out.println("\nData wykonania obliczeń indeksu dla wskaźnika PM10 - " + jsonNode.get("pm10CalcDate"));
        if (jsonNode.get("pm10IndexLevel").get("indexLevelName")==null){
            System.out.println("Na tej stacji nie są wykonywane pomiary dla wskaźnika PM10");
        }
        else{
            System.out.println("Wartość indeksu dla wskaźnika PM10 - " + jsonNode.get("pm10IndexLevel").get("indexLevelName") + "(" + jsonNode.get("stIndexLevel").get("id") + ")");
            System.out.println("Data danych źródłowych, z których policzono wartość indeksu dla wskaźnika PM10- " + jsonNode.get("pm10SourceDataDate"));
        }

        System.out.println("\nData wykonania obliczeń indeksu dla wskaźnika PM25 - " + jsonNode.get("pm25CalcDate"));
        if (jsonNode.get("pm25IndexLevel").get("indexLevelName")==null){
            System.out.println("Na tej stacji nie są wykonywane pomiary dla wskaźnika PM25");
        }
        else{
            System.out.println("Wartość indeksu dla wskaźnika PM25 - " + jsonNode.get("pm25IndexLevel").get("indexLevelName") + "(" + jsonNode.get("stIndexLevel").get("id") + ")");
            System.out.println("Data danych źródłowych, z których policzono wartość indeksu dla wskaźnika PM25- " + jsonNode.get("pm25SourceDataDate"));
        }

        System.out.println("\nData wykonania obliczeń indeksu dla wskaźnika O3 - " + jsonNode.get("o3CalcDate"));
        if (jsonNode.get("o3IndexLevel").get("indexLevelName")==null){
            System.out.println("Na tej stacji nie są wykonywane pomiary dla wskaźnika O3");
        }
        else{
            System.out.println("Wartość indeksu dla wskaźnika O3 - " + jsonNode.get("o3IndexLevel").get("indexLevelName") + "(" + jsonNode.get("stIndexLevel").get("id") + ")");
            System.out.println("Data danych źródłowych, z których policzono wartość indeksu dla wskaźnika O3 - " + jsonNode.get("o3SourceDataDate"));
        }

    }

}

