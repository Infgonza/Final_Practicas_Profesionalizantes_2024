document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('informacion-compra');
    const submitButton = document.getElementById('proceedToPaymentBtn');

    // validar y habilitar el botón cuando el formulario sea válido
    const verificarFormulario = () => {
        if (form.checkValidity()) {
            submitButton.disabled = false;
            submitButton.style.backgroundColor = ''; // color original
            submitButton.style.cursor = 'pointer';
        } else {
            submitButton.disabled = true;
            submitButton.style.backgroundColor = 'grey'; // color gris
            submitButton.style.cursor = 'not-allowed';
        }
    };

    // redirigir a Mercado Pago y gestionar los datos
    const handlePaymentProcess = async () => {
        try {
            const token = localStorage.getItem('token');
            if (!token) {
                throw new Error('No se encontró token de autenticación');
            }

            // solicitar la URL de pago a Mercado Pago al backend
            const response = await fetch('http://localhost:8080/api/v1/mp/pago', {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}`,
                },
            });

            if (!response.ok) {
                throw new Error(`Error al proceder al pago: ${response.statusText}`);
            }

            const data = await response.json();

            // redirigir a Mercado Pago
            window.location.href = data.url;
        } catch (error) {
            console.error('Error al proceder al pago:', error);
            alert('Ocurrió un error al proceder al pago. Inténtalo nuevamente.');
        }
    };

    // validar email
    const validateEmail = (emailCliente) => {
        const re = /^[a-zA-Z0-9._-]+@[a-zAZ0-9.-]+\.[a-zA-Z]{2,6}$/;
        return re.test(emailCliente);
    };

    // procesar el envío del formulario
    form.addEventListener('submit', async (event) => {
        event.preventDefault(); 

        const emailCliente = document.getElementById('clienteEmail').value;
        const nombre = document.getElementById('clienteNombre').value;
        const apellido = document.getElementById('clienteApellido').value;
        const dni = document.getElementById('clienteDni').value;
        const provincia = document.getElementById('clienteProvincia').value;
        const calle = document.getElementById('clienteCalle').value;
        const numero = document.getElementById('clienteNumero').value;
        const codPostal = document.getElementById('clienteCodPostal').value;

        if (!validateEmail(emailCliente)) {
            alert('Por favor, introduce un email válido');
            return;
        }

        try {
            const token = localStorage.getItem('token');
            if (!token) {
                throw new Error('No se encontró token de autenticación');
            }

         
            const pagoResponse = await fetch('http://localhost:8080/api/v1/mp/pago', {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}`,   
                },
            });

            if (!pagoResponse.ok) {
                throw new Error('Error al realizar el pago');
            }

            const pagoData = await pagoResponse.json();
            window.location.href = pagoData.url;  // redirigir a la URL de pago de Mercado Pago

           
            const registroResponse = await fetch('http://localhost:8080/api/v1/clientes/registro', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`,  
                },
                body: JSON.stringify({
                    emailCliente: emailCliente,
                    nombre: nombre,
                    apellido: apellido,
                    dni: dni,
                    provincia: provincia,
                    calle: calle,
                    numero: numero,
                    codPostal: codPostal,
                }),
            });

            if (!registroResponse.ok) {
                throw new Error('Error al registrar los datos del cliente');
            }

            // si el registro es exitoso, proceder con la redirección a Mercado Pago
            await handlePaymentProcess();

        } catch (error) {
            console.error('Error al procesar la solicitud:', error);
            alert('Hubo un problema al procesar la solicitud. Intenta nuevamente.');
        }
    });

    // escuchar cambios en el formulario para habilitar el boton
    form.addEventListener('input', verificarFormulario);

    // verificar el formulario al cargar la pagina
    verificarFormulario();
});
