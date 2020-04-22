package com.weather.air_o_inspect.Entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "weather_forecast_daily")
public class WeatherForecastDaily {
    @PrimaryKey
    private int id;
    private Long timeInMillis;
    private Long sunriseTimeInMillis;
    private Long sunsetTimeInMillis;
    private Float moonPhase;
    private Float precipIntensity;
    private Float precipIntensityMax;
    private Long precipIntensityMaxTimeInMillis;
    private Float precipProbability;
    private String precipType;
    private Float temperatureHigh;
    private Long temperatureHighTimeInMillis;
    private Float temperatureLow;
    private Long temperatureLowTimeInMillis;
    private Float dewPoint;
    private Float humidity;
    private Float pressure;
    private Float windSpeed;
    private Float windGust;
    private Long windGustTimeInMillis;
    private Float windBearing;
    private Float cloudCover;
    private Float uvIndex;
    private Long uvIndexTimeInMillis;
    private Float visibility;
    private Float ozone;


    public WeatherForecastDaily(@NonNull int id, Long timeInMillis, Long sunriseTimeInMillis,
                                Long sunsetTimeInMillis, Float moonPhase, Float precipIntensity,
                                Float precipIntensityMax, Long precipIntensityMaxTimeInMillis,
                                Float precipProbability, String precipType, Float temperatureHigh,
                                Long temperatureHighTimeInMillis, Float temperatureLow, Long temperatureLowTimeInMillis,
                                Float dewPoint, Float humidity, Float pressure, Float windSpeed, Float windGust,
                                Long windGustTimeInMillis, Float windBearing, Float cloudCover, Float uvIndex,
                                Long uvIndexTimeInMillis, Float visibility, Float ozone) {
        this.id = id;
        this.timeInMillis = timeInMillis;
        this.sunriseTimeInMillis = sunriseTimeInMillis;
        this.sunsetTimeInMillis = sunsetTimeInMillis;
        this.moonPhase = moonPhase;
        this.precipIntensity = precipIntensity;
        this.precipIntensityMax = precipIntensityMax;
        this.precipIntensityMaxTimeInMillis = precipIntensityMaxTimeInMillis;
        this.precipProbability = precipProbability;
        this.precipType = precipType;
        this.temperatureHigh = temperatureHigh;
        this.temperatureHighTimeInMillis = temperatureHighTimeInMillis;
        this.temperatureLow = temperatureLow;
        this.temperatureLowTimeInMillis = temperatureLowTimeInMillis;
        this.dewPoint = dewPoint;
        this.humidity = humidity;
        this.pressure = pressure;
        this.windSpeed = windSpeed;
        this.windGust = windGust;
        this.windGustTimeInMillis = windGustTimeInMillis;
        this.windBearing = windBearing;
        this.cloudCover = cloudCover;
        this.uvIndex = uvIndex;
        this.uvIndexTimeInMillis = uvIndexTimeInMillis;
        this.visibility = visibility;
        this.ozone = ozone;
    }

    public int getId() {
        return id;
    }

    public Long getTimeInMillis() {
        return timeInMillis;
    }

    public Long getSunriseTimeInMillis() {
        return sunriseTimeInMillis;
    }

    public Long getSunsetTimeInMillis() {
        return sunsetTimeInMillis;
    }

    public Float getMoonPhase() {
        return moonPhase;
    }

    public Float getPrecipIntensity() {
        return precipIntensity;
    }

    public Float getPrecipIntensityMax() {
        return precipIntensityMax;
    }

    public Long getPrecipIntensityMaxTimeInMillis() {
        return precipIntensityMaxTimeInMillis;
    }

    public Float getPrecipProbability() {
        return precipProbability;
    }

    public String getPrecipType() {
        return precipType;
    }

    public Float getTemperatureHigh() {
        return temperatureHigh;
    }

    public Long getTemperatureHighTimeInMillis() {
        return temperatureHighTimeInMillis;
    }

    public Float getTemperatureLow() {
        return temperatureLow;
    }

    public Long getTemperatureLowTimeInMillis() {
        return temperatureLowTimeInMillis;
    }

    public Float getDewPoint() {
        return dewPoint;
    }

    public Float getHumidity() {
        return humidity;
    }

    public Float getPressure() {
        return pressure;
    }

    public Float getWindSpeed() {
        return windSpeed;
    }

    public Float getWindGust() {
        return windGust;
    }

    public Long getWindGustTimeInMillis() {
        return windGustTimeInMillis;
    }

    public Float getWindBearing() {
        return windBearing;
    }

    public Float getCloudCover() {
        return cloudCover;
    }

    public Float getUvIndex() {
        return uvIndex;
    }

    public Long getUvIndexTimeInMillis() {
        return uvIndexTimeInMillis;
    }

    public Float getVisibility() {
        return visibility;
    }

    public Float getOzone() {
        return ozone;
    }
}
