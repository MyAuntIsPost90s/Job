$(function () {
    setLayoutSize();
    $(window).resize(function () {          //当浏览器大小变化时
        setLayoutSize();
    });
})

function setLayoutSize() {
    $('body').css('width', innerWidth + 'px');
    $('.header').css('width', innerWidth + 'px');
    $('.layout-footer').css('width', innerWidth + 'px');
    $('.container').css('height', (innerHeight - 90) + 'px');
    $('.main').css('width', (innerWidth - 205) + 'px');
    $('.main .layui-tab-content').css('height', (innerHeight - 90 - 60) + 'px');
}