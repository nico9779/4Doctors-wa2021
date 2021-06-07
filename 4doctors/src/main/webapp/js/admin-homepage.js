var doctors_table = $("#doctors_table");
var patients_table = $("#patients_table");
var doctors_wrapper = $("#doctors_wrapper");
var patients_wrapper = $("#patients_wrapper");
var doctors_list_h3 = $("#list_doctors_h3");
var patients_list_h3 = $("#list_patients_h3");

// const contextPath = "http://localhost:8080/4Doctors-1.00";

// When the page is loaded call the two REST service to get the list of all active doctors and all patients in the database
// and build two tables dynamically
$(document).ready(function (){
    // GET call to retrieve all the active doctors stored in the database
    $.getJSON(contextPath + "/rest/doctor/list", function (data, status) {
        if(status == "success") {
            var doctors_list = data["data"]["doctors-list"];

            // For each doctor in the list build dynamically a row in the table
            if(doctors_list.length !== 0){
                $.each(doctors_list, function (index, element){
                    var cf_html = $(document.createElement("td")).text(element["cf"]);
                    var name_html = $(document.createElement("td")).text(element["name"]);
                    var surname_html = $(document.createElement("td")).text(element["surname"]);
                    var buttons = $(document.createElement("td")).html("<button class='info' type='button'>INFO</button><button class='update' type='button'>UPDATE</button>");
                    doctors_table.append($(document.createElement("tr")).append(cf_html).append(name_html).append(surname_html).append(buttons));
                });

                doctors_table.css("display", "inline-block");
            }
            else {
                // If there are no doctor in the db show a message
                doctors_list_h3.css("display", "none");
                doctors_wrapper.html("<p class='table_message'>There are no active doctors in the database.</p>");
            }
        } else {
            // Show message if there is an error
            console.log(data);
            console.log(status);
            alert("Problem occurred while getting the list of doctors.");
        }
    });

    // GET call to retrieve all the patients stored in the database
    $.getJSON(contextPath + "/rest/patient/list", function (data, status) {
        if(status == "success") {
            var patients_list = data["data"]["patients-list"];

            // For each patient in the list build dynamically a row in the table
            if(patients_list.length !== 0) {
                $.each(patients_list, function (index, element){
                    var cf_html = $(document.createElement("td")).text(element["cf"]);
                    var name_html = $(document.createElement("td")).text(element["name"]);
                    var surname_html = $(document.createElement("td")).text(element["surname"]);
                    var buttons = $(document.createElement("td")).html("<button class='info' type='button'>INFO</button><button class='delete' type='button'>DELETE</button>");
                    patients_table.append($(document.createElement("tr")).append(cf_html).append(name_html).append(surname_html).append(buttons));
                });

                patients_table.css("display", "inline-block");
            } else {
                // If there are no patient in the db show a message
                patients_list_h3.css("display", "none");
                patients_wrapper.html("<p class='table_message'>There are no patients in the database.</p>");
            }
        } else {
            // Show message if there is an error
            console.log(data);
            console.log(status);
            alert("Problem occurred while getting the list of patients.");
        }
    });
});

// Add listener when clicking on INFO button to show all personal info about a doctor
doctors_table.on("click", "button.info", function (){

    // Get row containing cf of doctor
    var row = $(this).parent().parent();

    // If next row contains the personal info remove it
    if(row.next().attr("class") != "info_row") {
        var cf = $(this).parent().siblings("td:first").text();
        var info_row = $(document.createElement("tr")).attr("class", "info_row");
        var info_td = $(document.createElement("td")).attr("colspan", "4");

        // REST call to get personal info of a specific doctor
        $.getJSON(contextPath + "/rest/doctor/" + cf, function (data, status) {
            if(status == "success") {
                var doctor = data["data"]["doctor"];
                // Build a list containing all personal info and append it to the new row
                var list = $(document.createElement("ul")).css("list-style-type", "none");
                $.each(doctor, function (index, element) {
                    list.append($(document.createElement("li")).text(element));
                })
                info_td.html(list);
                info_row.html(info_td);
                row.after(info_row);
            }
            else {
                console.log(data);
                console.log(status);
                alert("Problem occurred while processing the request.");
            }
        });
    } else {
        row.next().remove();
    }
});

// Add listener when clicking on INFO button to show all personal info about a patient
patients_table.on("click", "button.info", function (){

    // Get row containing cf of patient
    var row = $(this).parent().parent();

    // If next row contains the personal info remove it
    if(row.next().attr("class") != "info_row") {
        var cf = $(this).parent().siblings("td:first").text();
        var info_row = $(document.createElement("tr")).attr("class", "info_row");
        var info_td = $(document.createElement("td")).attr("colspan", "4");

        // REST call to get personal info of a specific patient
        $.getJSON(contextPath + "/rest/patient/" + cf, function (data, status) {
            if(status == "success") {
                var patient = data["data"]["patient"];
                // Build a list containing all personal info and append it to the new row
                var list = $(document.createElement("ul")).css("list-style-type", "none");
                $.each(patient, function (index, element) {
                    list.append($(document.createElement("li")).text(element));
                })
                info_td.html(list);
                info_row.html(info_td);
                row.after(info_row);
            }
            else {
                console.log(data);
                console.log(status);
                alert("Problem occurred while processing the request.");
            }
        });
    } else {
        row.next().remove();
    }
});

// Add listener when clicking on UPDATE button to update status of a doctor
doctors_table.on("click", "button.update", function (){
    var cf = $(this).parent().siblings("td:first").text();
    var row = $(this).parent().parent();
    var next_row = row.next();

    // REST call to update status of a doctor
    $.ajax({
        url: contextPath + "/rest/doctor/" + cf,
        method: "PUT",
        contentType: "application/json",
        success: function(result) {
            // If success remove row and next if contains info about doctor
            row.remove();
            if(next_row.attr("class") == "info_row") {
                next_row.remove();
            }
            if($("#doctors_table tr").length === 1) {
                doctors_list_h3.css("display", "none");
                doctors_wrapper.html("<p class='table_message'>There are no active doctors in the database.</p>");
            }
        },
        error: function(result) {
            // If there is an error show it
            console.log(result);
            var error_message = result.responseJSON["error"]["message"];
            alert("Problem occurred while processing the request. "+error_message);
        }
    });
});

// Add listener when clicking on DELETE button to delete a patient from the webapp
patients_table.on("click", "button.delete", function (){
    var cf = $(this).parent().siblings("td:first").text();
    var row = $(this).parent().parent();
    var next_row = row.next();

    // REST call to delete a patient from the webapp
    $.ajax({
        url: contextPath + "/rest/patient/" + cf,
        method: "DELETE",
        success: function(result) {
            // If success remove row and next if contains info about patient
            row.remove();
            if(next_row.attr("class") == "info_row") {
                next_row.remove();
            }
            if($("#patients_table tr").length === 1) {
                patients_list_h3.css("display", "none");
                patients_wrapper.html("<p class='table_message'>There are no patients in the database.</p>");
            }
        },
        error: function(result) {
            // If there is an error show it
            console.log(result);
            var error_message = result.responseJSON["error"]["message"];
            alert("Problem occurred while processing the request. "+error_message);
        }
    });
});