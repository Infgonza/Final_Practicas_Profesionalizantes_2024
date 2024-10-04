document.addEventListener('DOMContentLoaded', function() {
    const editarBtn = document.getElementById('editar-btn');
    const guardarBtn = document.getElementById('guardar-btn');
    const campos = ['nombre', 'telefono', 'correo'];

    editarBtn.addEventListener('click', function() {
        campos.forEach(campo => {
            const display = document.getElementById(`${campo}-display`);
            const input = document.getElementById(`${campo}-input`);
            display.style.display = 'none';
            input.style.display = 'inline-block';
            input.value = display.textContent;
        });
        editarBtn.style.display = 'none';
        guardarBtn.style.display = 'inline-block';
    });

    guardarBtn.addEventListener('click', function() {
        campos.forEach(campo => {
            const display = document.getElementById(`${campo}-display`);
            const input = document.getElementById(`${campo}-input`);
            display.textContent = input.value;
            display.style.display = 'inline-block';
            input.style.display = 'none';
        });
        editarBtn.style.display = 'inline-block';
        guardarBtn.style.display = 'none';

        // Aquí podrías agregar código para enviar los datos actualizados al servidor
        console.log('Datos actualizados:', {
            nombre: document.getElementById('nombre-input').value,
            telefono: document.getElementById('telefono-input').value,
            correo: document.getElementById('correo-input').value
        });
    });
});