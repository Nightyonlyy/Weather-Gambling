document.addEventListener('DOMContentLoaded', function() {
    function fetchCurrentWeather() {
        fetch('/api/weather/current')
            .then(response => response.json())
            .then(data => displayCurrentWeather(data))
            .catch(error => console.error('Error fetching current weather data:', error));
    }

    function fetchHourlyWeather() {
        fetch('/api/weather/hourly')
            .then(response => response.json())
            .then(data => displayHourlyWeather(data))
            .catch(error => console.error('Error fetching hourly weather data:', error));
    }

    function fetchDailyWeather() {
        fetch('/api/weather/daily')
            .then(response => response.json())
            .then(data => displayDailyWeather(data))
            .catch(error => console.error('Error fetching daily weather data:', error));
    }

    function displayCurrentWeather(currentWeather) {
        const container = document.getElementById('current-weather');
        container.innerHTML = `
            <h2>Aktuelle Wetterdaten</h2>
            <p>Zeit: ${currentWeather.time}</p>
            <p>Temperatur: ${currentWeather.temperature}°C</p>
            <p>Windgeschwindigkeit: ${currentWeather.windSpeed} km/h</p>
        `;
    }

    function displayHourlyWeather(hourlyWeather) {
        const container = document.getElementById('hourly-weather');
        let content = '<h2>Stündliche Wetterdaten (nächste 12 Stunden)</h2>';
        for (let i = 0; i < 12; i++) {
            content += `
                <div>
                    <p>Zeit: ${hourlyWeather.times[i]}</p>
                    <p>Temperatur: ${hourlyWeather.temperatures[i]}°C</p>
                    <p>Luftfeuchtigkeit: ${hourlyWeather.humidities[i]}%</p>
                    <p>Windgeschwindigkeit: ${hourlyWeather.windSpeeds[i]} km/h</p>
                </div>
            `;
        }
        container.innerHTML = content;
    }

    function displayDailyWeather(dailyWeather) {
        const container = document.getElementById('daily-weather');
        let content = '<h2>Tägliche Wetterdaten (nächste 7 Tage)</h2>';
        for (let i = 0; i < dailyWeather.times.length; i++) {
            content += `
                <div>
                    <p>Datum: ${dailyWeather.times[i]}</p>
                    <p>Max Temperatur: ${dailyWeather.maxTemperatures[i]}°C</p>
                    <p>Min Temperatur: ${dailyWeather.minTemperatures[i]}°C</p>
                </div>
            `;
        }
        container.innerHTML = content;
    }

    fetchCurrentWeather();
    setInterval(fetchCurrentWeather, 5 * 60 * 1000); // Alle 5 Minuten aktualisieren

    fetchHourlyWeather();
    setInterval(fetchHourlyWeather, 60 * 60 * 1000); // Jede Stunde aktualisieren

    fetchDailyWeather();
    setInterval(fetchDailyWeather, 24 * 60 * 60 * 1000); // Täglich aktualisieren
});
