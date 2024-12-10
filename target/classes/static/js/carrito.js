document.addEventListener('DOMContentLoaded', function() {
    const token = localStorage.getItem('token');
    if (!token) {
        console.log('No se encontró token de autenticación');
        mostrarMensajeNoAutenticado();
    } else {
        cargarProductosCarrito();
    }
	
	
});


function mostrarMensajeNoAutenticado() {
    const contenedorCarrito = document.getElementById('productosCarrito');
    contenedorCarrito.innerHTML = `
        <div class="alert alert-warning" role="alert">
            No has iniciado sesión. Por favor, <a href="login.html" class="alert-link">inicia sesión</a> para ver tu carrito.
        </div>
    `;
}

async function cargarProductosCarrito() {
    try {
        const token = localStorage.getItem('token');
        if (!token) {
            throw new Error('No se encontró token de autenticación');
        }

        const response = await fetch('http://localhost:8080/api/carrito/productos', {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            credentials: 'include'
        });

        if (!response.ok) {
            if (response.status === 401) {
                localStorage.removeItem('token'); 
                throw new Error('Sesión expirada. Por favor, inicia sesión nuevamente.');
            }
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const productosCarrito = await response.json();
        mostrarProductosCarrito(productosCarrito);
    } catch (error) {
        console.error('Error al cargar los productos del carrito:', error);
        mostrarMensaje(error.message, 'error');
    }
}


async function eliminarProducto(productoId) {
    try {
        const token = localStorage.getItem('token');
        if (!token) {
            throw new Error('No se encontró token de autenticación');
        }

        const response = await fetch(`http://localhost:8080/api/carrito/productos/${productoId}`, {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer ${token}`
            },
            credentials: 'include'
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        // Recargar los productos del carrito
        await cargarProductosCarrito();
        mostrarMensaje('Producto eliminado exitosamente', 'success');
    } catch (error) {
        console.error('Error al eliminar el producto:', error);
        mostrarMensaje(error.message, 'error');
    }
}

function mostrarProductosCarrito(productos) {
    const contenedorCarrito = document.getElementById('productosCarrito');
    let html = '';
    let total = 0;

    if (productos.length === 0) {
        html = '<p>Tu carrito está vacío.</p>';
    } else {
        productos.forEach(producto => {
            const subtotal = producto.precio * producto.cantidad;
            total += subtotal;
            html += `
                <div class="card mb-3">
                    <div class="card-body">
                        <div class="d-flex justify-content-between">
                            <div class="d-flex flex-row align-items-center">
                                <div>
                                    <img src="${producto.imagenUrl}" class="img-fluid rounded-3" alt="${producto.nombre}" style="width: 50px;">
                                </div>
                                <div class="ms-3">
                                    <h5 class="mb-0">${producto.nombre}</h5>
                                </div>
                            </div>
                            <div class="d-flex flex-row align-items-center">
                                <div style="width: 50px;">
                                    <h5 class="fw-normal mb-0">x${producto.cantidad}</h5>
                                </div>
                                
                                <div style="width: 80px;">
                                    <h5 class="mb-0">$${subtotal.toFixed(0)}</h5>
                                </div>
                                <a href="#!" onclick="eliminarProducto(${producto.id})" style="color: #cecece;"><i class="fas fa-trash-alt"></i></a>
                            </div>
                        </div>
                    </div>
                </div>
            `;
        });
    }

    contenedorCarrito.innerHTML = html;
    actualizarTotal(total);
}

function actualizarTotal(total) {
    const totalElement = document.getElementById('totalCarrito');
    if (totalElement) {
        totalElement.textContent = `Total a pagar: $${total.toFixed(2)}`;
    }
}

function mostrarMensaje(mensaje, tipo) {
    const contenedorMensaje = document.getElementById('mensajeCarrito');
    contenedorMensaje.innerHTML = `
        <div class="alert alert-${tipo === 'error' ? 'danger' : 'success'}" role="alert">
            ${mensaje}
        </div>
    `;
    setTimeout(() => {
        contenedorMensaje.innerHTML = '';
    }, 3000);
}



