package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class UserServiceImpl implements UserService{
    private UserDao userDao = new UserDaoImpl();
    @Override
    public Boolean regist(User user) {
        //userDao.regist
        User user1 = userDao.selectByName(user.getUsername());
        if (user1!=null){
            //数据库中有该用户名
            return false;
        }
        user.setStatus("N");
        user.setCode(UuidUtil.getUuid());
        userDao.save(user);
        //发送激活邮件
        //发送邮件
        String content = "<a href = 'localhost:80/user/active?code="+user.getCode()+"'>点击激活</a>";
        try {
            MailUtils.sendMail(user.getEmail(),content, "用户激活");
        } catch (Exception e) {
            System.out.println("发送邮件失败");
        }
        //MailUtils.sendMail("")
        return true;
    }

    @Override
    public User checkLogin(String username,String password) {
        User user1 = userDao.checkLogin(username,password);
        return user1;
    }

    @Override
    public boolean activeUser(String code) {
        User user = userDao.findByCode(code);
        if (user!=null){
            userDao.setStatus(user);
            return true;
        }
        return false;
    }
}
