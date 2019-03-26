package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.*;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

//@javax.servlet.annotation.WebServlet(name = "RouteServlet")
@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private RouteService routeService = new RouteServiceImpl();
    /**
     *分页查询
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接收数据并处理
        String u_cid = request.getParameter("cid");
        String u_currentPage = request.getParameter("currentPage");
        String rname = request.getParameter("rname");
        //rname = new String(rname.getBytes("iso-8859-1"),"utf-8");

        int cid  = 0;
        int currentPage;
        if (u_cid != null && u_cid.length()>0 && !"null".equals(u_cid)/*!u_cid.equals("null")*/){
            cid = Integer.parseInt(u_cid);
        }

        //设置currentPage  默认为1
        if (u_currentPage != null && u_currentPage.length()>0){
            currentPage = Integer.parseInt(u_currentPage);
        }else {
            currentPage = 1;
        }
       // System.out.println("cid: "+u_cid + "currentPage: "+currentPage+"rname: "+rname);

        //调用service
       // RouteService routeService = new RouteServiceImpl();
        PageBean<Route> list = routeService.pageQuery(cid,currentPage,rname);
        //System.out.println(list);

        // json 格式： {"totalCount":513,"totalPage":52,"currentPage":1,"pageSize":10,"list":[{"rid":1,"rname":"}{}]}
        writeValue(list,response);

    }

    /**
     * 根据rid查询详情页数据
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String u_rid = request.getParameter("rid");
        //int rid = Integer.parseInt(u_rid);
        int rid = 1;
        if (u_rid != null && u_rid.length()>0){
           rid = Integer.parseInt(u_rid);
        }

        //调用service
     //   RouteService routeService = new RouteServiceImpl();
        Route route = routeService.findByRid(rid);
        writeValue(route,response);
        //System.out.println(route);
    }

    /**
     * 查询是否已收藏
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取线路id
        String u_rid = request.getParameter("rid");

        //获取登陆用户uid,如果未登录，设置uid为0
        User user = (User) request.getSession().getAttribute("user");
        int uid;
        if (user == null){
            uid = 0;
        }else {
            uid = user.getUid();
        }

        int rid = 0 ;
        if (u_rid != null && u_rid.length()>0){
            rid = Integer.parseInt(u_rid);
        }

       // System.out.println(u_rid +" : " + uid);
        FavoriteService favoriteService = new FavoriteServiceImpl();
        boolean result = favoriteService.findByRidAndUid(rid, uid);
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setFlag(result);
        writeValue(resultInfo,response);
    }

    /**
     * 判断是否登陆
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void isLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        ResultInfo resultInfo = new ResultInfo();
        if (user != null){
            resultInfo.setFlag(true);
        }else {
            resultInfo.setFlag(false);
        }
        writeValue(resultInfo,response);
    }

    /**
     * 执行收藏操作
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void cliclkCollect(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid = request.getParameter("rid");
        User user = (User) request.getSession().getAttribute("user");
        int uid = user.getUid();
        FavoriteService favoriteService = new FavoriteServiceImpl();
        favoriteService.addFavorite(rid,uid);
       // RouteService routeService = new RouteServiceImpl();
        routeService.addCount(rid);
        System.out.println("count字段同步成功");

    }

    /**
     * 查找热门旅游线路信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void findHot(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Route> hot = routeService.findHot();
        writeValue(hot,response);
    }
}
