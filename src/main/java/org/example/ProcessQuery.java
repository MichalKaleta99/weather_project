package org.example;

public class ProcessQuery {

    HTTPRequests queryClass;

    public ProcessQuery(HTTPRequests queryClass) {
        this.queryClass = queryClass;
    }

    public String process(String param) {
        return queryClass.getRequest(param).toUpperCase();
    }

}
