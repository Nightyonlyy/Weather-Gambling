document.getElementById('search-input').addEventListener('input', function() {
    let query = this.value;

    // Check if the input length is at least 5 characters
    if (query.length >= 5) {
        // Perform the request to the backend
        fetch(`/api/location/search?query=${query}`)
            .then(response => response.json())
            .then(data => {
                displayResults(data);
            })
            .catch(error => console.error('Error:', error));
    }
});

document.getElementById('search-input').addEventListener('keypress', function(event) {
    if (event.key === 'Enter') {
        let query = this.value;

        // Perform the request to the backend
        fetch(`/api/location/search?query=${query}`)
            .then(response => response.json())
            .then(data => {
                displayResults(data);
            })
            .catch(error => console.error('Error:', error));
    }
});

function displayResults(results) {
    let resultsContainer = document.getElementById('results');
    resultsContainer.innerHTML = '';

    if (results.length > 0) {
        let ul = document.createElement('ul');
        results.forEach(result => {
            let li = document.createElement('li');
            li.textContent = result.display_name;
            li.addEventListener('click', () => selectLocation(result));
            ul.appendChild(li);
        });
        resultsContainer.appendChild(ul);
    } else {
        resultsContainer.textContent = 'Keine Ergebnisse gefunden';
    }
}

function selectLocation(location) {
    document.getElementById('search-input').value = location.display_name;

    // Directly save the location
    saveLocation(location);
}

function saveLocation(location) {
    fetch('/api/user/updateCity', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'X-CSRF-TOKEN': getCsrfToken()
        },
        body: new URLSearchParams({
            'city': location.display_name,
            'latitude': location.lat,
            'longitude': location.lon
        })
    })
        .then(response => {
            if (response.ok) {
                window.location.href = '/dashboard';
            } else {
                console.error('Error saving location');
            }
        });
}