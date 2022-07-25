window.addEventListener("load", () => {

      
    let divPacientes = document.getElementById("div-pacientes");
    let listaPacientes = [];
    let formPaciente = document.getElementById("form-paciente");
    let seccionNuevoPaciente = document.getElementById("div-nuevo-paciente");
    let btnGuardarPaciente = document.getElementById("btn-guardar-paciente");
    let btnInicio = document.getElementById("btn-inicio");

    let nombrePaciente = document.getElementById("inp-nombre-paciente");
    let apellidoPaciente = document.getElementById("inp-apellido-paciente");
    let dniPaciente = document.getElementById("inp-dni-paciente");
    let emailPaciente = document.getElementById("inp-email-paciente");
    let fechaIngresoPaciente = document.getElementById("inp-fecha-ingreso-paciente");

    let calle = document.getElementById("inp-calle");
    let numero = document.getElementById("inp-numero");
    let localidad = document.getElementById("inp-localidad");
    let provincia = document.getElementById("inp-provincia");
       
    //btnGuardarPaciente.addEventListener("click", guardarPaciente);
    formPaciente.addEventListener("submit", function(event) {
        event.preventDefault();

        const payload = {
            nombre: nombrePaciente.value,
            apellido: apellidoPaciente.value,
            dni: dniPaciente.value,
            email: emailPaciente.value,
            fechaDeIngreso: fechaIngresoPaciente.value,
            domicilio: {
                calle: calle.value,
                numero: numero.value,
                localidad: localidad.value,
                provincia: provincia.value
            }
        };
        console.log(JSON.stringify(payload));

        const settings = {
            method: 'POST',
            body: JSON.stringify(payload),
            headers: {
                "Content-Type": "application/json"
            }
        }
        
        fetch("http://localhost:8080/pacientes", settings)
        .then(response => response.json())
        .then(data => {
            console.log(data);
            cargarPacientes();
        })
        .catch(error => {
            console.log(error);
        });

        formPaciente.reset();
    });

    btnInicio.addEventListener("click", () => {
        location.replace("./index.html");
    })    

    function cargarPacientes() {
        fetch("http://localhost:8080/pacientes")
        .then(response => {
            return response.json();
        })
        .then(data => {
            console.log(data);
            listaPacientes = data;
            /*
            for(paciente of listaPacientes) {
                let opcionPaciente = document.createElement("option");
                opcionPaciente.text = `${paciente.nombre} ${paciente.apellido}`;
                opcionPaciente.value = paciente.id;
                console.log("Paciente id en el select: " + paciente.id);
                //selectPacientes.appendChild(opcionPaciente);           
            }
            */
            mostrarTablaDePacientes();       
        })
        .catch(error => {
            console.log("Error en la petición: " + error);
        })
        return listaPacientes;
    }

    function borrarPaciente(id) {
        const settings = {
            method: 'DELETE',
        }
        
        fetch("http://localhost:8080/pacientes/" + id, settings)
       .then(response => {
            console.log(response);
            cargarPacientes();            
       })
        .catch(error => {
            console.log(error.message);
        });   
    }


    function mostrarTablaDePacientes() {

        if (document.contains(document.getElementById("div-tabla-pacientes"))) {
            document.getElementById("div-tabla-pacientes").remove();
        }        

        let divTablaPacientes = document.createElement("div");
        divTablaPacientes.setAttribute("id", "div-tabla-pacientes");     
        let tablaPacientes = document.createElement("table");
        let templateTablaPacientes = `<tr>
            <th>Nombre</th>
            <th>Apellido</th>
            <th>Email</th>
            <th>DNI</th>
            <ht></th>
        </tr>`;
        
        //<td><button id="btn-borrar-paciente-${paciente.id}"><i class="fas fa-trash-alt"></i></button></td>        
        for(paciente of listaPacientes) {        
            templateTablaPacientes += `<tr>
                <td>${paciente.nombre}</td>
                <td>${paciente.apellido}</td>
                <td>${paciente.email}</td>
                <td>${paciente.dni}</td>
                <td><i class="fas fa-trash-alt" id="btn-borrar-paciente-${paciente.id}"></i></td>
            </tr>`;
        }
        tablaPacientes.innerHTML += templateTablaPacientes;
        let tituloPacientes = document.createElement("h3");
        tituloPacientes.innerText = "Listado de Pacientes"; 
        divTablaPacientes.appendChild(tituloPacientes);                            
        divTablaPacientes.appendChild(tablaPacientes);
        divPacientes.appendChild(divTablaPacientes); 

        let btnBorrarPacientes = [];
        for (let k=0; k<listaPacientes.length; k++) {
            btnBorrarPacientes[k] = document.getElementById(`btn-borrar-paciente-${listaPacientes[k].id}`);
            btnBorrarPacientes[k].addEventListener("click", () => {
                borrarPaciente(listaPacientes[k].id);
                console.log("Se borrará el paciente con id " + listaPacientes[k].id);
            })
        }
    }
    
    cargarPacientes();


})
