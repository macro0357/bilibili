package com.bilibili.dao;

import com.bilibili.domain.auth.AuthRole;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthRoleDao {
    public AuthRole getRoleByCode(String code);
}
