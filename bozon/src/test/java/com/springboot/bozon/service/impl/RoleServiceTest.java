package com.springboot.bozon.service.impl;

import com.springboot.bozon.model.Role;
import com.springboot.bozon.repository.RoleRepository;
import com.springboot.bozon.service.RoleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static org.mockito.Mockito.when;

@SpringJUnitConfig(value = RoleServiceImpl.class)
class RoleServiceTest {

    @Autowired
    private RoleService roleService;
    @MockBean
    private RoleRepository roleRepository;

    @Test
    void getAll() {
        List<Role> roles = List.of(new Role());
        when(roleRepository.findAll()).thenReturn(roles);
        Assertions.assertEquals(roles, roleService.getAll());
    }
}