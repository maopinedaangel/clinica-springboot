window.addEventListener("load", () => {

    let btnPacientes = document.getElementById("btn-pacientes");
    let btnOdontologos = document.getElementById("btn-odontologos");    
    let btnTurnos = document.getElementById("btn-turnos"); 
    
    console.log(new Date().toISOString());
    btnPacientes.addEventListener("click", () => {
        location.replace("./pacientes.html");
    })

    btnOdontologos.addEventListener("click", () => {
        location.replace("./odontologos.html");
    })

    btnTurnos.addEventListener("click", () => {
        location.replace("./turnos.html");
    })

    let cardPacientes = document.getElementById("card-pacientes");
    let cardOdontologos = document.getElementById("card-odontologos");    
    let cardTurnos = document.getElementById("card-turnos"); 
    
    cardPacientes.addEventListener("click", () => {
        location.replace("./pacientes.html");
    })

    cardOdontologos.addEventListener("click", () => {
        location.replace("./odontologos.html");
    })

    cardTurnos.addEventListener("click", () => {
        location.replace("./turnos.html");
    })

})