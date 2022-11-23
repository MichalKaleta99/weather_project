package org.example;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
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


import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Stream;

//import static org.example.HTTPRequests.getRequest;
import static org.example.HTTPRequests.getRequest;
import static org.example.PDFTable.createPdf;
import static org.example.Stations.*;
import static org.example.UserInteraction.askUserForStationId;

public class Main {


    public static void main(String[] args) throws IOException, DocumentException, URISyntaxException {
        String getAllStationsRequest = getRequest("https://api.gios.gov.pl/pjp-api/rest/station/findAll");
        JsonNode allStationsJson = getStationsInfo(getAllStationsRequest);
        if (getAllStationsRequest=="null"){
            System.out.println("Nie znaleziono stacji. ");
        }
        else{
            displayStationsInfo(allStationsJson);

            System.out.println("\nU góry wyświetlono listę wszystkich stacji. Podaj ID stacji, dla której chcesz wyświetlić szczegółowe dane: ");
            int providedId = askUserForStationId();
            System.out.println("Szczegółowe informacje dla stacji o numerze ID "+providedId + ": ");
            String getSingleStationRequest = getRequest("https://api.gios.gov.pl/pjp-api/rest/aqindex/getIndex/"+providedId);
            JsonNode singleStationJson = getStationsInfo(getSingleStationRequest);
            displaySingleStationInfo(singleStationJson);
            createPdf(singleStationJson, providedId);

        }


    }

}

