$(document).ready(function () {

    $('#cityDropdown li > a').click( function(event) {
        var locationUidValue = $(this).data('location');
        var locationName = $(event.target).text();
        //$('.dropwown button#dropdownMenuBtn').html(locationName);
        $.cookie('locationUid', JSON.stringify({'uid': locationUidValue, 'name': locationName}), { expires: 1, path: '/' });
    });

    /* scrolling for scroll to top */
    $('.scroll-top').click(function() {
        $('body, html').animate({scrollTop: 0}, 1000);
    });

});