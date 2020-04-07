package com.tsystems.dao.api;

import com.tsystems.entity.User;

/**
 *
 */
public interface UserDAO extends GenericDAO<User, Integer> {
    User findByUsername(String username);
}
