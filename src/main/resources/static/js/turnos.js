window.addEventListener("load", () => {

     
    let divTurnos = document.getElementById("div-turnos");
    let listaPacientes = [];
    let listaOdontologos = []; 
    let listaTurnos = [];
    let formTurno = document.getElementById("form-turno");
    let selectPacientes = document.getElementById("sel-paciente");
    let selectOdontologos = document.getElementById("sel-odontologo");
    let btnGuardarTurno = document.getElementById("btn-guardar-turno");             
    let btnInicio = document.getElementById("btn-inicio");

    let pacienteTurnoId = document.getElementById("sel-paciente");
    let odontologoTurnoId = document.getElementById("sel-odontologo");
    let fechaTurno = document.getElementById("inp-fecha-turno");
    let horaTurno = document.getElementById("inp-hora-turno");        

    //btnGuardarTurno.addEventListener("click", guardarTurno);
    formTurno.addEventListener("submit", function(event) {
        event.preventDefault();

        const payload = {
            pacienteId: pacienteTurnoId.value,
            odontologoId: odontologoTurnoId.value,
            fecha: fechaTurno.value,
            hora: horaTurno.value
        };
        console.log(JSON.stringify(payload));

        const settings = {
            method: 'POST',
            body: JSON.stringify(payload),
            headers: {
                "Content-Type": "application/json"
            }
        }
        
        fetch("http://localhost:8080/turnos", settings)
        .then(response => response.json())
        .then(data => {
            console.log(data);
            cargarTurnos();
        })
        .catch(error => {
            console.log(error);
        });

        formTurno.reset();
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
            let opcionPaciente = document.createElement("option");
            opcionPaciente.text = "Seleccione el paciente";
            opcionPaciente.value = "";
            opcionPaciente.setAttribute("selected", "selected");
            opcionPaciente.setAttribute("disabled", "disabled");            
            opcionPaciente.setAttribute("hidden", "hidden");            
            selectPacientes.appendChild(opcionPaciente);             
            for(paciente of listaPacientes) {
                let opcionPaciente = document.createElement("option");
                opcionPaciente.text = `${paciente.nombre} ${paciente.apellido}`;
                opcionPaciente.value = paciente.id;
                selectPacientes.appendChild(opcionPaciente);           
            }     
        })
        .catch(error => {
            console.log("Error en la petición: " + error);
        })
        return listaPacientes;
    }

    function cargarOdontologos() {
        fetch("http://localhost:8080/odontologos")
        .then(response => {
            return response.json();
        })
        .then(data => {
            console.log(data);
            listaOdontologos = data;
            let opcionOdontologo = document.createElement("option");
            opcionOdontologo.text = "Seleccione el odontólogo";
            opcionOdontologo.value = "";
            opcionOdontologo.setAttribute("selected", "selected");
            opcionOdontologo.setAttribute("disabled", "disabled");            
            opcionOdontologo.setAttribute("hidden", "hidden");            
            selectOdontologos.appendChild(opcionOdontologo);                  
            for(odontologo of listaOdontologos) {
                let opcionOdontologo = document.createElement("option");
                opcionOdontologo.text = `${odontologo.nombre} ${odontologo.apellido}`;
                opcionOdontologo.value = odontologo.id;
                selectOdontologos.appendChild(opcionOdontologo);                    
            }            
        })
        .catch(error => {
            console.log("Error en la petición");
        })
    }   
    

    function cargarTurnos() {
        fetch("http://localhost:8080/turnos")
        .then(response => {
            return response.json();
        })
        .then(data => {
            console.log(data);
            listaTurnos = data;
            mostrarTablaDeTurnos()            
        })
    }

    function borrarTurno(id) {
        const settings = {
            method: 'DELETE',
        }
        
        fetch("http://localhost:8080/turnos/" + id, settings)
       .then(response => {
            console.log(response);
            cargarTurnos();            
       })
        .catch(error => {
            console.log(error.message);
        });   
    }    

    
    function mostrarTablaDeTurnos() {

        if (document.contains(document.getElementById("div-tabla-turnos"))) {
            document.getElementById("div-tabla-turnos").remove();
        }        

        let divTablaTurnos = document.createElement("div");
        divTablaTurnos.setAttribute("id", "div-tabla-turnos");     
        let tablaTurnos = document.createElement("table");
        let templateTablaTurnos = `<tr>
            <th>Paciente</th>
            <th>Odontólogo</th>
            <th>Fecha</th>
            <th>Hora</th>
            <th></th>          
        </tr>`;
        
        for(turno of listaTurnos) {        
            templateTablaTurnos += `<tr>
                <td>${turno.nombrePaciente}</td>
                <td>${turno.nombreOdontologo}</td>
                <td>${turno.fecha}</td>
                <td>${turno.hora}</td>
                <td><i class="fas fa-trash-alt" id="btn-borrar-turno-${turno.id}"></i></td>             
            </tr>`;
        }
        tablaTurnos.innerHTML += templateTablaTurnos;
        let tituloTurnos = document.createElement("h3");
        tituloTurnos.innerText = "Listado de Turnos";
        divTablaTurnos.appendChild(tituloTurnos);          
        divTablaTurnos.appendChild(tablaTurnos);
        divTurnos.appendChild(divTablaTurnos);
        
        let btnBorrarTurnos = [];
        for (let k=0; k<listaTurnos.length; k++) {
            btnBorrarTurnos[k] = document.getElementById(`btn-borrar-turno-${listaTurnos[k].id}`);
            btnBorrarTurnos[k].addEventListener("click", () => {
                borrarTurno(listaTurnos[k].id);
                console.log("Se borrará el paciente con id " + listaTurnos[k].id);
            })
        }        
    }
    

    cargarOdontologos();
    cargarPacientes();
    cargarTurnos();
})