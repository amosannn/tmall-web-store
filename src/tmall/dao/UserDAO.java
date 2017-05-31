package tmall.dao;


import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.User;
import tmall.util.DBUtil;
 
public class UserDAO {
	public static final String active = "active";
	public static final String delete =  "delete";
	
    public int getTotal() {
        int total = 0;
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {
 
            String sql = "select count(*) from User";
 
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
 
            e.printStackTrace();
        }
        return total;
    }
 
    public void add(User bean) {
 
        String sql = "insert into user values(null, ?, ?, default)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
 
            ps.setString(1, bean.getName());
            ps.setString(2, bean.getPassword());
 
            ps.execute();
 
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                bean.setId(id);
            }
        } catch (SQLException e) {
 
            e.printStackTrace();
        }
    }
 
    public void update(User bean) {
 
        String sql = "update user set name= ?, password = ?, status = ? where id = ? ";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
 
            ps.setString(1, bean.getName());
            ps.setString(2, bean.getPassword());
            ps.setString(3, bean.getStatus());
            ps.setInt(4, bean.getId());
 
            ps.execute();
 
        } catch (SQLException e) {
 
            e.printStackTrace();
        }
 
    }
 
    public void delete(int id) {
 
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {
 
            String sql = "delete from User where id = " + id;
 
            s.execute(sql);
 
        } catch (SQLException e) {
 
            e.printStackTrace();
        }
    }
 
    public User get(int id) {
        User bean = null;
 
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {
 
            String sql = "select * from User where id = " + id;
 
            ResultSet rs = s.executeQuery(sql);
 
            if (rs.next()) {
                bean = new User();
                String name = rs.getString("name");
                bean.setName(name);
                String password = rs.getString("password");
                bean.setPassword(password);
                bean.setId(id);
            }
 
        } catch (SQLException e) {
 
            e.printStackTrace();
        }
        return bean;
    }
 
    /**
     * 用户普通集合（包含所有状态，active，delete等）
     * @return
     */
    public List<User> list() {
        return list(0, Short.MAX_VALUE);
    }
 
    /**
     * 用户普通分页集合（包含所有状态，active，delete等）
     * @param start
     * @param count
     * @return
     */
    public List<User> list(int start, int count) {
        List<User> beans = new ArrayList<User>();
 
        String sql = "select * from User order by id desc limit ?,? ";
 
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
 
            ps.setInt(1, start);
            ps.setInt(2, count);
 
            ResultSet rs = ps.executeQuery();
 
            while (rs.next()) {
                User bean = new User();
                int id = rs.getInt(1);

                String name = rs.getString("name");
                bean.setName(name);
                String password = rs.getString("password");
                bean.setPassword(password);
                
                bean.setId(id);
                beans.add(bean);
            }
        } catch (SQLException e) {
 
            e.printStackTrace();
        }
        return beans;
    }

    /**
     * 用户集合（除了标记已删除的用户）
     * @param excludedStatus
     * @return
     */
    public List<User> list(String excludedStatus) {
        return list(excludedStatus, 0, Short.MAX_VALUE);
    }
    
    /**
     * 用户分页集合（除了标记已删除的用户）
     * @param excludedStatus
     * @param start
     * @param count
     * @return
     */
    public List<User> list(String excludedStatus, int start, int count){
    	List<User> beans = new ArrayList<User>();
    	String sql = "select * from user where status != ? order by id desc limit ?,?";
    	
    	try(Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)){
    		ps.setString(1, excludedStatus);
    		ps.setInt(2, start);
    		ps.setInt(3, count);
    		
    		ResultSet rs = ps.executeQuery();
    		
    		while(rs.next()){
    			User bean = new User();
                int id = rs.getInt(1);

                String name = rs.getString("name");
                bean.setName(name);
                String password = rs.getString("password");
                bean.setPassword(password);
                String status = rs.getString("status");
                bean.setStatus(status);
                
                bean.setId(id);
                beans.add(bean);
    		}
    		
    	}catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return beans;
    }
    
	public boolean isExist(String name) {
		User user = get(name);
		return user!=null;

	}

	public User get(String name) {
		User bean = null;
		 
		String sql = "select * from User where name = ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
        	ps.setString(1, name);
            ResultSet rs =ps.executeQuery();
 
            if (rs.next()) {
                bean = new User();
                int id = rs.getInt("id");
                bean.setName(name);
                String password = rs.getString("password");
                bean.setPassword(password);
                bean.setId(id);
            }
 
        } catch (SQLException e) {
 
            e.printStackTrace();
        }
        return bean;
	}

	public User get(String name, String password) {
		User bean = null;
		 
		String sql = "select * from User where name = ? and password=?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
        	ps.setString(1, name);
        	ps.setString(2, password);
            ResultSet rs =ps.executeQuery();
 
            if (rs.next()) {
                bean = new User();
                int id = rs.getInt("id");
                bean.setName(name);
                bean.setPassword(password);
                bean.setId(id);
            }
 
        } catch (SQLException e) {
 
            e.printStackTrace();
        }
        return bean;
	}
 
}