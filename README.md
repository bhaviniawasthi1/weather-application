# NimbusX Weather Application

A real-time weather forecast application built with **Java (Spring Boot)** and a lightweight **HTML/CSS/JS** frontend. It fetches live weather data from the [OpenWeatherMap API](https://openweathermap.org/api) and displays temperature, humidity, wind speed, and a 5-day forecast.

![NimbusX Screenshot](https://img.shields.io/badge/status-active-brightgreen)

---

## Features

- **Current Weather** — Temperature, humidity, wind speed, and weather description
- **5-Day Forecast** — Daily high/low temperatures, humidity, and weather icons
- **Real-time Data** — Fetches live data from OpenWeatherMap via REST API
- **Responsive UI** — Clean, mobile-friendly interface with gradient design
- **REST Backend** — Spring Boot API that proxies and transforms third-party data

## Tech Stack

| Layer      | Technology                             |
|------------|----------------------------------------|
| Backend    | Java 22, Spring Boot 3.3.5             |
| API Client | RestTemplate (JSON parsing with Jackson) |
| Frontend   | HTML5, CSS3, Vanilla JavaScript (ES6+) |
| Build      | Maven 3.9+                             |

## Project Structure

```
weather-application/
├── pom.xml
├── src/main/
│   ├── java/com/nimbusx/
│   │   ├── NimbusXApplication.java          # Application entry point
│   │   ├── controller/
│   │   │   └── WeatherController.java       # REST endpoint: GET /api/weather?city=
│   │   ├── service/
│   │   │   └── WeatherService.java          # OpenWeatherMap integration & JSON parsing
│   │   ├── model/
│   │   │   ├── WeatherResponse.java         # Current weather data model
│   │   │   └── ForecastDay.java             # Forecast day data model
│   │   └── config/
│   │       └── CorsConfig.java              # CORS configuration
│   └── resources/
│       ├── application.properties           # Server port & API key config
│       └── static/
│           ├── index.html                   # Main frontend page
│           ├── style.css                    # Styling
│           └── app.js                       # Frontend logic & API calls
```

## Getting Started

### Prerequisites

- Java 17 or later
- Maven 3.9+
- A free [OpenWeatherMap API key](https://openweathermap.org/api)

### Installation

1. **Clone the repository**

   ```bash
   git clone https://github.com/your-username/nimbusx-weather.git
   cd nimbusx-weather
   ```

2. **Configure your API key**

   Open `src/main/resources/application.properties` and replace the placeholder:

   ```properties
   weather.api.key=your_actual_api_key_here
   ```

3. **Build the project**

   ```bash
   mvn clean package
   ```

4. **Run the application**

   ```bash
   mvn spring-boot:run
   ```

5. **Open the app**

   Navigate to [http://localhost:8080](http://localhost:8080)

## API Endpoint

### `GET /api/weather?city={cityName}`

Returns current weather and 5-day forecast for the specified city.

**Example request:**

```
GET http://localhost:8080/api/weather?city=London
```

**Sample response:**

```json
{
  "city": "London",
  "temperature": 15.2,
  "humidity": 72,
  "description": "scattered clouds",
  "icon": "03d",
  "windSpeed": 4.5,
  "forecast": [
    {
      "date": "Mon, Jul 20",
      "tempMin": 12.3,
      "tempMax": 18.1,
      "humidity": 68,
      "description": "light rain",
      "icon": "10d"
    }
  ]
}
```

## Usage

1. Type a city name into the search bar (e.g., London, Mumbai, Tokyo)
2. Press **Enter** or click **Get Weather**
3. View current conditions and the 5-day forecast

## Built By

**Bhavini Awasthi**

---

*NimbusX — Real-time Weather Forecast Application*
