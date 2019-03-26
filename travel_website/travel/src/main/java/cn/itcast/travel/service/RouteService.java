package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteService {
    /**
     * 查询每页数据
     * @param cid
     * @param currentPage
     * @param rname
     * @return
     */
    PageBean<Route> pageQuery(int cid, int currentPage,String rname);

    /**
     * 根据rid查询线路对象
     * @param rid
     * @return
     */

    Route findByRid(int rid);

    /**
     * 对收藏count字段进行+1
     * @param rid
     */
    void addCount(String rid);

    /**
     * 查询热门路线
     * @return
     */
    List<Route> findHot();
}
