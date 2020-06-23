package com.lgyun.desk.feign;

import com.lgyun.common.api.R;
import com.lgyun.common.constant.AppConstant;
import com.lgyun.desk.entity.Notice;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Notice Feign接口类
 *
 * @author liangfeihu
 */
@FeignClient(
        value = AppConstant.APPLICATION_DESK_NAME
)
public interface INoticeClient {

    String API_PREFIX = "/dashboard";

    /**
     * 获取notice列表
     *
     * @param number
     * @return
     */
    @GetMapping(API_PREFIX + "/top")
	R<List<Notice>> top(@RequestParam("number") Integer number);

}
