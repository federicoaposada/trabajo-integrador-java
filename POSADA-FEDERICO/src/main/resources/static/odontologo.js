window.addEventListener('load', function () {

  const url = '/odontologos';

         const settings = {
             method: 'GET'
         }

         fetch(url,settings)
         .then(response => response.json())
         .then(data => {
                          console.log(data)
                    for(odontologo of data){
                    var tabla = document.getElementById("tablaOdontologos");
                    var fila = tabla.insertRow();
                    fila.innerHTML =`${odontologo.id} - ${odontologo.nombre} `
                    }
//             let pelicula = data;
//             document.querySelector('#pelicula_id').value = pelicula.id;
//
//             document.querySelector('#titulo').value = pelicula.titulo;
//
//             document.querySelector('#categoria').value = pelicula.categoria;
//
//             document.querySelector('#premios').value = pelicula.premios;
//
//             //el formulario por default está oculto y al editar se habilita
//
//             document.querySelector('#div_pelicula_updating').style.display = "block";

         }).catch(error => {
             alert("Error: " + error);
         })
})