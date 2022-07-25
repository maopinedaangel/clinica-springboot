window.addEventListener("load", () => {

    let formLogin = document.getElementById("form-login");
    let inputUsername =  document.getElementById("inp-username");
    let inputPassword = document.getElementById("inp-password");

    formLogin.addEventListener("submit", (e) => {
        e.preventDefault();

        let username = inputUsername.value;
        let password = inputPassword.value;

        console.log(`Username: ${username}, Password: ${password}.`);


        const payload = {
            username: username,
            password: password,

        };
        console.log(JSON.stringify(payload));

        const settings = {
            method: 'POST',
            body: JSON.stringify(payload),
            headers: {
                "Content-Type": "application/json"
            }
        }        

        fetch("http://localhost:8080/authenticate", settings)
        .then(response => response.json())
        .then(data => {
            console.log(data);

        })
        .catch(error => {
            console.log(error);
        })


    })
})