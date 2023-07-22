package com.bilibili.dao.repository;

import com.bilibili.domain.Video;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface VideoRepository extends ElasticsearchRepository<Video, Long> {

    //find by title like 根据Video中title属性查
    Video findByTitleLike(String keyword);
}
