var add_doctor_form = $("#add_doctor_form");
var cf = $("#cf");
var _name = $("#name");
var surname = $("#surname");
var email = $("#email");
var password = $("#password");
var retype_password = $("#retype_password");
var birthday = $("#birthday");
var birthplace = $("#birthplace");
var address = $("#address");
var asl_code = $("#asl_code");
var radio_male = $("#male");
var radio_female = $("#female");
var errors = $("div.error");
var password_strength = $("#password_strength");
var add_doctor_message = $("#add_doctor_message");

const contextPath = "http://localhost:8080/4Doctors-1.00";

// Regex to validate cf, email, asl code and password strength
const cf_regex = /[A-Za-z]{6}[0-9]{2}[A-Za-z][0-9]{2}[A-Za-z][0-9]{3}[A-Za-z]/;
const email_regex = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
const asl_regex = /[A-Z0-9]+/;
const strong_password = /(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.{8,})/;
const medium_password = /(?=.*[A-Za-z])(?=.*[0-9])(?=.{6,})/;

// Listener on form to validate data on submit
add_doctor_form.submit(function (event) {

    // Check if parameters are null
    if(cf.val() == null){
        cf.addClass("invalid");
        errors.eq(0).html("The fiscal code can't be null!");
        event.preventDefault();
        return false;
    }

    if(_name.val() == null){
        _name.addClass("invalid");
        errors.eq(1).html("The name can't be null!");
        event.preventDefault();
        return false;
    }

    if(surname.val() == null){
        surname.addClass("invalid");
        errors.eq(2).html("The surname can't be null!");
        event.preventDefault();
        return false;
    }

    if(email.val() == null){
        email.addClass("invalid");
        errors.eq(3).html("The email can't be null!");
        event.preventDefault();
        return false;
    }

    if(birthday.val() == null){
        birthday.addClass("invalid");
        errors.eq(4).html("The birthday can't be null!");
        event.preventDefault();
        return false;
    }

    if(birthplace.val() == null){
        birthplace.addClass("invalid");
        errors.eq(5).html("The birthplace can't be null!");
        event.preventDefault();
        return false;
    }

    if(address.val() == null){
        address.addClass("invalid");
        errors.eq(6).html("The address can't be null!");
        event.preventDefault();
        return false;
    }

    if(asl_code.val() == null){
        asl_code.addClass("invalid");
        errors.eq(7).html("The ASL Code can't be null!");
        event.preventDefault();
        return false;
    }

    if(password.val() == null){
        password.addClass("invalid");
        errors.eq(9).html("The password can't be null!");
        event.preventDefault();
        return false;
    }

    if(retype_password.val() == null){
        retype_password.addClass("invalid");
        errors.eq(10).html("The retyped password can't be null!");
        event.preventDefault();
        return false;
    }

    // Remove leading and trailing whitespace
    var cf_value = cf.val().trim().toUpperCase();
    var name_value = _name.val().trim();
    var surname_value = surname.val().trim();
    var email_value = email.val().trim();
    var pwd_value = password.val();
    var retype_pwd_value = retype_password.val();
    var birthday_value = birthday.val().trim();
    var birthplace_value = birthplace.val().trim();
    var address_value = address.val().trim();
    var asl_code_value = asl_code.val().trim();

    // Check if parameters value are valid or not
    if(cf_value.length === 0) {
        cf.addClass("invalid");
        errors.eq(0).html("The fiscal code can't be empty!");
        event.preventDefault();
        return false;
    } else if(!cf_regex.test(cf_value)) {
        cf.addClass("invalid");
        errors.eq(0).html("The format of the fiscal code is wrong!");
        event.preventDefault();
        return false;
    } else {
        cf.addClass("valid");
    }

    if(name_value.length === 0) {
        _name.addClass("invalid");
        errors.eq(1).html("The name can't be empty!");
        event.preventDefault();
        return false;
    } else {
        _name.addClass("valid");
    }

    if(surname_value.length === 0) {
        surname.addClass("invalid");
        errors.eq(2).html("The surname can't be empty!");
        event.preventDefault();
        return false;
    } else {
        surname.addClass("valid");
    }

    if(email_value.length === 0) {
        email.addClass("invalid");
        errors.eq(3).html("The email can't be empty!");
        event.preventDefault();
        return false;
    } else if(!email_regex.test(email_value)) {
        email.addClass("invalid");
        errors.eq(3).html("The value inserted is not an email!");
        event.preventDefault();
        return false;
    } else {
        email.addClass("valid");
    }

    if(birthday_value.length === 0) {
        birthday.addClass("invalid");
        errors.eq(4).html("The birthday can't be empty!");
        event.preventDefault();
        return false;
    } else if(isDateInvalid(birthday_value)) {
        birthday.addClass("invalid");
        errors.eq(4).html("The date format is not valid!");
        event.preventDefault();
        return false;
    } else if(isFutureDate(birthday_value)) {
        birthday.addClass("invalid");
        errors.eq(4).html("The birthday can't be after the current date!");
        event.preventDefault();
        return false;
    } else {
        birthday.addClass("valid");
    }

    if(birthplace_value.length === 0) {
        birthplace.addClass("invalid");
        errors.eq(5).html("The birthplace can't be empty!");
        event.preventDefault();
        return false;
    } else {
        birthplace.addClass("valid");
    }

    if(address_value.length === 0) {
        address.addClass("invalid");
        errors.eq(6).html("The address can't be empty!");
        event.preventDefault();
        return false;
    } else {
        address.addClass("valid");
    }

    if(asl_code_value.length === 0) {
        asl_code.addClass("invalid");
        errors.eq(7).html("The ASL Code can't be empty!");
        event.preventDefault();
        return false;
    } else if(!asl_regex.test(asl_code_value)) {
        asl_code.addClass("invalid");
        errors.eq(7).html("The format of the ASL Code is wrong!");
        event.preventDefault();
        return false;
    } else {
        asl_code.addClass("valid");
    }

    if(!radio_male.is(":checked") && !radio_female.is(":checked")){
        radio_male.addClass("invalid");
        radio_female.addClass("invalid");
        errors.eq(8).html("A gender must be selected!");
        event.preventDefault();
        return false;
    } else {
        radio_male.addClass("valid");
        radio_female.addClass("valid");
    }

    if(pwd_value.length === 0) {
        password.addClass("invalid");
        errors.eq(9).html("The password can't be empty");
        event.preventDefault();
        return false;
    } else if(!medium_password.test(pwd_value)) {
        password.addClass("invalid");
        errors.eq(9).html("The password should contain at least one lower case character, one number and be long at least 6 characters");
        event.preventDefault();
        return false;
    } else if(retype_pwd_value.length === 0){
        retype_password.addClass("invalid");
        errors.eq(10).html("The retyped password can't be empty");
        event.preventDefault();
        return false;
    } else if(pwd_value !== retype_pwd_value) {
        password.addClass("invalid");
        retype_password.addClass("invalid");
        errors.eq(10).html("The passwords are not corresponding");
        event.preventDefault();
        return false;
    } else {
        password.addClass("valid");
        retype_password.addClass("valid");
    }

    // Prevent to submit form since the action to perform is an AJAX call
    event.preventDefault();

    // Build JSON with doctor fields
    var doctor_json = {
        doctor: {
            cf: cf_value,
            name: name_value,
            surname: surname_value,
            email: email_value,
            password: CryptoJS.SHA3(pwd_value).toString(),
            birthday: birthday_value,
            birthplace: birthplace_value,
            address: address_value,
            gender: $('input[name="gender"]:checked').val(),
            aslcode: asl_code_value
        }
    };

    // REST call to add new doctor to the database
    $.ajax({
        url: contextPath + "/rest/doctor",
        method: "POST",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify(doctor_json),
        // If success or there is an error show it in a box
        success: function(result) {
            add_doctor_message.removeClass().addClass("alert alert-success").attr("role", "alert").text("New doctor added");
        },
        error: function(result) {
            var error_message = result.responseJSON["error"]["message"];
            add_doctor_message.removeClass().addClass("alert alert-danger").attr("role", "alert").text("Problem occurred while adding new doctor. "+error_message);
        }
    });
});

$("input").focus(function () {
    $(this).removeClass("invalid");
    errors.html("");
    add_doctor_message.removeClass().text("");
});

password.on("input", function (event) {

    if($(this).val().length === 0) {
        password_strength.css("display", "none");
    } else {
        strengthChecker($(this).val());
        password_strength.css("display", "block");
    }
});

function isDateInvalid(date_string) {

    var date = Date.parse(date_string);
    return isNaN(date);
}

function isFutureDate(date_string) {
    var date = new Date(date_string);
    var currentdate = new Date();

    return date > currentdate;
}

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