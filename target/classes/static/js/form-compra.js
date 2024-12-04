document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('informacion-compra');
    const submitButton = document.getElementById('proceedToPaymentBtn');

    // Función para verificar si el formulario es válido
    const verificarFormulario = () => {
        if (form.checkValidity()) {
            // Formulario válido: habilitar botón y cambiar color
            submitButton.disabled = false;
            submitButton.style.backgroundColor = ''; // Color original del botón
            submitButton.style.cursor = 'pointer'; // Cambiar el cursor a puntero
        } else {
            // Formulario inválido: deshabilitar botón y ponerlo gris
            submitButton.disabled = true;
            submitButton.style.backgroundColor = 'grey'; // Color gris
            submitButton.style.cursor = 'not-allowed'; // Cambiar el cursor a prohibido
        }
    };

    // Escuchar cambios en los campos del formulario
    form.addEventListener('input', verificarFormulario);

    // Verificar al cargar la página por si hay valores precargados
    verificarFormulario();
});





document.getElementById('proceedToPaymentBtn').addEventListener('click', async function() {
    try {
        const token = localStorage.getItem('token');
        if (!token) {
            throw new Error('No se encontró token de autenticación');
        }

        // Realizar la solicitud al backend para obtener la URL de Mercado Pago
        const response = await fetch('http://localhost:8080/api/v1/mp/pago', {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        if (!response.ok) {
            throw new Error(`Error al proceder al pago: ${response.statusText}`);
        }

        // Obtener la URL de redirección a Mercado Pago
        const data = await response.json();
        
        // Redirigir al usuario a la URL de Mercado Pago
        window.location.href = data.url;
    } catch (error) {
        console.error('Error al proceder al pago:', error);
        mostrarMensaje(error.message, 'error');
    }
});