$(document).ready(function() {
    //alert(getParameter("rid"));
    $.post("route/findOne",{rid:getParameter("rid")},function (data) {
        //对查询数据据进行展示
        $(".queren").html(data.rname+"二次确认");
        $(".hot").html(data.routeIntroduce);
        $("#sname").html(data.seller.sname);
        $("#consphone").html(data.seller.consphone);
        $("#address").html(data.seller.address);
        $("#price").html("¥"+data.price+".00");
        $("#big_img").attr("src",data.rimage);
        $("#category").html(data.category.cname);
        $("#category").attr("href","route_list.html?cid="+data.cid);

       // $("#favoriteCount").html("已收藏"+data.count+"次");
        $("#count_coll").html("已收藏"+data.count+"次");

        //对图片进行处理
        var img_list = '<a class="up_img up_img_disable"></a>';
       /* for(var i = 0; i < data.routeImgList.length; i++){
            var img = data.routeImgList[i];
            img_list += '<a title="" class="little_img cur_img" data-bigpic="'+img.bigPic+'"><img src="'+img.smallPic+'"></a>';
        }*/
       for(var i = 0; i < data.routeImgList.length; i++){
           if (i>=4){
               var img = data.routeImgList[i];
               img_list += '<a title="" class="little_img" style="display: none" data-bigpic="'+img.bigPic+'">\n' +
                   '                        <img src="'+img.smallPic+'">\n' +
                   '                    </a>';
           }else {
               var img = data.routeImgList[i];
               img_list += '<a title="" class="little_img" data-bigpic="'+img.bigPic+'">\n' +
                   '                        <img src="'+img.smallPic+'">\n' +
                   '                    </a>';
           }

       }
        img_list += '<a class="down_img down_img_disable" style="margin-bottom: 0;"></a>';

        $("#img_left").html(img_list);

       // auto_play();
        goImg();

    },"json");

    /**
     * 查询用户是否收藏
     */
    $.post("route/isFavorite",{rid:getParameter("rid")},function (data) {
        if (data.flag){
            //已经收藏
            $("#favorite").addClass("already");
            $("#favorite").attr("disabled","disabled");
            $("#msg_coll").html("已收藏");

            $("#favorite").removeAttr("onclick");
        }else {
            $("#msg_coll").html("点击收藏");
            //没有收藏
        }
    },"json");


    //自动播放
    // var timer = setInterval("auto_play()", 3000);
});


function addFavorite() {
    $.post("route/isLogin",function (data) {
        //判断是否登陆
        if(data.flag){
            //登陆了
            $.post("route/cliclkCollect",{rid:getParameter("rid")},function () {
                location.reload();
            })
        }else {
            if (confirm("您还未登陆，现在是否去登陆")){
                location.href="login.html";
            }else {

            }

        }
    },"json");
}

function goImg(){
    //焦点图效果
    //点击图片切换图片
    $('.little_img').on('mousemove', function() {
        $('.little_img').removeClass('cur_img');
        var big_pic = $(this).data('bigpic');
        $('.big_img').attr('src', big_pic);
        $(this).addClass('cur_img');
    });
    //上下切换
    var picindex = 0;
    var nextindex = 4;
    $('.down_img').on('click',function(){
        var num = $('.little_img').length;
        if((nextindex + 1) <= num){
            $('.little_img:eq('+picindex+')').hide();
            $('.little_img:eq('+nextindex+')').show();
            picindex = picindex + 1;
            nextindex = nextindex + 1;
        }
    });
    $('.up_img').on('click',function(){
        var num = $('.little_img').length;
        if(picindex > 0){
            $('.little_img:eq('+(nextindex-1)+')').hide();
            $('.little_img:eq('+(picindex-1)+')').show();
            picindex = picindex - 1;
            nextindex = nextindex - 1;
        }
    });
}

//自动轮播方法
function auto_play() {
    var cur_index = $('.prosum_left dd').find('a.cur_img').index();
    cur_index = cur_index - 1;
    var num = $('.little_img').length;
    var max_index = 3;
    if ((num - 1) < 3) {
        max_index = num - 1;
    }
    if (cur_index < max_index) {
        var next_index = cur_index + 1;
        var big_pic = $('.little_img:eq(' + next_index + ')').data('bigpic');
        $('.little_img').removeClass('cur_img');
        $('.little_img:eq(' + next_index + ')').addClass('cur_img');
        $('.big_img').attr('src', big_pic);
    } else {
        var big_pic = $('.little_img:eq(0)').data('bigpic');
        $('.little_img').removeClass('cur_img');
        $('.little_img:eq(0)').addClass('cur_img');
        $('.big_img').attr('src', big_pic);
    }
}