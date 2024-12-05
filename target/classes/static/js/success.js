async function displayPurchasedProducts() {
          try {
              // Fetch the cart products
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

              // Crear lista de productos
              const productList = document.createElement('div');
              productList.classList.add('product-list');

              productos.forEach(producto => {
                  const productDiv = document.createElement('div');
                  productDiv.classList.add('producto-item');
                  
                  // Create product image
                  const img = document.createElement('img');
                  img.src = producto.imagenUrl  
                  img.alt = producto.nombre;
                  img.classList.add('producto-imagen');

                  // Create product details
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

      // Call the function when the page loads
      document.addEventListener('DOMContentLoaded', displayPurchasedProducts);