package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService{
    private CategoryDao categoryDao = new CategoryDaoImpl();
    @Override
    public String findAll() {
        Jedis jedis = JedisUtil.getJedis();
        //查询cname
        //Set<String> cname = jedis.zrange("cname", 0, -1);
        //查询cid和cname
        Set<Tuple> cname = jedis.zrangeWithScores("cname", 0, -1);
        ObjectMapper mapper = new ObjectMapper();
        List<Category> categoryList=null;
        String str = "";
        if (cname.size() == 0){
            //缓存中没有数据，从数据库读取
            System.out.println("缓存中没有数据，从数据库读取");
            categoryList = categoryDao.findAll();

            //存入缓存
            for (int i = 0; i < categoryList.size(); i++) {
                jedis.zadd("cname",categoryList.get(i).getCid(),categoryList.get(i).getCname());
            }
            cname = jedis.zrangeWithScores("cname", 0, -1);
            categoryList = new ArrayList<Category>();
            for (Tuple tuple : cname) {
                Category category = new Category();
                category.setCname(tuple.getElement());
                category.setCid((int)tuple.getScore());
                categoryList.add(category);
            }

            try {
                str = mapper.writeValueAsString(categoryList);
            } catch (Exception e) {
            }
        }else{
            //缓存中有数据，直接读取
            System.out.println("缓存中有数据");

            //将cname转为list集合
            categoryList = new ArrayList<Category>();
            for (Tuple tuple : cname) {
                Category category = new Category();
                category.setCname(tuple.getElement());
                category.setCid((int)tuple.getScore());
                categoryList.add(category);
            }
            try {
                str =  mapper.writeValueAsString(categoryList);
            } catch (JsonProcessingException e) {
            }
        }
        return str;
    }
}
