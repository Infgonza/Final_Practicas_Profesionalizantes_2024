function navigateToSubirProductos() {
    const token = localStorage.getItem('token');
    if (!token) {
        alert('No hay sesión activa. Por favor, inicie sesión.');
        window.location.href = 'login.html';
        return;
    }

    // Redirigir a subirproductos.html
    window.location.href = 'subirproductos.html';
}

// Asegurarse de que el DOM esté cargado antes de agregar el event listener
document.addEventListener('DOMContentLoaded', function() {
    const adminPanelLink = document.getElementById('adminPanel');
    if (adminPanelLink) {
        adminPanelLink.addEventListener('click', function(e) {
            e.preventDefault();
            navigateToSubirProductos();
        });
    }
});