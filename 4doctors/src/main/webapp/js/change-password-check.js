var change_password_form = document.getElementById("change_password_form");
var passwords = document.querySelectorAll('input[type=password]');
var errors = document.getElementsByClassName("error");
var password_strength = $("#password_strength");
var pwd = $("#pwd");

const strong_password = /(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.{8,})/;
const medium_password = /(?=.*[a-z])(?=.*[0-9])(?=.{6,})/;

change_password_form.addEventListener("submit", function (event) {

    passwords.forEach(function (element, i) {
        if(element.value == null){
            element.className = "invalid";
            errors[i].innerHTML = "The password can't be null!";
            event.preventDefault();
            return false;
        }
        var pwd_value = element.value;

        if(pwd_value.length === 0) {
            element.className = "invalid";
            errors[i].innerHTML = "The password can't be empty!";
            event.preventDefault();
            return false;
        } else {
            element.className = "valid";
        }

        element.value = CryptoJS.SHA3(pwd_value).toString();

    });
});

passwords.forEach(function (element, i) {
        element.addEventListener("focus", function () {
            element.classList.remove("invalid");
            errors[i].innerHTML = "";
        });
});


// Check password strength when writing new password
pwd.on("input", function (event) {

    if($(this).val().length === 0) {
        password_strength.css("display", "none");
    } else {
        strengthChecker($(this).val());
        password_strength.css("display", "block");
    }
});


// Check the strength of a password changing style according to 3 different level
function strengthChecker(password){

    if(strong_password.test(password)) {
        password_strength.css("background-color", "#01b050");
        password_strength.html("Strong");
    } else if(medium_password.test(password)){
        password_strength.css("background-color", "blue");
        password_strength.html("Medium");
    } else{
        password_strength.css("background-color", "#d00");
        password_strength.html("Weak");
    }
}


