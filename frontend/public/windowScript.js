// Окно админа
$(document).ready(function () {
    $("a.myLinkModal").click(function (event) {
        event.preventDefault();
        $("#myOverlay").fadeIn(297, function () {
            $("#myModal")
                .css("display", "block")
                .animate({ opacity: 1 }, 100);
        });
    });

    $("#myModal__close, #myOverlay").click(function () {
        $("#myModal").animate({ opacity: 0 }, 100, function () {
            $(this).css("display", "none");
            $("#myOverlay").fadeOut(200);
        });
    });
});