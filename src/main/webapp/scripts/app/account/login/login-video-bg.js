
/** Document Ready Functions **/
/********************************************************************/

$(document).load(function() {
    scaleVideoContainer();

    initBannerVideoSize('.video-container video');

    $(window).on('resize', function() {
        scaleVideoContainer();

        scaleBannerVideoSize('.video-container video');
    });

});

$(window).load(function() {
    $(".token.attr-value:contains('PATH_TO')").css('background-color', '#5965BB').children().css('background-color', '#272822');
});

/** Reusable Functions **/
/********************************************************************/

function scaleVideoContainer() {

    var width = $(window).width();
    var unitWidth = parseInt(width) + 'px';
    $('.ch-layout').css('width',unitWidth);

}

function initBannerVideoSize(element){

    $(element).each(function(){
        $(this).data('height', $(this).height());
        $(this).data('width', $(this).width());
    });

    scaleBannerVideoSize(element);

}

function scaleBannerVideoSize(element){

    var windowWidth = $(window).width(),
        windowHeight = $(window).height(),
        videoWidth,
        videoHeight;

    $(element).each(function(){
        var videoAspectRatio = $(this).data('height')/$(this).data('width');

        $(this).width(windowWidth);

        if(windowWidth < 1000){
            videoHeight = windowHeight;
            videoWidth = videoHeight / videoAspectRatio;
            $(this).css({'margin-top' : 0, 'margin-left' : -(videoWidth - windowWidth) / 2 + 'px'});

            $(this).width(videoWidth).height(videoHeight);
        }

        $('.video-container video').addClass('fadeIn animated');

    });
}
