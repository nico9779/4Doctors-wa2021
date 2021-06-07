var update_email_form = document.getElementById("update_email_form");
var emails = document.querySelectorAll('input[type=text]');

const email_regex = /^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;

var errors = document.getElementsByClassName("error");


update_email_form.addEventListener("submit", function (event) {

    emails.forEach(function (element, i) {
        if(element.value == null){
            element.className = "invalid";
            errors[i].innerHTML = "The e-mail can't be null!";
            event.preventDefault();
            return false;
        }
        var email_value = element.value;

        if(email_value.length === 0) {
            element.className = "invalid";
            errors[i].innerHTML = "The e-mail can't be empty!";
            event.preventDefault();
            return false;
        } else if (!email_regex.test(email_value)) {
            element.className = "invalid";
            errors[i].innerHTML = "The e-mail format is wrong!";
            event.preventDefault();
            return false;
        } else {
            element.className = "valid";
        }
    });
});

emails.forEach(function (element, i) {
    element.addEventListener("focusout", function (event) {
        if(element.value == null){
            element.className = "invalid";
            errors[i].innerHTML = "The e-mail can't be null!";
            event.preventDefault();
            return false;
        }
        var email_value = element.value;

        if(email_value.length === 0) {
            return true;
        } else if (!email_regex.test(email_value)) {
            element.className = "invalid";
            errors[i].innerHTML = "The e-mail format is wrong!";
            event.preventDefault();
            return false;
        } else {
            element.className = "valid";
        }
    });
});

emails.forEach(function (element, i) {
    element.addEventListener("focusin", function (event) {
        element.classList.remove("invalid");
        errors[i].innerHTML = "";
    });
});



