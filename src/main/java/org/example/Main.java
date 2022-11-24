package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.itextpdf.text.*;


import java.io.IOException;
import java.net.URISyntaxException;

//import static org.example.HTTPRequests.getRequest;
import static org.example.PDF.createPdf;
import static org.example.Stations.*;
import static org.example.UserInteraction.askUserForDecision;
import static org.example.UserInteraction.askUserForStationId;

public class Main {

    static HTTPRequests httpRequests = new HTTPRequests("");

    static String getAllStationsRequest;
    static JsonNode allStationsJson;

    static int providedStationId;

    static String getSingleStationRequest;
    static JsonNode singleStationJson;

    static boolean decision;

    public static void main(String[] args) throws IOException, DocumentException, URISyntaxException {

        getStations();
        getIdFromUser();
        getSingleStation();
        checkPdfDecision();

    }
    public static void getStations() throws JsonProcessingException {
        getAllStationsRequest = httpRequests.getAllStationsRequest();
        allStationsJson = getStationsInfo(getAllStationsRequest);
        displayStationsInfo(allStationsJson);

    }

    public static void getSingleStation() throws JsonProcessingException {
        getSingleStationRequest = httpRequests.getRequest("https://api.gios.gov.pl/pjp-api/rest/aqindex/getIndex/"+providedStationId);
        singleStationJson = getStationsInfo(getSingleStationRequest);
        displaySingleStationInfo(singleStationJson, providedStationId);

    }
    public static void checkPdfDecision() throws DocumentException, IOException, URISyntaxException {
        decision = askUserForDecision();
        if(decision){
            createPdf(singleStationJson, providedStationId);
        }
    }

    public static void getIdFromUser(){
        providedStationId = askUserForStationId();
    }

}

