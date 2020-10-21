package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.BureauServiceProviderStatus;
import com.lgyun.common.tool.Func;
import com.lgyun.system.user.entity.ServiceProviderEntity;
import com.lgyun.system.user.mapper.ServiceProviderMapper;
import com.lgyun.system.user.vo.admin.RelBureauServiceProviderVO;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.record.VCenterRecord;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.lgyun.core.mp.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import com.lgyun.system.user.mapper.RelBureauServiceProviderMapper;
import com.lgyun.system.user.entity.RelBureauServiceProviderEntity;
import com.lgyun.system.user.service.IRelBureauServiceProviderService;

import java.util.ArrayList;
import java.util.List;

/**
 * 相关局与服务商关联表 Service 实现
 *
 * @author liangfeihu
 * @since 2020-10-20 18:47:56
 */
@Slf4j
@Service
@AllArgsConstructor
public class RelBureauServiceProviderServiceImpl extends BaseServiceImpl<RelBureauServiceProviderMapper, RelBureauServiceProviderEntity> implements IRelBureauServiceProviderService {

    private ServiceProviderMapper serviceProviderMapper;

    /**
     * 查询匹配的服务商
     *
     * @param serviceProviderName
     * @param page
     * @return
     */
    @Override
    public R<IPage<RelBureauServiceProviderVO>> queryRelBureauServiceProvider(String serviceProviderName, IPage<RelBureauServiceProviderVO> page) {
        return R.data(page.setRecords(baseMapper.queryRelBureauServiceProvider(serviceProviderName, page)));
    }

    /**
     * 给相关局添加匹配服务商
     *
     * @param serviceProviderIds
     * @param bureauId
     * @return
     */
    @Override
    public R addRelBureauServiceProvider(String serviceProviderIds, Long bureauId) {
        List<Long> longs = Func.toLongList(serviceProviderIds);
        for (Long id : longs) {
            ServiceProviderEntity serviceProviderEntity = serviceProviderMapper.selectById(id);
            if (serviceProviderEntity == null) {
                break;
            }
            RelBureauServiceProviderEntity entity = new RelBureauServiceProviderEntity();
            entity.setServiceProviderId(id);
            entity.setRelBureauId(bureauId);
            entity.setBureauServiceProviderStatus(BureauServiceProviderStatus.OPEN);
            this.save(entity);
        }
        return R.success("添加匹配服务商成功！");
    }
}
