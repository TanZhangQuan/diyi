package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgyun.system.user.entity.IndividualEnterpriseAnnualFeeEntity;
import com.lgyun.system.user.mapper.IndividualEnterpriseAnnualFeeMapper;
import com.lgyun.system.user.service.IIndividualEnterpriseAnnualFeeService;
import org.springframework.stereotype.Service;

/**
 *  Service 实现
 *
 * @author liangfeihu
 * @since 2020-07-02 17:44:02
 */
@Service("individualEnterpriseAnnualFeeService")
public class IndividualEnterpriseAnnualFeeServiceImpl extends ServiceImpl<IndividualEnterpriseAnnualFeeMapper, IndividualEnterpriseAnnualFeeEntity> implements IIndividualEnterpriseAnnualFeeService {

}
