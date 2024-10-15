document.addEventListener('DOMContentLoaded', function() {
    traerProductos();
});

async function traerProductos() {
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
        let productosHtml = '';

        for (let producto of productos) {
            productosHtml += `
            <div class="product-card">
                <a href="producto.html?id=${producto.idProducto}">
                    <img src="${producto.imagenUrl}" alt="${producto.nombre}" class="product-image" id="img-${producto.idProducto}" />
                </a>
                <a href="producto.html?id=${producto.idProducto}">
                    <h2 class="nombre-disco" id="nombreDisco-${producto.idProducto}" style="font-size: 20px;">${producto.nombre}</h2>
                </a>
                <p class="artista-texto">${producto.artista || 'Artista Desconocido'}</p>

                <span class="price" id="precio-${producto.idProducto}">$${producto.precio}</span>
                <button onclick="agregarAlCarrito(${producto.idProducto}, 1)" >Añadir al carrito</button>
            </div>
        `;
        }

        const productGrid = document.querySelector('.product-grid');
        if (productGrid) {
            productGrid.innerHTML = productosHtml;
        } else {
            
        }
    } catch (error) {
        console.error('Error al cargar los productos:', error);
    }
}

const token = localStorage.getItem('token');

async function agregarAlCarrito(idProducto, cantidad) {
    try {
        const response = await fetch('/api/carrito/agregar', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
                'Authorization': `Bearer ${token}`
            },
            credentials: 'include',
            body: `productoId=${idProducto}&cantidad=${cantidad}`
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        console.log(`Producto ${idProducto} añadido al carrito con cantidad ${cantidad}`);
        
    } catch (error) {
        console.error('Error al añadir producto al carrito:', error);
    }
}

