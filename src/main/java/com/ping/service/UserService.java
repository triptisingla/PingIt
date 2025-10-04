package com.ping.service;

import com.ping.exception.UserException;
import com.ping.model.User;
import com.ping.request.UpdateUserRequest;

import java.util.List;

public interface UserService {

    public User findUserById(Integer id) throws UserException;

    public User findUserProfile(String jwr) throws UserException;

    public User updateUser(Integer userId, UpdateUserRequest req) throws UserException;

    public List<User> searchUser (String query);

    public List<User> getTop10Users();

}
