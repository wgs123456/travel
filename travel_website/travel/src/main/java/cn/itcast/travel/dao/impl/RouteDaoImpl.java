package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 根据cid查询总记录数
     * @param cid
     * @param rname
     * @return
     */
    @Override
    public int countByCid(int cid,String rname) {
        String sql = "select count(*) from tab_route where 1 = 1";
        StringBuilder sb = new StringBuilder(sql);   //动态定义sql
        //System.out.println(cid);
        List params = new ArrayList();    //定义条件
        if (cid != 0){
            sb.append(" and cid = ?");
            params.add(cid);
        }

        if (rname != null && !rname.equals("null") && rname.length()>0){
            sb.append(" and rname like ?");
            params.add("%"+rname+"%");
        }
        sql = sb.toString();
        /*System.out.println(sql);
        System.out.println(params.toArray());*/
        return template.queryForObject(sql,Integer.class,params.toArray());
    }

    /**
     * 查找每页数据
     * @param cid
     * @param start
     * @param pageSize
     * @param rname
     * @return
     */
    @Override
    public List<Route> findByPage(int cid, int start, Integer pageSize,String rname) {

        //String sql = "select * from tab_route where cid = ? limit ?,?";
        String sql = "select * from tab_route where 1 = 1";
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList();

        if (cid !=0){
            sb.append(" and cid = ?");
            params.add(cid);
        }

        if (rname != null && !rname.equals("null")  && rname.length()>0){
            sb.append(" and rname like ?");
            params.add("%"+rname+"%");
        }

        sb.append(" limit ? , ?");
        sql = sb.toString();

        params.add(start);
        params.add(pageSize);

       // System.out.println(sql);

        List<Route> list = template.query(sql, new BeanPropertyRowMapper<Route>(Route.class),params.toArray());
       // System.out.println(list);
        // return null;
        return list;
    }

    /**
     * 根据rid查找Route对象
     * @param rid
     * @return
     */
    @Override
    public Route findOne(int rid) {
        String sql = "select * from tab_route where rid = ?";
        Route route ;
        try {
            route = template.queryForObject(sql,new BeanPropertyRowMapper<Route>(Route.class),rid);
        }catch (Exception e){
            return null;
        }
        return route;
    }

    /**
     * 修改Route表中收藏次数字段
     * @param rid
     */
    @Override
    public void addCount(int rid,int count) {
        String sql = "update tab_route set count = ? where rid = ?";
        template.update(sql,count,rid);
    }

    @Override
    public List<Route> findHot() {
        String sql = "SELECT * FROM tab_route ORDER BY COUNT DESC LIMIT 0 , 5";
        return template.query(sql,new BeanPropertyRowMapper<Route>(Route.class));
    }
}
