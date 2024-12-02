$(document).ready(function() {
    const token = localStorage.getItem('token'); // Obtener el token del localStorage
    
    function obtenerRolesDesdeToken(token) {
    const payloadBase64 = token.split('.')[1];
    const decodedPayload = atob(payloadBase64); // Decodifica la parte del payload del token
    const payload = JSON.parse(decodedPayload); // Convierte el JSON en objeto

    return payload.roles; // Retorna los roles que están en el token
}

    if (token) {
		
		 const roles = obtenerRolesDesdeToken(token);
        // Usuario autenticado
        $('#profileLink').show(); // Mostrar opción de perfil
        $('#logoutLink').show(); // Mostrar opción de cerrar sesión
        $('#loginLink').hide(); // Ocultar opción de ingresar
        
        if (roles.includes('Administrador')) {
            $('#subirProducto').show();
            $('#gestionarUsuarios').show();
        }

        $('#logoutLink').on('click', function() {
            // Lógica para cerrar sesión
            localStorage.removeItem('token'); // Eliminar el token
            window.location.href = 'login.html'; // Redirigir a la página de login
        });
    } else {
        // Usuario no autenticado
        $('#profileLink').hide(); // Ocultar opción de perfil
        $('#logoutLink').hide(); // Ocultar opción de cerrar sesión
        $('#loginLink').show(); // Mostrar opción de ingresar
    }
    
    

    // Mostrar el menú al hacer clic en el ícono
    $('#userIcon').on('click', function() {
        $('#userMenu').toggle(); // Alternar la visibilidad del menú
    });

    // Ocultar el menú al hacer clic fuera de él
    $(document).on('click', function(event) {
        if (!$(event.target).closest('.user').length) {
            $('#userMenu').hide(); // Ocultar el menú
        }
    });
});
