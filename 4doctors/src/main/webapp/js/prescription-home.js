
(function() {
var xhr1 = new XMLHttpRequest();
var xhr2 = new XMLHttpRequest();

var absolutePath = function(href) {
    var link = document.createElement("a");
    link.href = href;
    return link.href;
};

var url_medicine = absolutePath(contexPath + '/rest/medicine');
var url_exams = absolutePath(contexPath + '/rest/exams');

xhr1.onreadystatechange = alertContents1;
xhr2.onreadystatechange = alertContents2;

xhr1.open('GET', url_medicine);
xhr2.open('GET', url_exams);

xhr1.send();
xhr2.send();

function alertContents1() {

    if(xhr1.readyState === XMLHttpRequest.DONE){

    if(xhr1.status === 200){

        var responseObject = JSON.parse(xhr1.responseText);
        var resource = responseObject['resourceList'];
        var med = document.getElementById('med_select');

        for (var i = 0; i < resource.length; i++){

            med.add(new Option(resource[i].medicine.name, resource[i].medicine.code));

        }
    }
    }
}

function alertContents2() {

    if(xhr2.readyState === XMLHttpRequest.DONE){

    if(xhr2.status === 200){

        var responseObject2 = JSON.parse(xhr2.responseText);
        var resource2 = responseObject2['resourceList'];
        var med2 = document.getElementById('ex_select');

        for (var i = 0; i < resource2.length; i++){

            med2.add(new Option(resource2[i].exam.name, resource2[i].exam.code));

        }
    }
    }
}

var prescription_form = document.getElementById("p_form");
var inputFields = document.querySelectorAll('input[class=req]');
var errors = document.getElementsByClassName("error");

prescription_form.addEventListener("submit", function (event) {

    inputFields.forEach(function (element, i) {
        if(element.value == null){
            element.className = "invalid";
            errors[i].innerHTML = "The field can't be null!";
            event.preventDefault();
            return false;
        }
        var field_value = element.value;

        if(field_value.length === 0) {
            element.className = "invalid";
            errors[i].innerHTML = "The field can't be empty!";
            event.preventDefault();
            return false;
        } else {
            element.className = "valid";
        }

    });
});

$("#medicine").click(function(){
        $("#medicine_select").css("display","block");
        $("#quant").css("display","block");
        $("#exam_select").css("display","none");
});

$("#exam").click(function(){
        $("#exam_select").css("display","block");
        $("#medicine_select").css("display","none");
        $("#quant").css("display","none");
});

$("#pres_med").click(function(){
        var cf = $("#pres_med").val();
        console.log(cf);
        $.ajax({
            url: url_medicine + '/' +cf,
            type: 'get',
            success: function(data){
                console.log(data);

                var medicine_list = data.resourceList;

                if(medicine_list.length!==0){
                    console.log(medicine_list);

                    var code_html = $(document.createElement("td")).text(medicine_list[0].medicine.code);
                    var name_html = $(document.createElement("td")).text(medicine_list[0].medicine.name);
                    var producer_html = $(document.createElement("td")).text(medicine_list[0].medicine.producer);
                    var medicine_class_html = $(document.createElement("td")).text(medicine_list[0].medicine.medicine_class);
                    var description_html = $(document.createElement("td")).text(medicine_list[0].medicine.description);
                    $("#medicine_table tbody").html($(document.createElement("tr")).append(code_html).append(name_html).append(description_html).append(medicine_class_html).append(producer_html));


                    for (var i = 1; i < medicine_list.length; i++){

                                var code_html = $(document.createElement("td")).text(medicine_list[i].medicine.code);
                                var name_html = $(document.createElement("td")).text(medicine_list[i].medicine.name);
                                var producer_html = $(document.createElement("td")).text(medicine_list[i].medicine.producer);
                                var medicine_class_html = $(document.createElement("td")).text(medicine_list[i].medicine.medicine_class);
                                var description_html = $(document.createElement("td")).text(medicine_list[i].medicine.description);

                                $("#medicine_table tbody").append($(document.createElement("tr")).append(code_html).append(name_html).append(description_html).append(medicine_class_html).append(producer_html));

                    }
                } else{
                    $("#medicine_wrapper").html("<p class='table_message'>There are no medicines already prescribed to you.</p>");
                }
                $("#medicine_wrapper").css("display", "block");
            },
            error: function(){
                console.log(error);
            }
        })
});

})();


