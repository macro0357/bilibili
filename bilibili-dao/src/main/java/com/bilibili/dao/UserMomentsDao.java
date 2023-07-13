package com.bilibili.dao;

import com.bilibili.domain.UserMoment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMomentsDao {
    void addUserMoments(UserMoment userMoment);
}
