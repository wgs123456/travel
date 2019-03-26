package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.service.impl.CategoryServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

//@javax.servlet.annotation.WebServlet(name = "CategoryServlet")
@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {
    private CategoryService categoryService = new CategoryServiceImpl();

    /**
     * 查询所有信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String str =  categoryService.findAll();

        response.setContentType("application/json;charset=utf-8");
       // System.out.println(str);
        response.getWriter().write(str);
    }

    /**
     * 未定义方法
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void ssss(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
