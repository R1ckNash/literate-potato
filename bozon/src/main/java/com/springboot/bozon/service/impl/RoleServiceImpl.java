package com.springboot.bozon.service.impl;

import com.springboot.bozon.model.Role;
import com.springboot.bozon.repository.RoleRepository;
import com.springboot.bozon.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author mialyshev
 */

@Service
public class RoleServiceImpl implements RoleService {


    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }
}
