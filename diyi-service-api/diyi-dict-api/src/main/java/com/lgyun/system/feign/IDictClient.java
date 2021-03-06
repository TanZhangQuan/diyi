package com.lgyun.system.feign;

import com.lgyun.common.api.R;
import com.lgyun.common.constant.AppConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Feign接口类
 *
 * @author tzq
 * @since 2020/6/6 19:19
 */
@FeignClient(
        value = AppConstant.APPLICATION_SYSTEM_NAME,
        fallback = IDictClientFallback.class
)
public interface IDictClient {

    String API_PREFIX = "/dict";

    /**
     * 查询字典表对应值
     *
     * @param code    字典编号
     * @param dictKey 字典序号
     * @return
     */
    @GetMapping(API_PREFIX + "/getValue")
    R<String> getValue(@RequestParam("code") String code, @RequestParam("dictKey") String dictKey);

}
