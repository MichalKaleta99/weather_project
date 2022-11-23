package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Stream;

public class PDFTable {
    static SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
    static Date date = new Date(System.currentTimeMillis());

    private static void addTableHeader(PdfPTable table) {
        Stream.of("Wskaźnik", "1. Data wykonania obliczeń indeksu dla wskaźnika\n2. Data danych źródłowych, z których policzono wartość indeksu dla wskaźnika\n", "Wartość wskaźnika")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }
    public static void createPdf(JsonNode station, int id) throws DocumentException, IOException, URISyntaxException {

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("Measurements_results.pdf"));

        document.open();
        addMetaData(document, id);

        Font f = new Font();


        f.setStyle(Font.BOLD);
        f.setSize(32);

        document.add(new Paragraph("Informacje na temat stacji pomiarowej o numerze "+id+"\nStan na "+formatter.format(date)+"\n\n", f));

        PdfPTable table = new PdfPTable(3);
        createTable(table, station);

        document.add(table);
        document.close();
    }

    private static void addMetaData(Document document, int id) {
        document.addTitle("Wyniki dla stacji pomiarowej o numerze ID "+id+" - stan na "+formatter.format(date));
        document.addAuthor("Marta Wołk");
        document.addCreator("Marta Wołk");
    }

    private static void createTable(PdfPTable table, JsonNode stationInfo) {
        addTableHeader(table);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

        addInfoToCells(table, stationInfo);
    }

    public static void addInfoToCells(PdfPTable table, JsonNode stationInfo){
        addSo2Parameters(table, stationInfo);
        addNo2Parameters(table, stationInfo);
        addPm10Parameters(table, stationInfo);
        addPm25Parameters(table, stationInfo);
        addO3Parameters(table, stationInfo);
    }

    public static void addSo2Parameters(PdfPTable table, JsonNode stationInfo){
        table.addCell("SO2");
        if(stationInfo.get("so2IndexLevel").get("indexLevelName")==null){

            table.addCell("Na tej stacji nie wykonuje się pomiarów dla wskaźnika SO2");
            table.addCell("-");
        }
        else{
            table.addCell("1. "+stationInfo.get("so2CalcDate").toString().replaceAll("\"", "") + "\n2. "+stationInfo.get("so2SourceDataDate").toString().replaceAll("\"", ""));
            table.addCell(stationInfo.get("so2IndexLevel").get("indexLevelName").toString().replaceAll("\"", ""));
        }
    }
    public static void addNo2Parameters(PdfPTable table, JsonNode stationInfo){
        table.addCell("NO2");
        if(stationInfo.get("no2IndexLevel").get("indexLevelName")==null){

            table.addCell("Na tej stacji nie wykonuje się pomiarów dla wskaźnika NO2");
            table.addCell("-");
        }
        else{
            table.addCell("1. "+stationInfo.get("no2CalcDate").toString().replaceAll("\"", "") + "\n2. "+stationInfo.get("no2SourceDataDate").toString().replaceAll("\"", ""));
            table.addCell(stationInfo.get("no2IndexLevel").get("indexLevelName").toString().replaceAll("\"", ""));
        }
    }
    public static void addPm10Parameters(PdfPTable table, JsonNode stationInfo){
        table.addCell("PM10");
        if(stationInfo.get("pm10IndexLevel").get("indexLevelName")==null){

            table.addCell("Na tej stacji nie wykonuje się pomiarów dla wskaźnika PM10");
            table.addCell("-");
        }
        else{
            table.addCell("1. "+stationInfo.get("pm10CalcDate").toString().replaceAll("\"", "") + "\n2. "+stationInfo.get("pm10SourceDataDate").toString().replaceAll("\"", ""));
            table.addCell(stationInfo.get("pm10IndexLevel").get("indexLevelName").toString().replaceAll("\"", ""));
        }
    }
    public static void addPm25Parameters(PdfPTable table, JsonNode stationInfo){
        table.addCell("PM25");
        if(stationInfo.get("pm25IndexLevel").get("indexLevelName")==null){

            table.addCell("Na tej stacji nie wykonuje się pomiarów dla wskaźnika PM25");
            table.addCell("-");
        }
        else{
            table.addCell("1. "+stationInfo.get("pm25CalcDate").toString().replaceAll("\"", "") + "\n2. "+stationInfo.get("pm25SourceDataDate").toString().replaceAll("\"", ""));
            table.addCell(stationInfo.get("pm25IndexLevel").get("indexLevelName").toString().replaceAll("\"", ""));
        }
    }
    public static void addO3Parameters(PdfPTable table, JsonNode stationInfo){
        table.addCell("O3");
        if(stationInfo.get("o3IndexLevel").get("indexLevelName")==null){

            table.addCell("Na tej stacji nie wykonuje się pomiarów dla wskaźnika O3");
            table.addCell("-");
        }
        else{
            table.addCell("1. "+stationInfo.get("o3CalcDate").toString().replaceAll("\"", "") + "\n2. "+stationInfo.get("o3SourceDataDate").toString().replaceAll("\"", ""));
            table.addCell(stationInfo.get("o3IndexLevel").get("indexLevelName").toString().replaceAll("\"", ""));
        }
    }
}
