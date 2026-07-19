package com.nimbusx.model;

public class ForecastDay {
    private String date;
    private double tempMin;
    private double tempMax;
    private int humidity;
    private String description;
    private String icon;

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public double getTempMin() { return tempMin; }
    public void setTempMin(double tempMin) { this.tempMin = tempMin; }
    public double getTempMax() { return tempMax; }
    public void setTempMax(double tempMax) { this.tempMax = tempMax; }
    public int getHumidity() { return humidity; }
    public void setHumidity(int humidity) { this.humidity = humidity; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }
}
