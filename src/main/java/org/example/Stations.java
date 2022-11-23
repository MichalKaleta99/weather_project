package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.stream.StreamSupport;


public class Stations {

    static ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);


    public static JsonNode getStationsInfo(String json) throws JsonProcessingException {
        JsonNode jsonNode = mapper.readTree(json);
        return jsonNode;
    }

    public static void displayStationsInfo(JsonNode jsonNode){
        final int[] idx = {0};
        System.out.println("Znaleziono " + jsonNode.size() + " stacji. Oto ich pełna lista: ");
        StreamSupport.stream(jsonNode.spliterator(), true)
                        .forEach(station -> {
                            System.out.println(idx[0] +1+".");
                            System.out.println("ID stacji - " + station.get("id").toString().replaceAll("\"", ""));
                            System.out.println("Nazwa stacji - " + station.get("stationName").toString().replaceAll("\"", ""));
                            System.out.println("Szerokość geograficzna - " + station.get("gegrLat").toString().replaceAll("\"", ""));
                            System.out.println("Długość geograficzna - " + station.get("gegrLon").toString().replaceAll("\"", ""));
                            System.out.println("Miasto - " + station.get("city").get("name").toString().replaceAll("\"", ""));
                            System.out.println("Gmina - " + station.get("city").get("commune").get("communeName").toString().replaceAll("\"", ""));
                            System.out.println("Powiat - " + station.get("city").get("commune").get("districtName").toString().replaceAll("\"", ""));
                            System.out.println("Województwo - " + station.get("city").get("commune").get("provinceName").toString().replaceAll("\"", "").toLowerCase());
                            System.out.println("Ulica - " + station.get("addressStreet").toString().replaceAll("\"", ""));
                            idx[0]++;
                        });
    }

    public static void displaySingleStationInfo(JsonNode station) throws JsonProcessingException {

        System.out.println("Wartość indeksu dla stacji (wartość  najgorszego z indeksów cząstkowych zanieczyszczeń) - " + station.get("stIndexLevel").get("indexLevelName").toString().replaceAll("\"", "") + " (" + station.get("stIndexLevel").get("id").toString().replaceAll("\"", "") + ")");
        System.out.println("Data danych źródłowych, z których policzono wartość indeksu - " + station.get("stSourceDataDate").toString().replaceAll("\"", ""));
        displayAirInfo(station);

    }
    public static void displayAirInfo(JsonNode station){
        displaySo2Info(station);
        displayNo2Info(station);
        displayPm10Info(station);
        displayPm25Info(station);
        displayO3Info(station);
    }
    public static void displaySo2Info(JsonNode station){
        if (station.get("so2IndexLevel").get("indexLevelName")==null){
            System.out.println("\nNa tej stacji nie są wykonywane pomiary dla wskaźnika SO2");
        }
        else{
            System.out.println("\nData wykonania obliczeń indeksu dla wskaźnika SO2 - " + station.get("so2CalcDate").toString().replaceAll("\"", ""));
            System.out.println("Wartość indeksu dla wskaźnika SO2 - " + station.get("so2IndexLevel").get("indexLevelName").toString().replaceAll("\"", "") + " (" + station.get("stIndexLevel").get("id").toString().replaceAll("\"", "") + ")");
            System.out.println("Data danych źródłowych, z których policzono wartość indeksu dla wskaźnika SO2 - " + station.get("so2SourceDataDate").toString().replaceAll("\"", ""));
        }
    }
    public static void displayNo2Info(JsonNode station){
        if (station.get("no2IndexLevel").get("indexLevelName")==null){
            System.out.println("\nNa tej stacji nie są wykonywane pomiary dla wskaźnika NO2");
        }
        else{
            System.out.println("\nData wykonania obliczeń indeksu dla wskaźnika NO2 - " + station.get("no2CalcDate").toString().replaceAll("\"", ""));
            System.out.println("Wartość indeksu dla wskaźnika NO2 - " + station.get("no2IndexLevel").get("indexLevelName").toString().replaceAll("\"", "") + " (" + station.get("stIndexLevel").get("id").toString().replaceAll("\"", "") + ")");
            System.out.println("Data danych źródłowych, z których policzono wartość indeksu dla wskaźnika NO2 - " + station.get("no2SourceDataDate").toString().replaceAll("\"", ""));
        }

    }
    public static void displayPm10Info(JsonNode station){
        if (station.get("pm10IndexLevel").get("indexLevelName")==null){
            System.out.println("\nNa tej stacji nie są wykonywane pomiary dla wskaźnika PM10");
        }
        else{
            System.out.println("\nData wykonania obliczeń indeksu dla wskaźnika PM10 - " + station.get("pm10CalcDate").toString().replaceAll("\"", ""));
            System.out.println("Wartość indeksu dla wskaźnika PM10 - " + station.get("pm10IndexLevel").get("indexLevelName").toString().replaceAll("\"", "") + " (" + station.get("stIndexLevel").get("id").toString().replaceAll("\"", "") + ")");
            System.out.println("Data danych źródłowych, z których policzono wartość indeksu dla wskaźnika PM10 - " + station.get("pm10SourceDataDate").toString().replaceAll("\"", ""));
        }

    }
    public static void displayPm25Info(JsonNode station){
        if (station.get("pm25IndexLevel").get("indexLevelName")==null){
            System.out.println("\nNa tej stacji nie są wykonywane pomiary dla wskaźnika PM25");
        }
        else{
            System.out.println("\nData wykonania obliczeń indeksu dla wskaźnika PM25 - " + station.get("pm25CalcDate").toString().replaceAll("\"", ""));
            System.out.println("Wartość indeksu dla wskaźnika PM25 - " + station.get("pm25IndexLevel").get("indexLevelName").toString().replaceAll("\"", "") + " (" + station.get("stIndexLevel").get("id").toString().replaceAll("\"", "") + ")");
            System.out.println("Data danych źródłowych, z których policzono wartość indeksu dla wskaźnika PM25 - " + station.get("pm25SourceDataDate").toString().replaceAll("\"", ""));
        }

    }
    public static void displayO3Info(JsonNode station){

        if (station.get("o3IndexLevel").get("indexLevelName")==null){
            System.out.println("\nNa tej stacji nie są wykonywane pomiary dla wskaźnika O3");
        }
        else{
            System.out.println("\nData wykonania obliczeń indeksu dla wskaźnika O3 - " + station.get("o3CalcDate").toString().replaceAll("\"", ""));
            System.out.println("Wartość indeksu dla wskaźnika O3 - " + station.get("o3IndexLevel").get("indexLevelName").toString().replaceAll("\"", "") + " (" + station.get("stIndexLevel").get("id").toString().replaceAll("\"", "") + ")");
            System.out.println("Data danych źródłowych, z których policzono wartość indeksu dla wskaźnika O3 - " + station.get("o3SourceDataDate").toString().replaceAll("\"", ""));
        }

    }

}
