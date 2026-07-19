const API_BASE = 'http://localhost:8080/api';

const cityInput = document.getElementById('cityInput');
const searchBtn = document.getElementById('searchBtn');
const loading = document.getElementById('loading');
const error = document.getElementById('error');
const currentSection = document.getElementById('currentWeather');
const forecastSection = document.getElementById('forecast');

searchBtn.addEventListener('click', fetchWeather);
cityInput.addEventListener('keydown', (e) => {
    if (e.key === 'Enter') fetchWeather();
});

async function fetchWeather() {
    const city = cityInput.value.trim();
    if (!city) {
        showError('Please enter a city name.');
        return;
    }

    hideAllSections();
    loading.classList.remove('hidden');

    try {
        const res = await fetch(`${API_BASE}/weather?city=${encodeURIComponent(city)}`);
        const data = await res.json();
        if (!res.ok) throw new Error(data.error || 'Failed to fetch weather data.');
        displayCurrentWeather(data);
        displayForecast(data.forecast);
    } catch (err) {
        showError(err.message);
    } finally {
        loading.classList.add('hidden');
    }
}

function displayCurrentWeather(data) {
    document.getElementById('cityName').textContent = data.city;
    document.getElementById('temperature').textContent = Math.round(data.temperature);
    document.getElementById('weatherIcon').src = `https://openweathermap.org/img/wn/${data.icon}@2x.png`;
    document.getElementById('weatherIcon').alt = data.description;
    document.getElementById('description').textContent = data.description;
    document.getElementById('humidity').textContent = `${data.humidity}%`;
    document.getElementById('windSpeed').textContent = `${data.windSpeed} m/s`;
    currentSection.classList.remove('hidden');
}

function displayForecast(forecast) {
    const container = document.getElementById('forecastCards');
    container.innerHTML = '';

    forecast.forEach((day) => {
        const card = document.createElement('div');
        card.className = 'forecast-card';
        card.innerHTML = `
            <div class="day">${day.date}</div>
            <img src="https://openweathermap.org/img/wn/${day.icon}@2x.png" alt="${day.description}" />
            <div class="temps">
                <span class="high">${Math.round(day.tempMax)}°</span> /
                <span class="low">${Math.round(day.tempMin)}°</span>
            </div>
            <div class="hum">💧 ${day.humidity}%</div>
        `;
        container.appendChild(card);
    });

    forecastSection.classList.remove('hidden');
}

function showError(msg) {
    error.textContent = msg;
    error.classList.remove('hidden');
}

function hideAllSections() {
    error.classList.add('hidden');
    currentSection.classList.add('hidden');
    forecastSection.classList.add('hidden');
}
