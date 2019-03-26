package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.*;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    private RouteDao routeDao = new RouteDaoImpl();
    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage,String rname) {
        int count = routeDao.countByCid(cid,rname);
        PageBean<Route> pb = new PageBean<>();
        //设置页码数据
        pb.setCurrentPage(currentPage);
        pb.setTotalCount(count);

        //总页数
        int totalPage = count % pb.getPageSize() == 0 ? count / pb.getPageSize() : count / pb.getPageSize()+1;
        pb.setTotalPage(totalPage);

        //查询每页的数据
        int start = (currentPage - 1)*pb.getPageSize();
        List<Route> list =  routeDao.findByPage(cid,start,pb.getPageSize(),rname);
        pb.setList(list);

        return pb;
    }

    @Override
    public Route findByRid(int rid) {
        //查找route对象
        RouteDao routeDao = new RouteDaoImpl();
        Route route_result = routeDao.findOne(rid);

        //设置route的seller属性
        SellerDao sellerDao = new SellerDaoImpl();
        Seller seller = sellerDao.findBySid(route_result.getSid());
        route_result.setSeller(seller);

        //设置routeImgList属性
        RouteImgDao routeImgDao = new RouteImgDaoImpl();
        List<RouteImg> byRid = routeImgDao.findByRid(route_result.getRid());
        route_result.setRouteImgList(byRid);

        //设置category属性
        CategoryDao categoryDao = new CategoryDaoImpl();
        Category byCid = categoryDao.findByCid(route_result.getCid());
        route_result.setCategory(byCid);
        //sellerDao.findBySid()
        return route_result;
    }

    @Override
    public void addCount(String srid) {
        int rid = 0;
        if (srid !=null && srid.length()>0 && !"null".equals(rid)){
            rid = Integer.parseInt(srid);
        }
        Route one = routeDao.findOne(rid);

        System.out.println(one.getCount()+1);
        routeDao.addCount(rid,one.getCount()+1);
    }

    @Override
    public List<Route> findHot() {
        return routeDao.findHot();
    }
}
