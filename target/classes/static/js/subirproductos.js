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
    
    try {
        const response = await fetch('api/v1/productos/crearProducto', {
            method: 'POST',
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
    } catch (error) {
        console.error('Error:', error);
        mostrarMensaje('Error al subir el producto: ' + error.message, 'error');
    }
}

function mostrarMensaje(mensaje, tipo) {
    alert(tipo + ': ' + mensaje);
}