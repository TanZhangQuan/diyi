package com.lgyun.desk.feign;

import com.lgyun.common.api.R;
import com.lgyun.desk.entity.Notice;
import com.lgyun.desk.mapper.NoticeMapper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Notice Feign
 *
 * @author liangfeihu
 * @since 2020/6/30 11:28
 */
@RestController
@AllArgsConstructor
public class NoticeClient implements INoticeClient {

    NoticeMapper mapper;

    @Override
    @GetMapping(API_PREFIX + "/top")
    public R<List<Notice>> top(Integer number) {
        return R.data(mapper.topList(number));
    }

}
