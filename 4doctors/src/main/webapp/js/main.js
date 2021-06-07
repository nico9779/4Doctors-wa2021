// triggers at the very beginning when the "body"" is loaded
$(function() {

    setSiteHeight();

});

// triggers each time the window is resized
$(window).resize(setSiteHeight);

// set the #site height to avoid overflow (and consequent scrollbars) out of the body
function setSiteHeight() {

    $("#site").css("height", "auto");

    //set height always except for mobile version
    if( ($(window).outerWidth() > 768)  || ( ($(window).outerWidth() <= 768) && ($("body").height() < $(window).height()) )) {
        if($("header").css("display") == "none")
            $("#site").css("height", $(window).height() - $("footer").outerHeight());
        else
            $("#site").css("height", $(window).height() - $("header").outerHeight() - $("footer").outerHeight());
    }
}