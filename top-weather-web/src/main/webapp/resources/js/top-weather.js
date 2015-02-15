$(document).ready(function () {

    var locationName = typeof $.cookie('locationName') === 'undefined' ? 'Могилев': $.cookie('locationName');
    $('.dropdown button#dropdownMenuBtn').html(locationName);


    $('#cityDropdown li > a').click( function(event) {
        var locationUidValue = $(this).data('location');
        var locationName = $(event.target).text();
        $('.dropdown button#dropdownMenuBtn').html(locationName);
        $.cookie('locationUid', locationUidValue, { expires: 1, path: '/' });
        $.cookie('locationName', locationName, { expires: 1, path: '/' });
    });

    $('.scroll-top').click(function() {
        $('body, html').animate({scrollTop: 0}, 1000);
    });

    $('#nav .navbar-nav li>a').click(function() {
        var blockId = $(this).attr('href');
        var position = $(blockId).offset().top;
        $('body, html').animate({scrollTop: position}, 700);
    });

    $('#btn-top-10').click(function(e) {
        e.preventDefault();
        var blockId = $(this).attr('href');
        var position = $(blockId).offset().top;
        $('body, html').animate({scrollTop: position}, 700);
    });

    $('#forecastTable tbody').on('click', 'td', function () {
        var rowId = $(this).parent('tr').attr('id');
        var contentBlock = $('tr#' + rowId + '-weather');
        if(!contentBlock.hasClass("in")) {
            contentBlock.addClass("in");
        } else {
            contentBlock.removeClass("in");
        }
    });

});