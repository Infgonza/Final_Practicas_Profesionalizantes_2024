document.addEventListener('DOMContentLoaded', () => {
	document.getElementById('informacion-compra').addEventListener('submit', async (e) => {
		const form = document.getElementById('informacion-compra');

		    form.addEventListener('submit', async (e) => {
		        e.preventDefault();

		        
		        const cliente = {
		            email: document.getElementById('clienteEmail').value,
		            nombre: document.getElementById('clienteNombre').value,
		            apellido: document.getElementById('clienteApellido').value,
		            dni: document.getElementById('clienteDni').value,
		            domicilio: {
		                provincia: document.getElementById('clienteProvincia').value,
		                calle: document.getElementById('clienteCalle').value,
		                numero: document.getElementById('clienteNumero').value,
		                codigoPostal: document.getElementById('clienteCodPostal').value
		            }
		        };
				
				try{
					const response = await fetch('http://localhost:8080/api/v1/clientes/registro', {
						
						method: 'POST',
						headers: { 'Content-Type': 'application/json' },
						body: JSON.stringify(cliente)
					});
				} catch (error) {
					console.error('Error al enviar datos:', error);
				}
		
		})
	})
})