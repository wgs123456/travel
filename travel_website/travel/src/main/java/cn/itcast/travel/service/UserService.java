package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

public interface UserService {
    /**
     * 注册用户
     * @param user
     * @return
     */
    Boolean regist(User user);

    /**
     * 登陆校验
     * @param username
     * @param password
     * @return
     */
    User checkLogin(String username,String password);

    /**
     *用户进行邮箱激活
     * @return
     */
    boolean activeUser(String code);
}
