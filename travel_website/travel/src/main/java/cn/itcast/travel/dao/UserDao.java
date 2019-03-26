package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {
    /**
     * 根据name查是否存在该用户
     * @param username
     * @return
     */
    User selectByName(String username);

    /**
     * 保存注册信息
     * @param user
     */
    void save(User user);

    /**
     * 登陆校验
     * @param username
     * @param password
     * @return
     */
    User checkLogin(String username,String password);

    /**
     * 根据激活码查询用户
     * @return
     */
    User findByCode(String code);

    /**
     * 设置激活状态
     */
    void setStatus(User user);
}
