package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.BureauServiceProviderStatus;
import com.lgyun.common.enumeration.BureauType;
import com.lgyun.common.exception.CustomException;
import com.lgyun.common.tool.Func;
import com.lgyun.system.user.entity.RelBureauEntity;
import com.lgyun.system.user.entity.ServiceProviderEntity;
import com.lgyun.system.user.mapper.RelBureauMapper;
import com.lgyun.system.user.mapper.ServiceProviderMapper;
import com.lgyun.system.user.vo.RelBureauServiceProviderVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.mapper.RelBureauServiceProviderMapper;
import com.lgyun.system.user.entity.RelBureauServiceProviderEntity;
import com.lgyun.system.user.service.IRelBureauServiceProviderService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 相关局与服务商关联表 Service 实现
 *
 * @author tzq
 * @since 2020-10-20 18:47:56
 */
@Slf4j
@Service
@AllArgsConstructor
public class RelBureauServiceProviderServiceImpl extends BaseServiceImpl<RelBureauServiceProviderMapper, RelBureauServiceProviderEntity> implements IRelBureauServiceProviderService {

    private ServiceProviderMapper serviceProviderMapper;
    private RelBureauMapper relBureauMapper;

    /**
     * 查询匹配的服务商
     *
     * @param serviceProviderName
     * @param page
     * @return
     */
    @Override
    public R<IPage<RelBureauServiceProviderVO>> queryRelBureauServiceProvider(String serviceProviderName, BureauType bureauType, IPage<RelBureauServiceProviderVO> page) {
        return R.data(page.setRecords(baseMapper.queryRelBureauServiceProvider(serviceProviderName, bureauType, page)));
    }

    /**
     * 给相关局添加匹配服务商
     *
     * @param serviceProviderIds
     * @param bureauId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> addRelBureauServiceProvider(String serviceProviderIds, Long bureauId) {
        RelBureauEntity relBureauEntity = relBureauMapper.selectById(bureauId);
        if (relBureauEntity == null) {
            return R.fail("税务局不存在");
        }
        List<Long> longs = Func.toLongList(serviceProviderIds);
        for (Long id : longs) {
            ServiceProviderEntity serviceProviderEntity = serviceProviderMapper.selectById(id);
            if (serviceProviderEntity == null) {
                throw new CustomException("服务商不存在");
            }
            RelBureauServiceProviderEntity entity = new RelBureauServiceProviderEntity();
            entity.setServiceProviderId(id);
            entity.setRelBureauId(bureauId);
            entity.setBureauType(relBureauEntity.getBureauType());
            entity.setBureauServiceProviderStatus(BureauServiceProviderStatus.OPEN);
            this.save(entity);
        }
        return R.success("添加匹配服务商成功");
    }

    /**
     * 开启或关闭匹配服务商
     *
     * @param bureauServiceProviderId
     * @param bureauServiceProviderStatus
     * @return
     */
    @Override
    public R<String> updateBureauServiceProvider(Long bureauServiceProviderId, BureauServiceProviderStatus bureauServiceProviderStatus) {
        RelBureauServiceProviderEntity entity = this.getById(bureauServiceProviderId);
        if (entity == null) {
            return R.fail("你输入的匹配服务商不存在");
        }

        entity.setId(bureauServiceProviderId);
        entity.setBureauServiceProviderStatus(bureauServiceProviderStatus);
        this.updateById(entity);

        return R.success("操作成功");
    }

    /**
     * 撤销匹配的服务商
     *
     * @param bureauServiceProviderId
     * @return
     */
    @Override
    public R<String> deleteBureauServiceProvider(Long bureauServiceProviderId) {

        RelBureauServiceProviderEntity relBureauServiceProviderEntity = this.getById(bureauServiceProviderId);
        if (relBureauServiceProviderEntity == null) {
            return R.fail("你输入匹配服务商不存在");
        }

        baseMapper.removeById(bureauServiceProviderId);

        return R.success("撤销成功");
    }
}
