async function displayPurchasedProducts() {
          try {
             
              const response = await fetch('/api/carrito/productos', {
                  method: 'GET',
                  headers: {
                      'Content-Type': 'application/json'
                  }
              });

              if (!response.ok) {
                  throw new Error('No se pudieron obtener los productos');
              }

              const productos = await response.json();
              const contentProducto = document.querySelector('.content-producto');
              
              
              contentProducto.innerHTML = '';

              const productList = document.createElement('div');
              productList.classList.add('product-list');

              productos.forEach(producto => {
                  const productDiv = document.createElement('div');
                  productDiv.classList.add('producto-item');
                  
                  const img = document.createElement('img');
                  img.src = producto.imagenUrl  
                  img.alt = producto.nombre;
                  img.classList.add('producto-imagen');

                  const detalles = document.createElement('div');
                  detalles.classList.add('producto-detalles');
                  detalles.innerHTML = `
                      <h3>${producto.nombre}</h3>
                      <p>Cantidad: ${producto.cantidad}</p>
               
                  `;

                  productDiv.appendChild(img);
                  productDiv.appendChild(detalles);
                  productList.appendChild(productDiv);
              });

           

			  const botonTienda = document.createElement('a');
			               botonTienda.href = '/productos ';
			               botonTienda.textContent = 'IR A LA TIENDA';
			               botonTienda.classList.add('boton-tienda2');

			               contentProducto.appendChild(productList);
			               contentProducto.appendChild(botonTienda);
 

          } catch (error) {
              console.error('Error al obtener productos:', error);
              const contentProducto = document.querySelector('.content-producto');
              contentProducto.innerHTML = `
                  <p>No se pudieron cargar los productos comprados.</p>
                  <a href="productos.html" class="boton-tienda">IR A LA TIENDA</a>
              `;
          }
}

async function finalizarCompra() {
    try {
        
        const response = await fetch('/api/carrito/productos', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        if (!response.ok) {
            throw new Error('No se pudieron obtener los productos del carrito');
        }

        const productosCarrito = await response.json();

        const productosParaDescontar = productosCarrito.map(producto => ({
            idProducto: producto.id, 
            stock: producto.cantidad,
        }));

        const descontarResponse = await fetch('api/v1/productos/descontarStock', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(productosParaDescontar),
        });

        if (!descontarResponse.ok) {
            throw new Error('No se pudo descontar el stock');
        }

        console.log('Stock descontado correctamente');
    } catch (error) {
        console.error('Error al finalizar la compra:', error);
    }
}




document.addEventListener('DOMContentLoaded', () => {
    displayPurchasedProducts();
    finalizarCompra();
});