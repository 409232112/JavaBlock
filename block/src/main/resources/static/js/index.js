$(function(){
    //创建区块链
    $("#createBlockChain").click(function(){
        $.ajax({
            type: "GET",
            url: "cmd/createBlockChain",
            dataType: "json",
            contentType: 'application/json',
            success: function(data){
                var result = eval(data);
                $.messager.alert({
                    width: 450,
                    height: 160,
                    title: '操作提示',
                    msg: data.message+"<br />"+result.data.address
                });
            },
            error:function(data, XMLHttpRequest, textStatus, errorThrown){
                $.messager.alert("操作提示", data.responseJSON.message,"error");
            }
        });
    });

    //打印区块链
    $("#printChain").click(function(){
        $.ajax({
            type: "GET",
            url: "cmd/printChain",
            dataType: "json",
            contentType: 'application/json',
            success: function(data){
                var result = eval(data);
                $.messager.alert("操作提示", data.message,"info");
            },
            error:function(data, XMLHttpRequest, textStatus, errorThrown){
                $.messager.alert("操作提示", data.responseJSON.message,"error");
            }
        });
    });

    //生成新钱包
    $("#createWallet").click(function(){
        $.ajax({
            type: "GET",
            url: "cmd/createWallet",
            dataType: "json",
            contentType: 'application/json',
            success: function(data){
                var result = eval(data);
                $.messager.alert({
                    width: 450,
                    height: 160,
                    title: '操作提示',
                    msg: data.message+"<br />钱包地址："+result.data.address
                });
            },
            error:function(data, XMLHttpRequest, textStatus, errorThrown){
                $.messager.alert("操作提示", data.responseJSON.message,"error");
            }
        });
    });

    //获取钱包余额
    $("#getBalance").click(function(){
        if($("#balance").val().length==0){
            $.messager.alert("操作提示", "请输入钱包地址！","error");
            return;
        }
        $.ajax({
            type: "GET",
            url: "cmd/getBalance/"+$("#balance").val(),
            dataType: "json",
            contentType: 'application/json',
            success: function(data){
                var result = eval(data);
                $.messager.alert("操作提示", data.message+"<br />余额："+result.data.balance,"info");
            },
            error:function(data, XMLHttpRequest, textStatus, errorThrown){
                $.messager.alert("操作提示", data.responseJSON.message,"error");
            }
        });
    });

    //打印钱包
    $("#printWallet").click(function(){
        $.ajax({
            type: "GET",
            url: "cmd/printWallet",
            dataType: "json",
            contentType: 'application/json',
            success: function(data){
                var result = eval(data);
                $.messager.alert("操作提示", data.message,"info");
            },
            error:function(data, XMLHttpRequest, textStatus, errorThrown){
                $.messager.alert("操作提示", data.responseJSON.message,"error");
            }
        });
    });

    //reindexUTX0
    $("#reindexUTX0").click(function(){
        $.ajax({
            type: "GET",
            url: "cmd/reindexUTX0",
            dataType: "json",
            contentType: 'application/json',
            success: function(data){
                var result = eval(data);
                $.messager.alert("操作提示", data.message,"info");
            },
            error:function(data, XMLHttpRequest, textStatus, errorThrown){
                $.messager.alert("操作提示", data.responseJSON.message,"error");
            }
        });
    });

    //发币
    $("#send").click(function(){
        $.ajax({
            type: "POST",
            url: "cmd/send",
            data: JSON.stringify({from:$("#from").val(), to:$("#to").val(),amount:$("#amount").val(),mineNow:$("input[name='mineNow']:checked").val()}),
            dataType: "json",
            contentType: 'application/json',
            success: function(data){
                var result = eval(data);
                $.messager.alert("操作提示", data.message,"info");
            },
            error:function(data, XMLHttpRequest, textStatus, errorThrown){
                $.messager.alert("操作提示", data.responseJSON.message,"error");
            }
        });
    });
})




