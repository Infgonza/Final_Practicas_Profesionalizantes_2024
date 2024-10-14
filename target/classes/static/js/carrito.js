/*import { getToken, isAuthenticated } from './auth.js';

async function cargarCarrito() {
    if (!isAuthenticated()) {
        alert('Por favor, inicia sesión para ver tu carrito.');
        return;
    }

    try {
        const response = await fetch('api/v1/carrito/', {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${getToken()}`
            },
        });

        if (!response.ok) {
            throw new Error('Error al cargar el carrito');
        }

        const carrito = await response.json();
        mostrarCarrito(carrito);
    } catch (error) {
        console.error('Error:', error);
        alert('Error al cargar el carrito');
    }
}

function mostrarCarrito(carrito) {
    const productosCarritoDiv = document.getElementById('productosCarrito');
    let html = '';

    carrito.items.forEach(item => {
        html += `
        <div class="card mb-3">
            <div class="card-body">
                <div class="d-flex justify-content-between">
                    <div class="d-flex flex-row align-items-center">
                        <div>
                            <img src="${item.producto.imagenUrl}" class="img-fluid rounded-3" alt="${item.producto.nombre}" style="width: 65px;">
                        </div>
                        <div class="ms-3">
                            <h5>${item.producto.nombre}</h5>
                        </div>
                    </div>
                    <div class="d-flex flex-row align-items-center">
                        <div style="width: 50px;">
                            <h5 class="fw-normal mb-0">${item.cantidad}</h5>
                        </div>
                        <div style="width: 80px;">
                            <h5 class="mb-0">$${item.producto.precio}</h5>
                        </div>
                        <div style="width: 80px;">
                            <h5 class="mb-0">$${item.producto.precio * item.cantidad}</h5>
                        </div>
                        <a href="#!" style="color: #cecece;" onclick="eliminarProducto(${item.producto.id})"><i class="fas fa-trash-alt"></i></a>
                    </div>
                </div>
            </div>
        </div>
        `;
    });

    productosCarritoDiv.innerHTML = html;
    actualizarTotalCarrito(carrito);
}

function actualizarTotalCarrito(carrito) {
    const total = carrito.items.reduce((acc, item) => acc + (item.producto.precio * item.cantidad), 0);
    document.getElementById('totalCarrito').textContent = `$${total.toFixed(2)}`;
}

async function eliminarProducto(productoId) {
    try {
        const response = await fetch(`api/v1/carrito/eliminar/${productoId}`, {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer ${getToken()}`
            },
        });
*/