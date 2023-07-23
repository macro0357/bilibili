package com.bilibili.api;

import com.bilibili.domain.JsonResponse;
import com.bilibili.domain.Video;
import com.bilibili.service.DemoService;
import com.bilibili.service.ElasticSearchService;
import com.bilibili.service.feign.MsDeclareService;
import com.bilibili.service.util.FastDFSUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
public class DemoApi {
    @Autowired
    private DemoService demoService;

    @Autowired
    private FastDFSUtil fastDFSUtil;

    @Autowired
    private ElasticSearchService elasticSearchService;

    @Autowired
    private MsDeclareService msDeclareService;

    @GetMapping("/query")
    public Map<String, Object> query(Long id) {
        return demoService.query(id);
    }

    @GetMapping("/slices")
    public void slices(MultipartFile file) throws Exception {
        fastDFSUtil.convertFileToSlices(file);
    }

    @GetMapping("/es-videos")
    public JsonResponse<Video> getEsVideos(@RequestParam String keyword) {
        Video video = elasticSearchService.getVideos(keyword);
        return new JsonResponse<>(video);
    }

    @GetMapping("/demos")
    public Long msget(@RequestParam Long id) {
        return msDeclareService.msget(id);
    }

    @PostMapping("demos")
    public Map<String, Object> mspost(@RequestBody Map<String, Object> params) {
        return msDeclareService.mspost(params);
    }

    @HystrixCommand(fallbackMethod = "error",
            commandProperties = { // 接口请求超过2s就会降级为error方法
                    @HystrixProperty(
                            name = "execution.isolation.thread.timeoutInMilliseconds",
                            value = "2000"
                    )
            }
    )
    @GetMapping("/timeout")
    public String circuitBreakerWithHystrix(@RequestParam Long time){
        return msDeclareService.timeout(time);
    }

    public String error(Long time){
        return "超时出错！";
    }
}
