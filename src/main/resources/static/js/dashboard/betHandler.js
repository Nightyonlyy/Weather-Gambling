var location;

function setLocation(loc) {
    location = loc;
}


function openOverlay() {
    document.getElementById('bet-overlay').style.display = 'flex';
    document.getElementById('category-selection').style.display = 'block';
    document.getElementById('bet-form').style.display = 'none';
    document.querySelectorAll('.bet-fields').forEach(field => field.style.display = 'block');
    document.getElementById(`bet-amount-field`).style.display = 'none';
    document.getElementById(`${category}-fields`).style.display = 'none';
}

function closeOverlay(event) {
    if (event.target.id === 'bet-overlay' || event.target.tagName === 'BUTTON') {
        document.getElementById('bet-overlay').style.display = 'none';
    }
}

function closeOverlay() {
    document.getElementById('bet-overlay').style.display = 'none';
}

function selectCategory(category) {
    document.getElementById('category-selection').style.display = 'none';
    document.getElementById('bet-form').style.display = 'block';
    document.querySelectorAll('.bet-fields').forEach(field => field.style.display = 'none');
    document.getElementById(`bet-amount-field`).style.display = 'block';
    document.getElementById(`${category}-fields`).style.display = 'block';
}

function submitBet() {
    const betDTO = {
        filterTimestamp: new Date().toISOString(),
        ammount: document.getElementById('bet-amount').value,
        temperature: document.getElementById('temperature-value').value || null,
        humidity: document.getElementById('humidity-value').value || null,
        windSpeed: document.getElementById('windspeed-value').value || null,
        location: location
    };

    fetch('/api/bet/place', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(betDTO)
    })
    .then(response => {
        if (response.ok) {
            alert('Bet placed successfully!');
            closeOverlay();
        } else {
            alert('Failed to place bet.');
        }
    })
    .catch(error => {
        console.error('Error placing bet:', error);
        alert('An error occurred while placing the bet.');
    });
}