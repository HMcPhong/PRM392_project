package com.PRM392.prm392.service.Interface;

import com.PRM392.prm392.entity.User;
import com.PRM392.prm392.request.create.User.CustomerCreateRequest;

import java.util.List;

public interface UserService {

    boolean login(String username, String password);

    boolean isExist(String username);


    /* Customer */
    User createCustomer(CustomerCreateRequest user);

    User getUserById(int id);


    /* Manager */
    User createManager(CustomerCreateRequest user);

    List<User> getAllUser();
}
