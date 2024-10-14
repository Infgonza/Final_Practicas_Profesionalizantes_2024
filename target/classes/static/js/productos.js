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
                    <h2 id="nombreDisco-${producto.idProducto}">${producto.nombre}</h2>
                </a>
                <p>${producto.artista || 'Artista Desconocido'}</p>
                <span class="price" id="precio-${producto.idProducto}">$${producto.precio}</span>
                <button onclick="agregarAlCarrito(${producto.idProducto}, 1)">Añadir al carrito</button>
            </div>
        `;
        }

        document.querySelector('.product-grid').innerHTML = productosHtml;
    } catch (error) {
        console.error('Error al cargar los productos:', error);
    }
}

function agregarAlCarrito(idProducto, cantidad) {
    // Obtener el carrito del localStorage o crear uno vacío
    let carrito = JSON.parse(localStorage.getItem('carrito')) || [];

    // Verificar si el producto ya está en el carrito
    const index = carrito.findIndex(p => p.idProducto === idProducto);
    if (index > -1) {
        // Si el producto ya está, actualiza la cantidad
        carrito[index].cantidad += parseInt(cantidad);
    } else {
        // Buscar el producto en la página actual (puedes obtener la info desde los elementos HTML)
        const producto = { 
            idProducto, 
            nombre: document.getElementById(`nombreDisco-${idProducto}`).innerText, 
            precio: document.getElementById(`precio-${idProducto}`).innerText,
            imagenUrl: document.getElementById(`img-${idProducto}`).src,
            cantidad: parseInt(cantidad)
        };
        // Añadir el producto al carrito
        carrito.push(producto);
    }

    // Guardar el carrito actualizado en el localStorage
    localStorage.setItem('carrito', JSON.stringify(carrito));

    console.log(`Producto ${idProducto} añadido al carrito con cantidad ${cantidad}`);
}
