package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;

public interface FavoriteDao {
    /**
     * 查找表favorite 查询用户是否收藏路线
     * @param rid
     * @param uid
     * @return
     */
    Favorite findByRidAndUid(int rid, int uid);

    /**
     * 添加收藏记录
     * @param roid
     * @param uid
     */
    void addFavorite(int roid, int uid);
}
