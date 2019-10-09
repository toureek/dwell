package com.dwell.it.model.geo;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;

@Data
@ToString
public class GeoInfoResponse {

    private String status;

    private String info;

    private String infocode;

    private String count;

    private ArrayList<HashMap<String, Object>> geocodes;

    public GeoInfoResponse() {
    }

    public GeoInfoResponse(String status, String info, String infocode, String count, ArrayList<HashMap<String, Object>> geocodes) {
        this.status = status;
        this.info = info;
        this.infocode = infocode;
        this.count = count;
        this.geocodes = geocodes;
    }
}