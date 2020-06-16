package com.jamila.server.dogazdoori.dto;

public class ResponseBody {

    public ResponseBody(String distance) {
        this.nearestPersonDistance = distance;
    }

    public String nearestPersonDistance;
}
