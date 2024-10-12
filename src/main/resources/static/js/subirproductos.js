document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('subir-producto');
    form.addEventListener('submit', function(e) {
        e.preventDefault();
        subirProductos();
    });
});

async function subirProductos() {
    const form = document.getElementById('subir-producto');
    const formData = new FormData(form);
    
    const token = localStorage.getItem('token');
    if (!token) {
        mostrarMensaje('No hay sesión activa. Por favor, inicie sesión.', 'error');
        window.location.href = 'login.html';
        return;
    }

    try {
        const response = await fetch('api/v1/productos/crearProducto', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
                
            },
            body: formData
        });

        if (!response.ok) {
            const errorBody = await response.text();
            console.error('Estado de la respuesta:', response.status);
            console.error('Cuerpo de la respuesta:', errorBody);
            throw new Error(`Error HTTP! estado: ${response.status}`);
        }

        const result = await response.json();
        mostrarMensaje('Producto subido exitosamente', 'success');
        form.reset();
        
        window.location.href = 'index.html';
    } catch (error) {
        console.error('Error:', error);
        mostrarMensaje('Error al subir el producto: ' + error.message, 'error');
    }
}

function mostrarMensaje(mensaje, tipo) {
    alert(tipo + ': ' + mensaje);
}