var $, layer;

//主动加载jquery模块
layui.use(['jquery', 'layer'], function () {
    $ = layui.$ //重点处
        , layer = layui.layer;
});