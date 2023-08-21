package com.crio.jukebox.services;

import com.crio.jukebox.entities.User;
import com.crio.jukebox.repositories.IUserRepository;

public class UserService implements IUserService{

    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository){
        this.userRepository = userRepository;
    }
    
    @Override
    public String create(String name){
        User user = new User(name);
      //  System.out.println(userRepository.getSize());
        User u = userRepository.save(user);
        return u.getId() + " " + u.getName();
    }
}
