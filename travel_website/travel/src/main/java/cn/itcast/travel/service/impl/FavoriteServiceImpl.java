package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.service.FavoriteService;

public class FavoriteServiceImpl implements FavoriteService{
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();
    @Override
    public boolean findByRidAndUid(int rid,int uid) {
        //return favoriteDao.findByRidAndUid(rid,uid);
        return favoriteDao.findByRidAndUid(rid,uid) != null;
    }

    @Override
    public void addFavorite(String rid, int uid) {
        int roid = 0;
        if (rid != null && rid.length()>0){
            roid = Integer.parseInt(rid);
        }
        favoriteDao.addFavorite(roid,uid);
    }
}
