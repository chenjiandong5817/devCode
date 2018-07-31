package com.plugs.module_driver.dtos;

public class LatLngDto {

    private Double longitude;
    private Double latitude;

    public LatLngDto() {
    }

    public LatLngDto(Double longitude, Double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}
