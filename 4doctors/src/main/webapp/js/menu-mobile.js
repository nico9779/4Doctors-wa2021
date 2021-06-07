// triggers at the very beginning when the document is loaded
$(function() {

	$("#toggle-menu-button").click(toggleMenu);
	
});

function toggleMenu() {

	if($("nav").hasClass("menu-open")) closeMenu();
	else openMenu();
}

function openMenu() {

	$("#toggle-menu-button").addClass("menu-open");
	$("nav").addClass("menu-open");
	
	var overlay = $('<div class="overlay" onclick="closeMenu()"></div>');
	$("#site").append(overlay);
	$("body").css("overflow", "hidden");

	$(".overlay").animate({opacity: 1}, 400);
}

function closeMenu() {

	$("#toggle-menu-button").removeClass("menu-open");
	$("nav").removeClass("menu-open");
	$("body").css("overflow", "");
		
	$(".overlay").animate({
		opacity: 0
	}, 400, function() { //overlay remotion
		$(".overlay").remove();
	});
}