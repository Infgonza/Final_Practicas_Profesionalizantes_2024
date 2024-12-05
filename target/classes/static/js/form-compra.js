document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('informacion-compra');
    const submitButton = document.getElementById('proceedToPaymentBtn');

    // Verificar si el formulario es válido
    const verificarFormulario = () => {
        if (form.checkValidity()) {
            submitButton.disabled = false;
            submitButton.style.backgroundColor = ''; // Color original
            submitButton.style.cursor = 'pointer';
        } else {
            submitButton.disabled = true;
            submitButton.style.backgroundColor = 'grey'; // Color gris
            submitButton.style.cursor = 'not-allowed';
        }
    };

    // Redirigir a Mercado Pago y gestionar los datos
    submitButton.addEventListener('click', async () => {
        if (form.checkValidity()) {
            try {
                const token = localStorage.getItem('token');
                if (!token) {
                    throw new Error('No se encontró token de autenticación');
                }

                // Solicitar URL de Mercado Pago al backend
                const response = await fetch('http://localhost:8080/api/v1/mp/pago', {
                    method: 'GET',
                    headers: {
                        'Authorization': `Bearer ${token}`
                    }
                });

                if (!response.ok) {
                    throw new Error(`Error al proceder al pago: ${response.statusText}`);
                }

                const data = await response.json();

                // Redirigir a Mercado Pago
                window.location.href = data.url;

                // (Opcional) Enviar datos del formulario al backend después de una transacción exitosa
                // Esto se debería hacer en el backend cuando el webhook de Mercado Pago confirme la transacción
            } catch (error) {
                console.error('Error al proceder al pago:', error);
                alert('Ocurrió un error al proceder al pago. Inténtalo nuevamente.');
            }
        } else {
            alert('Por favor, completa todos los campos correctamente antes de continuar.');
        }
    });

    // Escuchar cambios en el formulario para habilitar/deshabilitar el botón
    form.addEventListener('input', verificarFormulario);

    // Verificar el formulario al cargar la página
    verificarFormulario();
});
