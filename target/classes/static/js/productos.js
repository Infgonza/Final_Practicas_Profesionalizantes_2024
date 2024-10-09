
document.addEventListener('DOMContentLoaded', function() {
    traerProductos();
	agregarAlCarrito();

});

const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);
const idProducto = urlParams.get('id');


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
                <h2 id="nombreDisco-${producto.idProducto}">${producto.nombre}</h2>
            </a>
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


function agregarAlCarrito(idProducto) {

    console.log(`Producto ${idProducto} añadido al carrito`);
}
