$(function () {

    $("#authorityManager").addClass("layui-this");
    $("#authorityManager").parent().parent().addClass("layui-nav-itemed");


});


/**
 设置权限和功能的对应
 */
function atfSetting(id) {

    $.ajax({
        url: "/feature/authorityId/" + id,
        type: "GET",
        dataType: "JSON",
        success: function (data) {
            atfSuccess(data, id);
        }
    });


}

/**
 * 成功弹出框的回调，对弹框进行渲染
 *
 * @param data json数据
 * @param id    权限ID
 */
function atfSuccess(data, id) {
    layer.open({
        title: '权限-功能设置',
        content: $("#atfSetting").html(),
        success: function (layero, index) {

            for (var fi in data) {
                //选中
                $(layero).find("input[title='" + data[fi].featureName + "']").prop('checked', true);
            }

            layui.use('form', function () {
                layui.form.render(); //更新全部
            });
        },
        btn: ['确认', '取消'],
        yes: function (index, layero) {
            //复选框集合
            var atfs = $(layero).find("input[type='checkbox'][name='atfs']");

            if (atfs == null || atfs.length == 0) {
                layer.close(index);
                return;
            }

            //创建一个JSON对象，传到后台去
            var atfJson = {};
            atfJson["authorityId"] = id;

            for (var i = 0; i < atfs.length; i++) {
                atfJson[$(atfs[i]).attr("title")] = $(atfs[i]).is(':checked');
            }

            $.ajax({
                url: "/authority/atf",
                type: "POST",
                data: atfJson
            });

            layer.close(index);
        },

    });
}


function addAuthority() {
    layer.prompt({
        title: '请输入权限名称',
    }, function (value, index, elem) {

        //向服务端发送添加指令
        $.ajax({//put request
            url: "/authority/" + value,
            type: "PUT",
            success: function (data) {
                layer.close(index);
                window.location.reload()
            }
        });


    });
}

function addFeature() {
    layer.prompt({
        title: '请输入功能名称',
    }, function (value, index, elem) {
        //向服务端发送添加指令
        $.ajax({//put request
            url: "/feature/" + value,
            type: "PUT",
            success: function (data) {
                layer.close(index);
                window.location.reload()
            }
        });

    });
}

function deleteAuthority(id, dom) {

    layer.confirm('真的删除该权限么', function (index) {

        //向服务端发送删除指令
        $.ajax({//delete request
            url: "/authority/" + id,
            type: "DELETE",
            success: function (data) {
                layer.close(index);
                window.location.reload()
            }
        });

    });


}

function deleteFeature(id, dom) {

    layer.confirm('真的删除该功能么', function (index) {

        //向服务端发送删除指令
        $.ajax({//delete request
            url: "/feature/" + id,
            type: "DELETE",
            success: function (data) {
                layer.close(index);
                window.location.reload()
            }
        });

    });


}