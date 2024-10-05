$(document).ready(function() {
    iniciarSesion();
});

async function iniciarSesion() {
	
	let datos={};
	datos.email=document.getElementById('txtEmail').value;
	datos.password=document.getElementById('txtPassword').value;
	
	
  const rawResponse = await fetch('http://localhost:8080/api/v1/auth/login', {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    
    body: JSON.stringify(datos)
  });
  
  const respuesta = await request.json();
  
  


}	
