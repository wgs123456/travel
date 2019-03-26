package cn.itcast.travel.service;

public interface FavoriteService {
    /**
     * 根据用户uid和线路rid查找是否已经收藏
     * @param rid
     * @param uid
     * @return
     */
    boolean findByRidAndUid(int rid,int uid);

    void addFavorite(String rid,int uid);
}
