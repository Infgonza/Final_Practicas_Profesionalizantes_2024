document.addEventListener('DOMContentLoaded', function() {
    detallesProducto();
});

const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);
const idProducto = urlParams.get('id');

async function detallesProducto() {
    try {
        const response = await fetch('api/v1/productos', {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const productos = await response.json();
        
        // Encuentra el producto específico basado en el ID de la URL
        const productoEspecifico = productos.find(producto => producto.idProducto == idProducto);
        

        if (productoEspecifico) {
            // Actualiza los elementos HTML con la información del producto
            document.getElementById('featured-image').src = productoEspecifico.imagenUrl;
            document.getElementById('artista').textContent = productoEspecifico.artista;
            document.getElementById('genero').textContent = productoEspecifico.genero;
            document.getElementById('fecha').textContent = productoEspecifico.fechaLanzamiento;
            document.getElementById('descripcion').textContent = productoEspecifico.descripcion;
            document.getElementById('nombre-producto').textContent = productoEspecifico.nombre;
            document.getElementById('precio-producto').textContent = `$${productoEspecifico.precio.toFixed(2)}`;
            
        } else {
            console.error('Producto no encontrado');
        }
    } catch (error) {
        console.error('Error al cargar el producto:', error);
    }
}