package com.weather.air_o_inspect.Entities;

public class WeatherCurrentRequired {

    private Float sunshine;
    private Float temperature;
    private Float windSpeed;
    private Float windGust;
    private Float precipIntensity;
    private Float precipProbability;
    private String dateTime;
    private String cityName;
    private int flyStatus;

    public Float getSunshine() {
        return sunshine;
    }

    public void setSunshine(Float sunshine) {
        this.sunshine = sunshine;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public Float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Float getWindGust() {
        return windGust;
    }

    public void setWindGust(Float windGust) {
        this.windGust = windGust;
    }

    public Float getPrecipIntensity() {
        return precipIntensity;
    }

    public void setPrecipIntensity(Float precipIntensity) {
        this.precipIntensity = precipIntensity;
    }

    public Float getPrecipProbability() {
        return precipProbability;
    }

    public void setPrecipProbability(Float precipProbability) {
        this.precipProbability = precipProbability;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getFlyStatus() {
        return flyStatus;
    }

    public void setFlyStatus(int flyStatus) {
        this.flyStatus = flyStatus;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
