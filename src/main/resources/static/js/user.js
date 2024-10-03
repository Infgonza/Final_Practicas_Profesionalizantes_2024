const forms = document.querySelector(".forms"),
pwShowHide = document.querySelectorAll(".eye-icon"),
links = document.querySelectorAll(".link");

pwShowHide.forEach(eyeIcon => {
eyeIcon.addEventListener("click", () => {
  let pwFields = eyeIcon.parentElement.parentElement.querySelectorAll(".password");
  
  pwFields.forEach(password => {
      if(password.type === "password"){
          password.type = "text";
          eyeIcon.classList.replace("bx-hide", "bx-show");
          return;
      }
      password.type = "password";
      eyeIcon.classList.replace("bx-show", "bx-hide");
  })
  
})
})      

links.forEach(link => {
link.addEventListener("click", e => {
 e.preventDefault(); 
 forms.classList.toggle("show-signup");
})
})


// Funciones de validación
document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('signupForm').addEventListener('submit', async (e) => {
        e.preventDefault();  // Previene el comportamiento por defecto del formulario

        // Obtener los valores de los campos de entrada
        const email = document.getElementById('signupEmail').value;
        const password = document.getElementById('signupPassword').value;
        const username = document.getElementById('signupUsername').value;

        console.log("Form submitted"); // Esto se ejecuta cada vez que se envía el formulario
        

        // Nombre de usuario debe tener al menos 3 caracteres
        if (username.length < 3) {
            alert('El nombre de usuario debe tener al menos 3 caracteres');
            return;  // Detener la ejecución si no pasa la validación
        }
        
        // Email debe ser válido
        if (!validateEmail(email)) {
            alert('Por favor, introduce un email válido');
            return;  
        }
        
        // Contraseña debe tener al menos 8 caracteres
        if (!validatePassword(password)) {
            alert('La contraseña debe tener al menos 8 caracteres');
            return;  
        }

        try {
            const response = await fetch('api/v1/auth/registro', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    nombreUsuario: username,
                    email: email,
                    contrasenia: password,
                }),
            });
    
            if (response.ok) {
                
                window.location.href = 'index.html'; // Redirigir al index
            } else {
                const errorData = await response.json();
                alert('Error: ' + errorData.error); // Mostrar el error del backend
            }
        } catch (error) {
            alert('Ocurrió un error: ' + error.message); // Manejo de errores
        }

        
    });
});

// Funciones de validación
function validateEmail(email) {
    const re = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
    return re.test(email);
}

function validatePassword(password) {
    return password.length >= 8;
}


    

