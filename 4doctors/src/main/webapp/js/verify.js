var session_code = sessionStorage.getItem("verification_code");
var verification_form = document.getElementById("verification_form");
var code = document.getElementById("code");
var error = document.getElementsByClassName("error")[0];

// Listener on form to validate data on submit
verification_form.addEventListener("submit", function (event){

    // Check if parameters are null
    if(code.value == null) {
        code.className = "invalid";
        error.innerHTML = "The verification code can't be null!";
        event.preventDefault();
        return false;
    }

    var code_value = code.value.trim();

    // Check if parameters value are valid or not
    if(code_value.length === 0) {
        code.className = "invalid";
        error.innerHTML = "The verification code can't be empty!";
        event.preventDefault();
        return false;
    } else if(code_value !== session_code) {        // Check if code inserted by user is correct
        code.className = "invalid";
        error.innerHTML = "Incorrect verification code. Please rewrite the code.";
        event.preventDefault();
        return false;
    }
    else {
        code.className = "valid";
        sessionStorage.removeItem("verification_code");
    }

    code.value = code_value;
});

code.addEventListener("focus", function () {
    code.classList.remove("invalid");
    error.innerHTML = "";
});