// URL de l'API backend (à remplacer par l'URL de votre déploiement Railway)
const API_URL = 'https://votre-app-railway.up.railway.app';

// Éléments du DOM
const number1Input = document.getElementById('number1');
const number2Input = document.getElementById('number2');
const addButton = document.getElementById('addButton');
const reduceButton = document.getElementById('reduceButton');
const resultSpan = document.getElementById('result');

// Fonction pour effectuer les requêtes API
async function makeRequest(endpoint, numbers) {
    try {
        const response = await fetch(`${API_URL}${endpoint}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(numbers)
        });

        if (!response.ok) {
            throw new Error(`Erreur HTTP: ${response.status}`);
        }

        const data = await response.json();
        return data;
    } catch (error) {
        console.error('Erreur:', error);
        resultSpan.textContent = 'Erreur de connexion';
        throw error;
    }
}

// Gestionnaire pour l'addition
addButton.addEventListener('click', async () => {
    const numbers = {
        number1: parseFloat(number1Input.value),
        number2: parseFloat(number2Input.value)
    };

    if (isNaN(numbers.number1) || isNaN(numbers.number2)) {
        resultSpan.textContent = 'Veuillez entrer des nombres valides';
        return;
    }

    try {
        const result = await makeRequest('/add', numbers);
        resultSpan.textContent = result.result;
    } catch (error) {
        // L'erreur est déjà gérée dans makeRequest
    }
});

// Gestionnaire pour la soustraction
reduceButton.addEventListener('click', async () => {
    const numbers = {
        number1: parseFloat(number1Input.value),
        number2: parseFloat(number2Input.value)
    };

    if (isNaN(numbers.number1) || isNaN(numbers.number2)) {
        resultSpan.textContent = 'Veuillez entrer des nombres valides';
        return;
    }

    try {
        const result = await makeRequest('/reduce', numbers);
        resultSpan.textContent = result.result;
    } catch (error) {
        // L'erreur est déjà gérée dans makeRequest
    }
}); 