
function addAuthHeader(headers = {}) {
    console.log('addAuthHeader called');
    console.log('Initial headers:', headers);
    const token = localStorage.getItem('token');
    console.log('Token from localStorage:', token);
    if (token) {
        headers['Authorization'] = `Bearer ${token}`;
        console.log('Token added to headers. New headers:', headers);
    } else {
        console.log('No token found in localStorage');
    }
    return headers;
}

// Sobrescribir el método fetch global para incluir automáticamente el token
const originalFetch = window.fetch;
window.fetch = function(...args) {
	console.log('Fetch intercepted');
    let [resource, config] = args;
    if (!config) {
        config = {};
    }
    if (!config.headers) {
        config.headers = {};
    }
    config.headers = addAuthHeader(config.headers);
    return originalFetch(resource, config);
};
console.log('auth.js loaded and executed');