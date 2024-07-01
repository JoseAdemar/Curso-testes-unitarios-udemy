package com.appsdeveloperblog.store.service;

import com.appsdeveloperblog.store.model.User;

public interface UserService {
    User createUser(String firstName,
                    String lastName,
                    String email,
                    String password,
                    String repeatPassword);
}
