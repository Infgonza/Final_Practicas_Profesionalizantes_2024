document.addEventListener('DOMContentLoaded', function() {
        const editarBtn = document.getElementById('editar-btn');
        const guardarBtn = document.getElementById('guardar-btn');
        const campos = ['nombre', 'telefono', 'correo'];

        // Cargar datos del usuario al iniciar
        cargarDatosUsuario();

        editarBtn.addEventListener('click', function() {
            campos.forEach(campo => {
                const display = document.getElementById(`${campo}-display`);
                const input = document.getElementById(`${campo}-input`);
                display.style.display = 'none';
                input.style.display = 'inline-block';
                input.value = display.textContent;
            });
            editarBtn.style.display = 'none';
            guardarBtn.style.display = 'inline-block';
        });

       guardarBtn.addEventListener('click', function() {
    const datosActualizados = {
        nombreUsuario: document.getElementById('nombre-input').value,
        telefono: document.getElementById('telefono-input').value,
        email: document.getElementById('correo-input').value
    };

    // Enviar datos actualizados al servidor
    fetch('/api/v1/usuarios/actualizar', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('token') 
        },
        body: JSON.stringify(datosActualizados)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Error al actualizar los datos');
        }
        return response.json();
    })
    .then(data => {
        const { usuario, token } = data;

        // Actualizar el token en localStorage
        localStorage.setItem('token', token);

        // Actualizar vista después de actualización exitosa
        campos.forEach(campo => {
            const display = document.getElementById(`${campo}-display`);
            const input = document.getElementById(`${campo}-input`);
            display.textContent = input.value;
            display.style.display = 'inline-block';
            input.style.display = 'none';
        });

        editarBtn.style.display = 'inline-block';
        guardarBtn.style.display = 'none';

        alert('Datos actualizados correctamente');
    })
    .catch(error => {
        console.error('Error:', error);
        alert('No se pudieron actualizar los datos');
    });
});

        
        

 function cargarDatosUsuario() {
 const token = localStorage.getItem('token');
 console.log('Token being used:', token);

    fetch('/api/v1/usuarios/perfil', {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Accept': 'application/json',
            'Content-Type': 'application/json', 
        }
    })
    .then(response => {
        console.log('Response status:', response.status);
        if (!response.ok) {
            // Try to get more error details
            return response.text().then(text => {
                throw new Error(text || 'No se pudieron cargar los datos del usuario');
            });
        }
        return response.json();
    })
    .then(usuario => {
        document.getElementById('nombre-display').textContent = usuario.nombreUsuario;
        document.getElementById('telefono-display').textContent = usuario.telefono;
        document.getElementById('correo-display').textContent = usuario.email;
    })
    .catch(error => {
    console.error('Full error:', error);
    if (error.message.includes('401')) {
        alert('Tu sesión ha expirado. Por favor, inicia sesión nuevamente.');
        window.location.href = '/login.html';
    } else {
        alert('No se pudieron cargar los datos del usuario: ' + error.message);
    }
});

}
    });