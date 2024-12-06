function mostrarProductos(productos) {
    const tablaProductos = document.getElementById('tablaProductos');
    tablaProductos.innerHTML = '';

    productos.forEach(producto => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${producto.idProducto || 'N/A'}</td>
            <td>${producto.nombre || 'Sin nombre'}</td>
            <td>${producto.precio !== undefined ? producto.precio.toFixed(2) : 'N/A'}</td>
            <td>${producto.stock !== undefined ? producto.stock : 'N/A'}</td>
            <td>${producto.tipo || 'Sin tipo'}</td>
            <td>${producto.artista || 'Sin artista'}</td>
            <td>${producto.genero || 'Sin género'}</td>
            <td>${producto.fechaLanzamiento || 'Sin fecha'}</td>
            
			 
            <td>
                <button class="btn btn-danger btn-sm" onclick="deleteProducto(${producto.idProducto})">
                    Eliminar
                </button>
            </td>
        `;
        tablaProductos.appendChild(row);
    });
}

async function cargarProductos() {
    try {
        const token = localStorage.getItem('token');
        const response = await fetch('/api/v1/productos/listarProductos', {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error('Error al cargar productos');
        }

        const productos = await response.json();
        
        mostrarProductos(productos);
    } catch (error) {
        console.error('Error:', error);
        showError('Error al cargar productos ');
    }
}

async function deleteProducto(productoId) {
    
    
    if (!confirm('¿Está seguro que desea eliminar este producto?')) {
        return;
    }

    try {
        const token = localStorage.getItem('token');
        const response = await fetch(`api/v1/productos/eliminar/${productoId}`, {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.error || 'Error al eliminar producto');
        }

        // Recargar la lista de productos
        cargarProductos();
        showSuccess('Producto eliminado exitosamente');
    } catch (error) {
        console.error('Error:', error);
        showError(error.message || 'Error al eliminar el producto');
    }
}


function showError(message) {
    
    alert(message);
}

// Mostrar mensajes de éxito
function showSuccess(message) {
    
    alert(message);
}

// Inicializar la página
document.addEventListener('DOMContentLoaded', () => {
    
    cargarProductos();
});