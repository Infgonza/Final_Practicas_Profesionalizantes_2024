function navigateToSubirProductos() {
    const token = localStorage.getItem('token');
    if (!token) {
        alert('No hay sesión activa. Por favor, inicie sesión.');
        window.location.href = 'login.html';
        return;
    }

    window.location.href = 'subirproductos.html';
}
function navigateToUsuarios() {
    const token = localStorage.getItem('token');
    if (!token) {
        alert('No hay sesión activa. Por favor, inicie sesión.');
        window.location.href = 'login.html';
        return;
    }

  
    window.location.href = 'usuarios.html';
}

function navigateToProductos() {
    const token = localStorage.getItem('token');
    if (!token) {
        alert('No hay sesión activa. Por favor, inicie sesión.');
        window.location.href = 'login.html';
        return;
    }

  
    window.location.href = 'tabla-productos.html';
}

// Asegurarse de que el DOM esté cargado antes de agregar el event listener
document.addEventListener('DOMContentLoaded', function() {
    const subirProducto = document.getElementById('subirProducto');
    const gestionarUsuarios = document.getElementById('gestionarUsuarios');
	const gestionarProductos = document.getElementById('gestionarProductos');
	
    if (subirProducto) {
        subirProducto.addEventListener('click', function(e) {
            e.preventDefault();
            navigateToSubirProductos();
            
        });
    }
    if (gestionarUsuarios) {
        gestionarUsuarios.addEventListener('click', function(e) {
            e.preventDefault();
            navigateToUsuarios();
            
        });
    }
	if (gestionarProductos) {
	        gestionarProductos.addEventListener('click', function(e) {
	            e.preventDefault();
	            navigateToProductos();
	            
	        });
	    }

});