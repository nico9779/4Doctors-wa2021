
function addMedEx() {

    if(validate()){

        var patientCF = getCookieValue("patientCF");
        var doctorCF = document.getElementById("patientDoctor").value;
        var selectedDate = document.getElementById("dateselect").value;
        var selectedTime = document.getElementById("timeToSelect").value;

        var medEx = {
            medicalExamination: {
                patient_cf: patientCF,
                doctor_cf: doctorCF,
                date: selectedDate,
                time: selectedTime,
                outcome: '--'
            }
        }

        $.ajax({
            url: 'http://localhost:8080/4Doctors-1.00/rest/medicalExamination',
            type: 'post',
            dataType: 'json',
            contentType: 'application/json',
            success: function (responseMedEx) {

                var table = document.getElementById('future-exams');
                var sdate = new Date(responseMedEx.medicalExamination.date);
                var inserted = false;

                var notbooked = document.getElementById('notbooked');

                //if the element is there, no examination was booked yet
                if(notbooked){

                    table.deleteRow(1);

                    var newRow = table.getElementsByTagName('tbody')[0].insertRow();

                    var datecell = newRow.insertCell(0);
                    var timecell = newRow.insertCell(1);
                    var doctorcell = newRow.insertCell(2);
                    var cancelbutton = newRow.insertCell(3);

                    datecell.innerText = responseMedEx.medicalExamination.date;
                    timecell.innerText = responseMedEx.medicalExamination.time;
                    doctorcell.innerText = responseMedEx.medicalExamination.doctor_cf;
                    cancelbutton.innerHTML = '<button id="deletemedex" onClick="deleteMedEx(this)">Cancel</button>';

                    console.log("row inserted");

                    return;
                }

                //compare the date with the elements in the table to find the row where to place the new medex
                for (var i = 1, row; row = table.rows[i]; i++) {
                    //obtain date in row i (start from second row of the table to skip the tableheader)
                    var dateInTheRow = new Date(row.cells[0].innerText);

                    if(sdate < dateInTheRow && !inserted){
                        var newRow = table.insertRow(i);

                        var datecell = newRow.insertCell(0);
                        var timecell = newRow.insertCell(1);
                        var doctorcell = newRow.insertCell(2);
                        var cancelbutton = newRow.insertCell(3);

                        doctorcell.setAttribute("data-attr",responseMedEx.medicalExamination.doctor_cf);

                        datecell.innerText = responseMedEx.medicalExamination.date;
                        timecell.innerText = responseMedEx.medicalExamination.time;
                        doctorcell.innerText = responseMedEx.medicalExamination.doctor_cf;
                        cancelbutton.innerHTML = '<button id="deletemedex" onClick="deleteMedEx(this)">Cancel</button>';

                        console.log("row inserted");

                        inserted = true;
                    }
                }
                //if all rows were parsed without finding a slot, add the new medex at the end
                if(!inserted){

                    var newRow = table.insertRow(-1);

                    var datecell = newRow.insertCell(0);
                    var timecell = newRow.insertCell(1);
                    var doctorcell = newRow.insertCell(2);
                    var cancelbutton = newRow.insertCell(3);

                    doctorcell.setAttribute("data-attr",responseMedEx.medicalExamination.doctor_cf);

                    datecell.innerText = responseMedEx.medicalExamination.date;
                    timecell.innerText = responseMedEx.medicalExamination.time;
                    doctorcell.innerText = responseMedEx.medicalExamination.doctor_cf;
                    cancelbutton.innerHTML = '<button id="deletemedex" onClick="deleteMedEx(this)">Cancel</button>';

                    console.log("row inserted");

                    inserted = true;
                }
            },
            data: JSON.stringify(medEx)
        });
    }
}



//delete a medical examination and update the table in the page
function deleteMedEx(b){
    if(confirm('Are you sure you wish to cancel this reservation?')){

        var row = b.parentNode.parentNode;

        var medEx = {
            date: row.cells[0].innerText,
            time: formatTime(row.cells[1].innerText),
            doctor: row.cells[2].dataset.attr
        }

        $.ajax({
            url: 'http://localhost:8080/4Doctors-1.00/rest/medicalExamination/'+medEx.doctor+'/'+medEx.date+'/'+medEx.time,
            type: 'delete',
            success: function (data) {
                //when the rest call is successful, delete the row from the table
                var i = row.rowIndex;
                var table = document.getElementById("future-exams");
                table.deleteRow(i);

                //we removed all examinations, only header is left
                if(table.rows.length == 1){
                    table.getElementsByTagName('tbody')[0].innerHTML = '<tr id="notbooked"><td>No examinations booked.</td><td></td><td></td></tr>';
                }
            }
        });
    }
}



//get time as HH:mm from HH:mm:ss
function formatTime(time){
    let arr = time.split(':');
    return arr[0]+':'+arr[1];
}



//return the value of the cookie associated to the given name
function getCookieValue(cname) {
  var name = cname + '=';
  var decodedCookie = decodeURIComponent(document.cookie);
  var ca = decodedCookie.split(';');
  for(var i = 0; i <ca.length; i++) {
    var c = ca[i];
    while (c.charAt(0) == ' ') {
      c = c.substring(1);
    }
    if (c.indexOf(name) == 0) {
      return c.substring(name.length, c.length);
    }
  }
  return "";
}


//go through the form elements and check if everything is all right
function validate(){

    var doctorCF = document.getElementById("patientDoctor").value;
    var selectedDate = document.getElementById("dateselect").value;
    var selectedTime = document.getElementById("timeToSelect").value;

    var errors = document.getElementsByClassName("error");

    if(selectedDate.length == 0){
        //no date was selected
        //alert('A date for the examination needs to specified.');
        var dateSelector = document.getElementById("dateselect");
        dateSelector.className = "invalid";
        errors[0].innerHTML = "A date for the examination needs to specified.";
        dateSelector.addEventListener("focus", function () {
                                                    dateSelector.classList.remove("invalid");
                                                    errors[0].innerHTML = "";
                                             });
        return false;
    }
    //check date is not in the past
    var selectedDateDate = new Date(selectedDate);
    var today = new Date();
    if(selectedDateDate <= today){
        if(selectedDate === '2009-10-25'){
           //ee
           window.location.href = 'https://www.youtube.com/watch?v=dQw4w9WgXcQ';
           return false;
        }
        //alert('You must select a future date.');
        var dateSelector = document.getElementById("dateselect");
        dateSelector.className = "invalid";
        errors[0].innerHTML = "You must select a future date.";
        dateSelector.addEventListener("focus", function () {
                                                    dateSelector.classList.remove("invalid");
                                                    errors[0].innerHTML = "";
                                               });
        return false;
    }
    if( findExamination(selectedDate, selectedTime, doctorCF) ){
        //the specified medical examination already exists
        //alert('Time slot already booked on the selected day at the selected doctor');
        var timeSelector = document.getElementById("timeToSelect");
        timeSelector.className = "invalid";
        errors[1].innerHTML = "Time slot already booked on the selected day.";
        timeSelector.addEventListener("focus", function () {
                                                    timeSelector.classList.remove("invalid");
                                                    errors[1].innerHTML = "";
                                            });
        return false;
    }

    //all is good
    return true;
}

function findExamination(date, time, doctor){
    console.log("looking for examination");

    var found = false;

    //look for the selected examination with an ajax call, asynchronous call
    $.ajax({
        url: 'http://localhost:8080/4Doctors-1.00/rest/medicalExamination/'+doctor+'/'+date+'/'+time,
        type: 'get',
        async: false,
        success: function (data) {
            //a matching examination was found
            console.log("examination already exists");
            found = true;
        },
        error: function(){
            //no matching was found
            console.log("examination not found");
            found = false;
        }
    });

    return found;
}



function openPopup(b){
    // Get the modal
    var modal = document.getElementById("popupModal");
    var paragraph = document.getElementById("outcomeText");
    paragraph.innerText = b.value;
    modal.style.display = "block";
}

function closePopup(){
    // Get the modal
    var modal = document.getElementById("popupModal");
    modal.style.display = "none";
}
