package com.crio.jukebox.repositories;

import com.crio.jukebox.entities.User;

public interface IUserRepository {
    public User save(User entity);
    public int getSize();
}
