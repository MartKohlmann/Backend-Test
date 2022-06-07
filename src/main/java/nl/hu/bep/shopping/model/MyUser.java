package nl.hu.bep.shopping.model;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyUser implements Principal {
    private String username;
    private String userpassword;
    private String role;
    private static List<MyUser> users = new ArrayList<>();

    public MyUser(String username, String userpassword, String role){
        this.username = username;
        this.userpassword = userpassword;
        this.role = role;
        this.users.add(this);
    }
    public static void addUser(MyUser user){
        if (!users.contains(user)){
            users.add(user);
        }
    }

    public String getName() {
        return username;
    }

    public static String validateLogin(String name, String password) {
        for (MyUser user : users){
            if (Objects.equals(user.username, name) && Objects.equals(user.userpassword, password)){
                return user.role;
            }
        }
        return null;
    }
    public List<MyUser> getAllUsers(){
        return users;
    }

    public String getRole(){
        return role;
    }
    public static MyUser getUserByName(String name){
        for (MyUser user : users){
            if (user.username.equals(name)){
                return user;
            }
        }
        return null;
    }
}
