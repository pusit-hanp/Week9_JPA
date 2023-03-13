/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dataaccess.UserDB;
import java.util.List;
import models.Role;
import models.User;

/**
 *
 * @author pusit
 */
public class UserService {

    public User get(String email) throws Exception {
        //constructs userDB and get me that user based on email
        UserDB userDB = new UserDB();
        User user = userDB.get(email);
        return user;
    }

    public List<User> getAll() throws Exception {
        //get all users
        UserDB userDB = new UserDB();
        List<User> users = userDB.getAll();
        return users;
    }

    public void insert(String email, String firstName, String lastName, String password, Role role) throws Exception {
        User user = new User(email, firstName, lastName, password, role);
        UserDB userDB = new UserDB();
        userDB.insert(user);
    }

    //param from the servlet
    public void update(String email, String firstName, String lastName, String password, Role role) throws Exception {
        User user = new User(email, firstName, lastName, password, role);
        UserDB userDB = new UserDB();
        userDB.update(user);
    }

    public void delete(String email) throws Exception {
        User user = new User();
        user.setEmail(email);
        UserDB userDB = new UserDB();
        userDB.delete(user);
    }
}
