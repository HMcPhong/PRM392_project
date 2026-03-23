package com.PRM392.prm392.service.Interface;

import com.PRM392.prm392.entity.User;
import com.PRM392.prm392.request.create.User.CustomerCreateRequet;

import java.util.List;

public interface UserService {

    /* Customer */
    User createCustomer(CustomerCreateRequet user);

    User getUserById(int id);


    /* Manager */
    List<User> getAllUser();
}
