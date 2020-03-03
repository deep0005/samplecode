package com.mindgeek.samplecode.datatransferobject.response;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class GeneralResponse {

    private String message;
    private Object data;
    private PaginationData metadata;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public PaginationData getMetadata() {
        return metadata;
    }

    public void setMetadata(PaginationData metadata) {
        this.metadata = metadata;
    }

    public static GeneralResponse getResponse(Boolean type, Object body){
        GeneralResponse newResponse = new GeneralResponse();
        newResponse.message = type ? "success" : "false";

        if(body.getClass().equals(PageImpl.class)){
            newResponse.data = ((Page)body).getContent();
            newResponse.metadata = new PaginationData((Page)body);
        }else{
            newResponse.data = body;
        }

        return newResponse;
    }
}
