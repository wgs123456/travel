package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import cn.itcast.travel.util.Md5Util;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao{
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public User selectByName(String username) {
        User user = null;
        try {
            String sql = "select * from tab_user where username = ?";
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        }catch (Exception e){
        }

        return user;
    }

    @Override
    public void save(User user) {
        String sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code) values(?,?,?,?,?,?,?,?,?)";
        try {
            jdbcTemplate.update(sql,
                    user.getUsername(),
                    Md5Util.encodeByMd5(user.getPassword()),
                    user.getName(),
                    user.getBirthday(),
                    user.getSex(),
                    user.getTelephone(),
                    user.getEmail(),
                    user.getStatus(),
                    user.getCode());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public User checkLogin(String username,String password) {
        String sql = "select * from tab_user where username = ? and password = ?";
        User user1 = null;
        try {
            user1 = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username, Md5Util.encodeByMd5(password));
        } catch (Exception e) {
        }
        return user1;
    }

    @Override
    public User findByCode(String code) {
        User user =null;
        try {
            String sql = "select * from tab_user where code = ?";
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), code);
        } catch (DataAccessException e) {
            return null;
        }
        return user;
    }

    @Override
    public void setStatus(User user) {
        String sql = "update tab_user set status = ? where uid=?";
        jdbcTemplate.update(sql,"Y",user.getUid());
    }
}
