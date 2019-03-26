package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

//@javax.servlet.annotation.WebServlet(name = "UserServlet")
@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    private User user = new User();
    private UserService userService = new UserServiceImpl();
    /**
     *注册用户
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        ResultInfo result = new ResultInfo();
        //验证码校验
        String check = request.getParameter("check");
        String checkcode_server = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        request.getSession().removeAttribute("CHECKCODE_SERVER");
        if (checkcode_server==null || !checkcode_server.equalsIgnoreCase(check)){

            result.setFlag(false);
            result.setErrorMsg("验证码错误");
            writeValue(result,response);
//            String str = mapper.writeValueAsString(result);
//            response.getWriter().write(str);
            return;
        }

        //获取数据
        Map<String, String[]> map = request.getParameterMap();
        //封装对象
       // User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //调用service执行注册
       // UserService userService = new UserServiceImpl();
        Boolean flag = userService.regist(user);

        //返回结果信息使用ResultInfo类封装
        if (flag){
            //保存成功
            result.setFlag(true);
        }else {
            //保存失败
            result.setFlag(false);
            result.setErrorMsg("用户名已存在，注册失败..");
        }

        writeValue(result,response);
//        String res = mapper.writeValueAsString(result);
//        response.getWriter().write(res);
    }

    /**
     * 激活邮件
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
       // UserService userService = new UserServiceImpl();
        boolean flag = userService.activeUser(code);
        if (flag){
            //激活成功
            response.getWriter().write("激活成功，<a href='/login.html'><font color='blue'>点我</font></a>登陆");
            System.out.println("激活成功");
            //response.sendRedirect("/login.html");
        }else {
            //激活失败
            response.getWriter().write("激活失败,请联系管理员");
            System.out.println("激活失败");
        }
    }

    /**
     * 登陆账户
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取前台提交数据
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String check = request.getParameter("check");
        System.out.println(check);
        String checkcode_server = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        //销毁CHECKCODE_SERVER使其只能使用一次
        request.getSession().removeAttribute("CHECKCODE_SERVER");
        ResultInfo result = new ResultInfo();

        if (checkcode_server != null && !checkcode_server.equalsIgnoreCase(check)){
           result.setFlag(false);
           result.setErrorMsg("验证码错误，请重新输入");
           writeValue(result,response);
           return;
        }


        //查询数据
       // UserService userService = new UserServiceImpl();
        User user = userService.checkLogin(username, password);
        // System.out.println(user);
        if (user != null ){
            // System.out.println(user.getStatus());
            if (user.getStatus().equals("N")){
                //账户未激活，无法登陆
                result.setFlag(false);
                result.setErrorMsg("登陆失败,该账户还未激活，请先激活");
            }else {
                //登陆成功
                result.setFlag(true);
                request.getSession().setAttribute("user",user);

            }

        }else {
            result.setFlag(false);
            result.setErrorMsg("登陆失败,用户名或密码错误");
        }
        //设置响应json

        writeValue(result,response);
//        response.setContentType("application/json;charset=utf-8");
//        ObjectMapper mapper = new ObjectMapper();
//        String re = mapper.writeValueAsString(result);
//        response.getWriter().write(re);
    }

    /**
     * 退出账号
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        //System.out.println("exit方法被访问");
        response.sendRedirect(request.getContextPath()+"/index.html");
    }

    /**
     * 用户欢迎提示
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void welcome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        writeValue(user,response);
//        ObjectMapper mapper = new ObjectMapper();
//        response.setContentType("application/json;charset=utf-8");
//        mapper.writeValue(response.getOutputStream(),user);
    }
}
