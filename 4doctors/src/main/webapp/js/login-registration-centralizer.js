$(function() {

	$(window).resize(adjustBodyHeight);
    adjustBodyHeight();
});

function adjustBodyHeight() {
    if($(window).height() > $("body").height()) $("body").css("height", $(window).height());
}