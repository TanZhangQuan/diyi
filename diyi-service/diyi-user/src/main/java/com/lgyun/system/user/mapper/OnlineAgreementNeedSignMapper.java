package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgyun.common.enumeration.ObjectType;
import com.lgyun.common.enumeration.TemplateType;
import com.lgyun.system.user.entity.OnlineAgreementNeedSignEntity;
import com.lgyun.system.user.vo.OnlineAgreementNeedSignVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Mapper
 *
 * @author jun
 * @since 2020-07-18 14:37:08
 */
@Mapper
public interface OnlineAgreementNeedSignMapper extends BaseMapper<OnlineAgreementNeedSignEntity> {

    /**
     * 查询需要签署的授权协议
     *
     * @param objectType
     * @param objectTypeId
     * @param templateType
     * @return
     */
    List<OnlineAgreementNeedSignVO> getOnlineAgreementNeedSign(ObjectType objectType, Long objectTypeId, TemplateType templateType);

}

