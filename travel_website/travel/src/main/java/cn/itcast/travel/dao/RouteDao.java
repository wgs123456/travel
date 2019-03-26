package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteDao {
    /**
     * 查询总记录数
     * @param cid
     * @param rname
     * @return
     */
    int countByCid(int cid,String rname);

    /**
     * 查询分页信息
     * @param cid
     * @param start
     * @param pageSize
     * @param rname
     * @return
     */
    List<Route> findByPage(int cid, int start, Integer pageSize,String rname);

    /**
     * 根据rid查找对象,设置route的seller，routeImgList属性
     * @param rid
     * @return
     */
    Route findOne(int rid);


    /**
     * 修改路线的收藏次数
     * @param rid
     * @param count
     */
    void addCount(int rid,int count);

    /**
     * 根据count排序，查询前五条数据
     * @return
     */
    List<Route> findHot();
}
