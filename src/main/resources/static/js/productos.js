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
                    <img src="${producto.imagenUrl}" alt="${producto.nombre}" class="product-image" id="img-${producto.idProducto}" />
                    <h2 id="nombreDisco-${producto.idProducto}">${producto.nombre}</h2>
                    <p>${producto.artista || 'Artista Desconocido'}</p>
                    <span class="price" id="precio-${producto.idProducto}">$${producto.precio}</span>
                    <button onclick="agregarAlCarrito(${producto.idProducto})">Añadir al carrito</button>
                </div>
            `;
        }

        document.querySelector('.product-grid').innerHTML = productosHtml;
    } catch (error) {
        console.error('Error al cargar los productos:', error);
    }
}
