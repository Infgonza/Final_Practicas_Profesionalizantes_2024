document.addEventListener('DOMContentLoaded', function() {
    cargarProductosCarrito();
});

async function cargarProductosCarrito() {
    try {
        const response = await fetch('/api/carrito/productos', {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const productosCarrito = await response.json();
        mostrarProductosCarrito(productosCarrito);
    } catch (error) {
        console.error('Error al cargar los productos del carrito:', error);
        mostrarMensaje('Error al cargar los productos del carrito', 'error');
    }
}

function mostrarProductosCarrito(productos) {
    const contenedorCarrito = document.getElementById('productosCarrito');
    let html = '';

    if (productos.length === 0) {
        html = '<p>Tu carrito está vacío.</p>';
    } else {
        productos.forEach(producto => {
            html += `
                <div class="card mb-3">
                    <div class="card-body">
                        <div class="d-flex justify-content-between">
                            <div class="d-flex flex-row align-items-center">
                                <div>
                                    <img src="${producto.imagenUrl}" class="img-fluid rounded-3" alt="${producto.nombre}" style="width: 65px;">
                                </div>
                                <div class="ms-3">
                                    <h5>${producto.nombre}</h5>
                                    <p class="small mb-0">${producto.artista}</p>
                                </div>
                            </div>
                            <div class="d-flex flex-row align-items-center">
                                <div style="width: 50px;">
                                    <h5 class="fw-normal mb-0">${producto.cantidad}</h5>
                                </div>
                                <div style="width: 80px;">
                                    <h5 class="mb-0">$${producto.precio}</h5>
                                </div>
                                <a href="#!" style="color: #cecece;"><i class="fas fa-trash-alt"></i></a>
                            </div>
                        </div>
                    </div>
                </div>
            `;
        });
    }

    contenedorCarrito.innerHTML = html;
}

function mostrarMensaje(mensaje, tipo) {
    alert(mensaje);  
}