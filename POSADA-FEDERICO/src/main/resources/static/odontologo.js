window.addEventListener('load', function () {
    const url = '/odontologos';

    fetch(url)
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error("Error en la solicitud: " + response.status);
            }
        })
        .then(data => {
            console.log(data);
            const tabla = document.getElementById("tablaOdontologos");
            data.forEach(odontologo => {
                const fila = tabla.insertRow();
                fila.innerHTML = `${odontologo.id} - ${odontologo.nombre}`;
            });
        })
        .catch(error => {
            alert("Error: " + error);
        });
});
