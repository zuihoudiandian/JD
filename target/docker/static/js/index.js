toastr.options = {
    "closeButton": false,
    "debug": false,
    "positionClass": "toast-top-right",
    "onclick": null,
    "showDuration": "300",
    "hideDuration": "1000",
    "timeOut": "3000",
    "extendedTimeOut": "1000",
    "showEasing": "swing",
    "hideEasing": "linear",
    "showMethod": "fadeIn",
    "hideMethod": "fadeOut"
};
    $("#btn").click(function () {
        var url = $("#url").val();
        $.ajax({
            type: "post",
            url: "/query",
            data: {"url": url},
            success: function (response) {
                if (response.code == 200) {
                    $("#la").show();
                    $("#la").text(response.data.minPrice+"   "+response.data.nowPrice);
                    $("#title").text(response.data.title);
                     toastr.success(response.message);
                } else {
                     toastr.error(response.message);
                }
            }
        });
    });


$("#save").click(function () {
        var form = $("#form").serializeJSON();
        console.log("123123");
        console.log(form);
        $.ajax({
            type: "post",
            url: "/save",
            data: JSON.stringify(form),
            contentType:"application/json;charset=UTF-8",
            dataType: 'json',
            success: function (response) {
                if (response.code===200) {
                    toastr.success(response.message);
                } else {
                    toastr.error(response.message);}
            }
        })
});


$("#query").click(function (){
    const wxId = $("#queryID").val();
    $('#table').bootstrapTable({
        idField: "id",
        pagination: true,
        showRefresh: true,
        cache: false,
        sidePagination:'server',
        pageList: [5, 10, 15],
        clickToSelect: true,
        pageNumber: 1,                       //初始化加载第一页，默认第一页
        pageSize: 5,
        queryParams: function queryParams(params) {
            var resultParams = {
                "pageSize": params.limit,
                "pageNumber": (params.offset / params.limit) + 1,
                "wxId":wxId
            };
            return resultParams;
        },
        url: '/getGoods',        // 表格数据来源
        method: 'get',
        columns: [{
            field: 'id',
            title: 'id'
        },{
            field: 'title',
            title: '商品'
        }, {
            field: 'minPrice',
            title: '价格'
        },
        {
            field: 'nowPrice',
            title: '当前价格'
        },
            {
                field: 'goodsUrl',
                title: '商品链接',
                align : 'center',
                hidden:true,
                valign: 'middle',
                formatter : query
            },
            {
                title : '操作',
                field : 'id',
                align : 'center',
                formatter : delquestion
            }
            ],
        responseHandler: function (res) {
            console.log("123123123");//后台返回的结果
            if (res.code === 200) {
               console.log(res.data.TOTAL);//后台返回的结果
                console.log(res.data.ROWS);//后台返回的结果
                var data = {
                    total: res.data.TOTAL,
                    rows: res.data.ROWS,
                };
                return data;
            }
        }
    });
});

function delquestion(value, row, index) {
    var str=[];
    str.push("<button  class='btn btn-outline-secondary btn-sm mr-1' onclick='delquesionById(\"" + row.id+ "\")'>退订</button>");
    return str;
}
function query(value, row, index) {
    var str=[];
    str.push("<button  class='btn btn-outline-secondary btn-sm mr-1' onclick='joinrl(\"" + row.goodsUrl+ "\")'>跳转</button>");
    return str;
}
function joinrl(url) {
    window.open (url);
}

function delquesionById(id) {
    $.ajax({
        url: "/del",
        type: "POST",
        data: {"id": id},
        success: function (data) {
            if (data.code == 200) {
                toastr.success(data.message);
                refreshTable();
            } else {
                toastr.error(data.message);
            }
        },
        error: function (data) {
            toastr.error("请求失败");
        }
    });
}
function refreshTable() {
    $("#table").bootstrapTable('refresh');
}
