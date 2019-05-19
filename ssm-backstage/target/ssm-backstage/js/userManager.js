$(function () {

    $("#userManager").addClass("layui-this");
    $("#userManager").parent().parent().addClass("layui-nav-itemed");

    tableDataBind();

    tableTool();
});

/**
 * 表格数据绑定
 */
function tableDataBind() {
    var table = layui.table;

    table.render({
        elem: '#demo',
        height: 312,
        width: 838,
        url: '/user/tablePage/', //数据接口
        page: true,//开启分页
        cols: [[ //表头
            {field: 'userId', title: 'ID', width: 80, sort: true, fixed: 'left'},
            {field: 'userName', title: '用户名', width: 80},
            {field: 'sex', title: '性别', width: 80, sort: true},
            {field: 'idCard', title: '身份证', width: 200},
            {field: 'phone', title: '手机号', width: 140},
            {
                field: 'authority',
                title: '权限',
                width: 110,
                templet: '<div>{{d.authority.authorityName}}</div>'
            },
            {field: 'userId', title: '操作', width: 140, align: 'center', toolbar: '#operating'},

        ]]
    });
}

/**
 * 表格的监听
 */
function tableTool() {
    var table = layui.table;

    //监听工具条
    table.on('tool(test)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的DOM对象

        if (layEvent === 'del') { //删除
            layer.confirm('真的删除该用户么', function (index) {
                obj.del(); //删除对应行（tr）的DOM结构，并更新缓存


                //向服务端发送删除指令
                $.ajax({//delete request
                    url: "/user/" + data.userId,
                    type: "DELETE"
                });

                layer.close(index);
            });
        } else if (layEvent === 'edit') { //编辑
            editUser(obj);
        }
    });
}

function editUser(obj) {


    layer.open({
        title: '修改用户',
        content: $("#editUser").html(),
        area: ["600px", "500px"],
        success: function (layero, index) {
            $.ajax({
                url: "authority/all",
                type: "GET",
                dataType: "JSON",
                async: false,
                success: function (data) {
                    for (var i = 0; i < data.length; i++) {
                        var $option = $("<option></option>")
                            .attr('value', data[i].authorityName)
                            .text(data[i].authorityName);
                        $(layero).find("select[name='authority']").append($option);
                    }


                    layui.use('form', editUserForm(obj,index));


                }
            });//ajax end...
        },
        btn: []
    });
}


function editUserForm(obj,openIndex) {

    var trData = obj.data; //获得当前行数据

    var form = layui.form;
    form.render();//渲染
    form.val("editUserForm", {
        "sex": trData.sex,
        "idCard": trData.idCard,
        "phone": trData.phone,
        "authority": trData.authority.authorityName
    });


    //监听提交事件
    form.on('submit(editUser)', function (data) {


        var authorityName = data.field.authority;
        data.field.authority = {
            authorityId: null, authorityName: authorityName
        };
        data.field.userName = trData.userName;

        //向服务端发送修改指令
        $.ajax({//put request
            url: "/user/edit",
            type: "PUT",
            contentType: "application/json",
            data: JSON.stringify(data.field),
            success: function (msg) {

                if ('success' == msg) {

                    data.field.authority = authorityName;
                    //同步更新缓存对应的值
                    obj.update(data.field);

                    layer.close(openIndex);
                }

            }
        });
        return false;
    });
}

/**
 * 添加用户
 */
function addUser() {
    layer.open({
        title: '添加用户',
        content: $("#addUser").html(),
        area: ["600px", "500px"],
        success: function (layero, index) {

            $.ajax({
                url: "authority/all",
                type: "GET",
                dataType: "JSON",
                async: false,
                success: function (data) {
                    for (var i = 0; i < data.length; i++) {
                        var $option = $("<option></option>")
                            .attr('value', data[i].authorityName)
                            .text(data[i].authorityName);
                        $(layero).find("select[name='authority']").append($option);
                    }
                }
            });

            layui.use('form', function () {
                var form = layui.form;

                form.render();//渲染

                //监听提交事件
                form.on('submit(addUser)', function (data) {

                    var authorityName = data.field.authority;
                    data.field.authority = {
                        authorityId: null, authorityName: authorityName
                    };

                    //向服务端发送添加指令
                    $.ajax({//put request
                        url: "/user/add",
                        type: "PUT",
                        contentType: "application/json",
                        data: JSON.stringify(data.field),
                        success: function (data) {

                            if ('existed' == data) {
                                layer.msg("用户已存在");
                            } else if ('success' == data) {
                                layer.close(index);
                                window.location.reload()
                            }

                        }
                    });
                    return false;
                });
            });
        },
        btn: []
    });
}
