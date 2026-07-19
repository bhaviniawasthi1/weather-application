package com.nimbusx.model;

import java.util.List;

public class WeatherResponse {
    private String city;
    private double temperature;
    private int humidity;
    private String description;
    private String icon;
    private double windSpeed;
    private List<ForecastDay> forecast;

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public double getTemperature() { return temperature; }
    public void setTemperature(double temperature) { this.temperature = temperature; }
    public int getHumidity() { return humidity; }
    public void setHumidity(int humidity) { this.humidity = humidity; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }
    public double getWindSpeed() { return windSpeed; }
    public void setWindSpeed(double windSpeed) { this.windSpeed = windSpeed; }
    public List<ForecastDay> getForecast() { return forecast; }
    public void setForecast(List<ForecastDay> forecast) { this.forecast = forecast; }
}
