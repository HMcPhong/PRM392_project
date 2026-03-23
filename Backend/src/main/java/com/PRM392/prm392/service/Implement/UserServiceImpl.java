package com.PRM392.prm392.service.Implement;

import com.PRM392.prm392.entity.Carts;
import com.PRM392.prm392.entity.User;
import com.PRM392.prm392.enums.Role;
import com.PRM392.prm392.repository.CartRepository;
import com.PRM392.prm392.repository.UserRepository;
import com.PRM392.prm392.request.create.User.CustomerCreateRequet;
import com.PRM392.prm392.service.Interface.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CartRepository cartRepository;

    public UserServiceImpl(UserRepository userRepository, CartRepository cartRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
        this.cartRepository = cartRepository;
    }

    /* Customer */

    @Override
    public User createCustomer(CustomerCreateRequet request) {
        User customer = new User();
        customer.setUserName(request.getUserName());
        customer.setPasswordHash(bCryptPasswordEncoder.encode(request.getPassword()));
        if(request.getEmail() != null) customer.setEmail(request.getEmail());
        if(request.getPhoneNumber() != null) customer.setPhoneNumber(request.getPhoneNumber());
        if(request.getAddress() != null) customer.setAddress(request.getAddress());
        customer.setRole(Role.CUSTOMER);
        customer = userRepository.save(customer);

        Carts cart = new Carts();
        cart.setUser_id(customer.getUserID());
        cartRepository.save(cart);

        return customer;
    }

    @Override
    public User getUserById(int id) {
        Optional<User> user = userRepository.findByUserID(id);
        return user.orElse(null);
    }


    /* Manager */

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

}
