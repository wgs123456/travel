
$(function () {
    //获取携带的cid
   /* var id = location.search;
    var cid = id.split("=")[1];*/
    var cid = getParameter("cid");
    var rname = getParameter("rname");

    //解码
    if (rname != null)
    rname = window.decodeURIComponent(rname);
    //alert(rname);
    //调用load方法加载数据
    load(cid,null,rname);
    /**
     * 加载热门推荐
     */
    $.post("route/findHot",function (data) {
        var lis ="";
        for(var i = 0; i < data.length; i++){
            var route = data[i];
            lis  += ' <li>\n' +
                '                            <div class="left"><img src="'+route.rimage+'" alt=""></div>\n' +
                '                            <div class="right">\n' +
                '                                <a href="route_detail.html?rid='+route.rid+'"><p>'+route.rname+'</p></a>\n' +
                '                                <p>网付价<span>&yen;<span>'+route.price+'</span>起</span>\n' +
                '                                </p>\n' +
                '                            </div>\n' +
                '                        </li>';
        }
        $("#hot_list").html(lis);
    });
});

function load(cid,currentPage,rname) {
    $.post("route/pageQuery",{cid:cid,currentPage:currentPage,rname:rname},function (data) {

        //设置总页数和总记录数
        $("#totalPage").html(data.totalPage);
        $("#totalCount").html(data.totalCount);
        /*
                        <!--<li><a href="">首页</a></li>
                            <li class="threeword"><a href="#">上一页</a></li>
                            &lt;!&ndash;<li><a href="#">1</a></li>&ndash;&gt;
                            <li><a href="#">2</a></li>
                            <li><a href="#">3</a></li>
                            <li><a href="#">4</a></li>
                            <li><a href="#">5</a></li>
                            <li><a href="#">6</a></li>
                            <li><a href="#">7</a></li>
                            <li><a href="#">8</a></li>
                            <li><a href="#">9</a></li>
                            <li><a href="#">10</a></li>
                            <li class="threeword"><a href="javascript:;">下一页</a></li>
                            <li class="threeword"><a href="javascript:;">末页</a></li>&ndash;&gt;-->
        */
        //定义页码数据
        var lis = "";
        var fristPage = '<li onclick="javascipt:load('+cid+',1,\''+rname+'\')"><a href="javascript:void(0)">首页</a></li>';
        //上一页
        var beforeNum =  data.currentPage - 1;
        if (beforeNum<=0){
            beforeNum = 1;
        }
        var beforePage = '<li onclick="javascipt:load('+cid+','+beforeNum+',\''+rname+'\')" class="threeword"><a href="javascript:void(0)">上一页</a></li>';
        //首页功能和上一页拼接
        lis += fristPage;
        lis += beforePage;

        //定义显示页码
        var begin;
        var end;

        if(data.totalPage < 10){
            begin = 1;
            end = data.totalPage;
        }else {
            begin = data.currentPage - 5;
            end = data.currentPage + 4;
            if (begin<1){
                begin = 1;
                end = begin + 9;
            }
            if(end > data.totalPage){
                end = data.totalPage;
                begin = end - 9;
            }
        }


        for(var i = begin; i <= end; i++){
            if ( i == data.currentPage){
                lis += '<li class="curPage" onclick="javascript:load('+cid+','+i+',\''+rname+'\')"><a href="javascript:void(0)">'+i+'</a></li>';
            }else {
                lis += '<li onclick="javascript:load('+cid+','+i+',\''+rname+'\')"><a href="javascript:void(0)">'+i+'</a></li>';
            }
        }

        //定义下一页和末页
        var nextPage = data.currentPage+1;
        if (nextPage > data.totalPage){
            nextPage = data.totalPage;
        }

        lis += '<li id="next_page" class="threeword" onclick="javascript:load('+cid+','+nextPage+',\''+rname+'\')"><a href="javascript:void(0);">下一页</a></li>';
        //lis += '<li class="threeword" onclick="javascript:load('+cid+','+data.totalPage+')"><a href="javascript:;">末页</a></li>&ndash;&gt;';
        lis += '<li class="threeword" onclick="javascript:load('+cid+','+data.totalPage+',\''+rname+'\')"><a href="javascript:void(0);">末页</a></li>';

        //添加页码数据到前台
       // $("#test").after(lis);
        $("#ul_page").html(lis);



        /*详情页
         <li>
                            <div class="img"><img src="images/04-search_03.jpg" alt=""></div>
                            <div class="text1">
                                <p>黑妞皇家旅行普吉岛攀牙湾大船星光之旅皮划艇日落休闲特色体验</p>
                                <br/>
                                <p>1-2月出发，网付立享￥1099/2人起！爆款位置有限，抢完即止！</p>
                            </div>
                            <div class="price">
                                <p class="price_num">
                                    <span>&yen;</span>
                                    <span>999</span>
                                    <span>起</span>
                                </p>
                                <p><a href="route_detail.html">查看详情</a></p>
                            </div>
                        </li>
         */
        //对搜索结果进行展示
        var result = "";
        for(var i = 0; i < data.list.length; i++){
            var list = data.list[i];

            //对每条数据进行拼接
            result +='<li>\n' +
                '                            <div class="img"><img src="'+list.rimage+'" width="299px"></div>\n' +
                '                            <div class="text1">\n' +
                '                                <p>'+list.rname+'</p>\n' +
                '                                <br/>\n' +
                '                                <p>'+list.routeIntroduce+'</p>\n' +
                '                            </div>\n' +
                '                            <div class="price">\n' +
                '                                <p class="price_num">\n' +
                '                                    <span>&yen;</span>\n' +
                '                                    <span>'+list.price+'</span>\n' +
                '                                    <span>起</span>\n' +
                '                                </p>\n' +
                '                                <p><a href="route_detail.html?rid='+list.rid+'">查看详情</a></p>\n' +
                '                            </div>\n' +
                '                        </li>';

        }

        //添加到页面
        $("#detail").html(result);
        window.scrollTo(0,0);

    },"json")
}