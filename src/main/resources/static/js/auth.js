// auth.js
function addAuthHeader(headers = {}) {
    const token = localStorage.getItem('token');
    if (token) {
        headers['Authorization'] = `Bearer ${token}`;
    }
    return headers;
}

// Sobrescribir el método fetch global para incluir automáticamente el token
const originalFetch = window.fetch;
window.fetch = function(...args) {
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