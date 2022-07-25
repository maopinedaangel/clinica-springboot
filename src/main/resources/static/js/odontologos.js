window.addEventListener("load", () => {
       
    let divOdontologos = document.getElementById("div-odontologos");
    let listaOdontologos = []; 
    let formOdontologo = document.getElementById("form-odontologo");
    let btnGuardarOdontologo = document.getElementById("btn-guardar-odontologo");
    let btnInicio = document.getElementById("btn-inicio");

    let nombreOdontologo = document.getElementById("inp-nombre-odontologo");
    let apellidoOdontologo = document.getElementById("inp-apellido-odontologo");
    let matriculaOdontologo = document.getElementById("inp-matricula-odontologo");    

    //btnGuardarOdontologo.addEventListener("click", guardarOdontologo);
    formOdontologo.addEventListener("submit", function(event) {
        event.preventDefault();

        const payload = {
            nombre: nombreOdontologo.value,
            apellido: apellidoOdontologo.value,
            matricula: matriculaOdontologo.value,
        };
        console.log(JSON.stringify(payload));

        const settings = {
            method: 'POST',
            body: JSON.stringify(payload),
            headers: {
                "Content-Type": "application/json"
            }
        }
        
        fetch("http://localhost:8080/odontologos", settings)
        .then(response => response.json())
        .then(data => {
            console.log(data);
            cargarOdontologos();
        })
        .catch(error => {
            console.log(error);
        });

        formOdontologo.reset();
    });    

    btnInicio.addEventListener("click", () => {
        location.replace("./index.html");
    })  

    function cargarOdontologos() {
        fetch("http://localhost:8080/odontologos")
        .then(response => {
            return response.json();
        })
        .then(data => {
            console.log(data);
            listaOdontologos = data;
            /*        
            for(odontologo of listaOdontologos) {
                //divOdontologos.innerHTML += odontologo.nombre;
                //let opcionOdontologo = document.createElement("option");
                //opcionOdontologo.text = `${odontologo.nombre} ${odontologo.apellido}`;
                //opcionOdontologo.value = odontologo.id;
                //console.log("Odontólogo id en el select: " + odontologo.id);
                //selectOdontologos.appendChild(opcionOdontologo);                    
            }
            */
            mostrarTablaDeOdontologos();               
        })
        .catch(error => {
            console.log("Error en la petición");
        })
    }

    function borrarOdontologo(id) {
        const settings = {
            method: 'DELETE',
        }
        
        fetch("http://localhost:8080/odontologos/" + id, settings)
       .then(response => {
            console.log(response);
            cargarOdontologos();            
       })
        .catch(error => {
            console.log(error.message);
        });   
    }    

    function mostrarTablaDeOdontologos() {

        if (document.contains(document.getElementById("div-tabla-odontologos"))) {
            document.getElementById("div-tabla-odontologos").remove();
        }

        let divTablaOdontologos = document.createElement("div");
        divTablaOdontologos.setAttribute("id", "div-tabla-odontologos");     
        let tablaOdontologos = document.createElement("table");
        let templateTablaOdontologos = `<tr>
            <th>Nombre</th>
            <th>Apellido</th>
            <th>Matrícula</th>
            <th></th>
        </tr>`;
        
        for(odontologo of listaOdontologos) {        
            templateTablaOdontologos += `<tr>
                <td>${odontologo.nombre}</td>
                <td>${odontologo.apellido}</td>
                <td>${odontologo.matricula}</td>
                <td><i class="fas fa-trash-alt" id="btn-borrar-odontologo-${odontologo.id}"></i></td>                
            </tr>`;
        }
        tablaOdontologos.innerHTML += templateTablaOdontologos;
        let tituloOdontologos = document.createElement("h3");
        tituloOdontologos.innerText = "Listado de Odontólogos";
        divTablaOdontologos.appendChild(tituloOdontologos);                      
        divTablaOdontologos.appendChild(tablaOdontologos);
        divOdontologos.appendChild(divTablaOdontologos);
        
        let btnBorrarOdontologos = [];
        for (let k=0; k<listaOdontologos.length; k++) {
            btnBorrarOdontologos[k] = document.getElementById(`btn-borrar-odontologo-${listaOdontologos[k].id}`);
            btnBorrarOdontologos[k].addEventListener("click", () => {
                borrarOdontologo(listaOdontologos[k].id);
                console.log("Se borrará el odontólogo con id " + listaOdontologos[k].id);
            })
        }        
    }


    cargarOdontologos();
})
