document.addEventListener('DOMContentLoaded', function() {
    function extractTime(datetime) {
        return datetime.split('T')[1];
    }

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
        document.getElementById('current-selected-location').textContent = "Aktuelles Wetter in: "+currentWeather.location.city;
        document.getElementById('current-temperature').textContent = "Temperatur: " + currentWeather.temperature + "°C";
        document.getElementById('current-time').textContent = "Uhrzeit: " + extractTime(currentWeather.time) + " Uhr";
        document.getElementById('current-windspeed').textContent = "Windgeschwindigkeit: " + currentWeather.windspeed + " km/h";
        //console.log(currentWeather)
    }

    function displayHourlyWeather(hourlyWeather) {
        document.getElementById('current-humidity').textContent = "Luftfeuchtigkeit: " + hourlyWeather.relative_humidity_2m[0] + "%";
        console.log(hourlyWeather);
        const container = document.getElementById('hourly-weather');
        let content = '<h2>Stündliche Wetterdaten (nächste 12 Stunden)</h2>';
        for (let i = 0; i < 12; i++) {
            document.getElementById('hourly-data-' + i).innerHTML = `
                <div>
                    <p>${extractTime(hourlyWeather.time[i])}</p>
                    <p>${hourlyWeather.temperature_2m[i]}</p>
                    <p>${hourlyWeather.relative_humidity_2m[i]}</p>
                    <p>${hourlyWeather.wind_speed_10m[i]}</p>
                </div>
            `
            //content += `
            //    <div>
            //        <p>Zeit: ${hourlyWeather.times[i]}</p>
            //        <p>Temperatur: ${hourlyWeather.temperatures[i]}°C</p>
            //        <p>Luftfeuchtigkeit: ${hourlyWeather.humidities[i]}%</p>
            //        <p>Windgeschwindigkeit: ${hourlyWeather.windSpeeds[i]} km/h</p>
            //    </div>
            //`;
        }
        //container.innerHTML = content;
    }

    function displayDailyWeather(dailyWeather) {
        const container = document.getElementById('daily-weather');
        let content = '<h2>Tägliche Wetterdaten (nächste 7 Tage)</h2>';
        for (let i = 0; i < dailyWeather.times.length; i++) {
            //content += `
            //    <div>
            //        <p>Datum: ${dailyWeather.times[i]}</p>
            //        <p>Max Temperatur: ${dailyWeather.maxTemperatures[i]}°C</p>
            //        <p>Min Temperatur: ${dailyWeather.minTemperatures[i]}°C</p>
            //    </div>
            //`;
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