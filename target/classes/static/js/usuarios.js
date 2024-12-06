function displayUsers(users) {
    
    const tableBody = document.getElementById('usuariosTableBody');
    tableBody.innerHTML = '';

    users.forEach(user => {
       
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${user.id}</td>
            <td>${user.nombreUsuario}</td>
            <td>${user.email}</td>
            <td>
            
             <select class="seleccionar-rol" onchange="cambiarRol(${user.id}, this)">
             <option value="Administrador" ${user.rol === 'Administrador' ? 'selected' : ''}>Administrador</option>
             <option value="Usuario" ${user.rol === 'Usuario' ? 'selected' : ''}>Usuario</option>
			 <option value="Empleado" ${user.rol === 'Empleado' ? 'selected' : ''}>Empleado</option>
			 
            </select>
            
            </td>
            <td>
                <button class="btn btn-danger btn-sm" onclick="deleteUser(${user.id})">
                    Eliminar
                </button>
            </td>
        `;
        tableBody.appendChild(row);
    });
}

async function loadUsers() {
    try {
        const token = localStorage.getItem('token');
        const response = await fetch('api/v1/usuarios/listar', {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error('Error al cargar usuarios');
        }

        const users = await response.json();
        
        displayUsers(users);
    } catch (error) {
        console.error('Error:', error);
        showError('Error al cargar los usuarios');
    }
}

// Eliminar usuario
async function deleteUser(userId) {
    
    
    if (!confirm('¿Está seguro que desea eliminar este usuario?')) {
        return;
    }

    try {
        const token = localStorage.getItem('token');
        const response = await fetch(`api/v1/usuarios/${userId}`, {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.error || 'Error al eliminar usuario');
        }

        // Recargar la lista de usuarios
        loadUsers();
        showSuccess('Usuario eliminado exitosamente');
    } catch (error) {
        console.error('Error:', error);
        showError(error.message || 'Error al eliminar el usuario');
    }
}

async function cambiarRol(userId, selectElement) {
    const nuevoRol = selectElement.value;
    const token = localStorage.getItem('token');
    
    console.log('Token:', token);  
    console.log('User ID:', userId);
    console.log('Nuevo Rol:', nuevoRol);

    try {
        const response = await fetch(`api/v1/usuarios/cambiarRol/${userId}`, {
            method: 'PUT',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ rol: nuevoRol })
        });


        if (!response.ok) {
            
            const errorText = await response.text();
            console.error('Error response:', errorText);
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        loadUsers();
        showSuccess('Rol cambiado exitosamente');
    } catch (error) {
        console.error('Error completo:', error);
        showError(error.message || 'Error al cambiar rol');
    }
}








// Mostrar mensajes de error
function showError(message) {
    
    alert(message);
}

// Mostrar mensajes de éxito
function showSuccess(message) {
    
    alert(message);
}

// Inicializar la página
document.addEventListener('DOMContentLoaded', () => {
    
    loadUsers();
});