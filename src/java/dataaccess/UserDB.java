/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import models.Role;
import models.User;

/**
 *
 * @author pusit
 */
public class UserDB {

//get all user
    public List<User> getAll() throws Exception {
        List<User> users = new ArrayList<>();
        //retrieve the connection pool-using singleton pattern(cannot call a constructor directly
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT email, first_name, last_name, password, role_id, role_name FROM user, role WHERE role_id = role";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String email = rs.getString(1);
                String first = rs.getString(2);
                String last = rs.getString(3);
                String password = rs.getString(4);
                int id = rs.getInt(5);
                String name = rs.getString(6);
                Role r = new Role(id, name);
                User user = new User(email, first, last, password, r);
                users.add(user);
            }
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }

        return users;
    }
//get one particular user for specific email

    public User get(String email) throws Exception {
        User user = null;
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT first_name, last_name, password, role_id, role_name FROM user u, role r WHERE email=? and r.role_id= u.role";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                String first = rs.getString(1);
                String last = rs.getString(2);
                String pw = rs.getString(3);
                int id = rs.getInt(4); 
                String name = rs.getString(5);
                Role r = new Role(id, name);
                user = new User(email, first, last, pw, r);
             
            }
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
        return user;
    }

    
//insert  one user
//pass the whole user object
    public void insert(User user) throws Exception {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        String sql = "INSERT INTO user(email, first_name, last_name, password, role) VALUES (?, ?, ?, ?, ?)";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getLastName());
            ps.setString(4, user.getPassword());
            ps.setInt(5, user.getRole().getId());

            ps.executeUpdate();
        } finally {
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
    }

    public void update(User user) throws Exception {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        String sql = "UPDATE user SET first_name=?, last_name=?, password =?, role=? WHERE email=?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getPassword());
            ps.setInt(4, user.getRole().getId());
            ps.setString(5, user.getEmail());
            ps.executeUpdate();
        } finally {
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
    }

    public void delete(User user) throws Exception {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        String sql = "DELETE FROM user WHERE email=?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, user.getEmail());
            ps.executeUpdate();
        } finally {
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
    }

}