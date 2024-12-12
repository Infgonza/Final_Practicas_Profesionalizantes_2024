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
	let totalCarrito = 0;
	let productosCarrito = [];
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

        productosCarrito = await response.json();

		
		productosCarrito.forEach(producto => {
		    totalCarrito += producto.cantidad * producto.precio; 
		});
		
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
	
	// GUARDAR DATOS EN COMPRA
	try {
	    const response = await fetch('http://localhost:8080/api/v1/compras/guardar', {
	        method: 'POST',
	        headers: {
	            'Content-Type': 'application/json',
	        },
			body: JSON.stringify({
			                total: totalCarrito,
			                // productos: productosCarrito <--- para detalle compra 
			            }),
	    });

		if (!response.ok) {
		    const errorDetails = await response.text(); // Obtén el detalle del error
		    throw new Error(`Error al guardar la compra: ${errorDetails}`);
		}

	    const compra = await response.json();
	    console.log('Compra guardada exitosamente:', compra);
	    alert('Compra guardada exitosamente.');
	} catch (error) {
	    console.error('Error al guardar la compra:', error);
	    //alert('Hubo un problema al guardar la compra. Intenta nuevamente.');
	}
}




document.addEventListener('DOMContentLoaded', () => {
    displayPurchasedProducts();
    finalizarCompra();
});