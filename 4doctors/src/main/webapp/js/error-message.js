// triggers at the very beginning when the document is loaded
$(function() {

	$("#error-message-box button").click(closeAlert);

});

function closeAlert() {

    $(".overlay").animate({
        opacity: 0
    }, 400, function() { //overlay remotion
        $(".overlay").remove();
    });
}