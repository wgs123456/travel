package cn.itcast.travel.dao;

import cn.itcast.travel.domain.RouteImg;

import java.util.List;

public interface RouteImgDao {
    /**
     * 根据rid查找route的图片集合
     * @param rid
     * @return
     */
    List<RouteImg> findByRid(int rid);
}
