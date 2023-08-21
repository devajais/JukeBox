package com.crio.jukebox.repositories;

import java.util.HashMap;
import java.util.Map;
import com.crio.jukebox.entities.User;

public class UserRepository implements IUserRepository{
    private final Map<Integer, User> userMap;
    private Integer autoIncrement;
    private static UserRepository instance;

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    public UserRepository() {
        userMap = new HashMap<Integer, User>();
        autoIncrement = 0;
    }

    public UserRepository(Map<Integer, User> userMap) {
        this.userMap = userMap;
        this.autoIncrement = userMap.size();
    }

    public int getSize(){
        return userMap.size();
    }
    public String getUserById(int id){
        return userMap.get(id).getName();
    }

    @Override
    public User save(User entity) {
    //    if (entity.getId() == 0) {
            autoIncrement++;
            User u = new User(autoIncrement, entity.getName());
            userMap.put(u.getId(), u);
           // System.out.println(userMap.get(u.getId()).getName());
            return u;
        // }
        // userMap.put(entity.getId(), entity);
        // return entity;
    }
}
