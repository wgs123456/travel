package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CategoryDaoImpl implements CategoryDao{
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public List<Category> findAll() {
        List<Category> cate =null;
        try {
            String sql = "select * from tab_category";
            cate = template.query(sql, new BeanPropertyRowMapper<Category>(Category.class));
        }catch (Exception e){
            return null;
        }

        return cate;
    }

    @Override
    public Category findByCid(int cid) {
        String sql = "select * from tab_category where cid = ?";
        return template.queryForObject(sql,new BeanPropertyRowMapper<Category>(Category.class),cid);
    }
}
