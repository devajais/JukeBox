package com.crio.jukebox.entities;

import java.util.List;

public class User extends BaseEntity{
    private String name;
    
    public User(Integer id, String name)
    {
        this.id = id;
        this.name = name;
    }
    public User(String name){
        this.name = name;
        
    }

    public String getName(){
        return this.name;
    }
}
