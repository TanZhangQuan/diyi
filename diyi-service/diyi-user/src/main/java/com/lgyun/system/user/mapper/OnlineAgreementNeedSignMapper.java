package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgyun.common.api.R;
import com.lgyun.system.user.entity.OnlineAgreementNeedSignEntity;
import com.lgyun.system.user.vo.OnlineAgreementNeedSignVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *  Mapper
 *
 * @author jun
 * @since 2020-07-18 14:37:08
 */
@Mapper
public interface OnlineAgreementNeedSignMapper extends BaseMapper<OnlineAgreementNeedSignEntity> {

    List<OnlineAgreementNeedSignVO> getOnlineAgreementNeedSign(Long makerId);
}

