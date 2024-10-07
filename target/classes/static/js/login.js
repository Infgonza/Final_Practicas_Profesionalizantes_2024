  $(document).ready(function() {
    $('#loginForm').on('submit', function(e) {
        e.preventDefault();
        iniciarSesion();
    });
});

async function iniciarSesion() {
    const nombreUsuario = $('#loginUser').val();
    const contrasenia = $('#loginPassword').val();

    if (!nombreUsuario || !contrasenia) {
        mostrarMensaje('Por favor, complete todos los campos', 'error');
        return;
    }

    const datos = {
        nombreUsuario: nombreUsuario,
        contrasenia: contrasenia
    };
    console.log(datos);

    try {
        const response = await fetch('http://localhost:8080/api/v1/auth/login', {
            method: 'POST', 
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(datos)
        });

        if (!response.ok) {
            throw new Error('Error en la autenticación');
        }

        const respuesta = await response.json();

        if (respuesta.token) {
            // Guardar el token en localStorage
            localStorage.setItem('token', respuesta.token);
            mostrarMensaje('Inicio de sesión exitoso', 'succes');
            // Redirigir a la página principal o dashboard
            setTimeout(() => {
                window.location.href = 'index.html';
            }, 1500);
        } else {
            throw new Error('No se recibió un token válido');
        }
    } catch (error) {
        console.error('Error:', error);
        mostrarMensaje('Error en el inicio de sesión: ' + error.message, 'error');
    }
}

function mostrarMensaje(mensaje, tipo) {
    
    alert(tipo.toUpperCase() + ': ' + mensaje);
}