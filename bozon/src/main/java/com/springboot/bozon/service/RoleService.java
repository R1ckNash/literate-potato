package com.springboot.bozon.service;

import com.springboot.bozon.model.Role;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author mialyshev
 */

public interface RoleService {
    List<Role> getAll();
}
