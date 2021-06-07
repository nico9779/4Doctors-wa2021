var username = document.getElementById("user");
var password = document.getElementById("key");
var login_form = document.getElementById("login_form");
var errors = document.getElementsByClassName("error");

// Listener on form to validate data on submit
login_form.addEventListener("submit", function (event) {

    // Check if parameters are null
    if(username.value == null){
        username.className = "invalid";
        errors[0].innerHTML = "The username can't be null!";
        event.preventDefault();
        return false;
    }

    if(password.value == null){
        password.className = "invalid";
        errors[1].innerHTML = "The password can't be null!";
        event.preventDefault();
        return false;
    }

    var username_value = username.value.trim();
    var pwd_value = password.value;

    // Check if parameters value are valid or not
    if(username_value.length === 0) {
        username.className = "invalid";
        errors[0].innerHTML = "The username can't be empty!";
        event.preventDefault();
        return false;
    } else {
        username.className = "valid";
    }

    if(pwd_value.length === 0) {
        password.className = "invalid";
        errors[1].innerHTML = "The password can't be empty!";
        event.preventDefault();
        return false;
    } else {
        password.className = "valid";
    }

    username.value = username_value;
    // Crypt password using CryptoJS library and SHA3 algorithm
    password.value = CryptoJS.SHA3(pwd_value).toString();
});

// Add listener to remove errors messages and class on focus
username.addEventListener("focus", function () {
    username.classList.remove("invalid");
    errors[0].innerHTML = "";
});

password.addEventListener("focus", function () {
    password.classList.remove("invalid");
    errors[1].innerHTML = "";
});
