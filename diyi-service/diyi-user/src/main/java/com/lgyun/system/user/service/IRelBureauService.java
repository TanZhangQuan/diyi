package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.BureauType;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.dto.admin.AddRelBureauDTO;
import com.lgyun.system.user.dto.admin.QueryRelBureauListDTO;
import com.lgyun.system.user.dto.admin.UpdateRelBureauDTO;
import com.lgyun.system.user.entity.RelBureauEntity;
import com.lgyun.system.user.vo.admin.RelBureauNoticeVO;
import com.lgyun.system.user.vo.admin.RelBureauVO;

/**
 * 税务局管理表 Service 接口
 *
 * @author liangfeihu
 * @since 2020-10-20 18:47:56
 */
public interface IRelBureauService extends BaseService<RelBureauEntity> {

    R addRelBureau(AddRelBureauDTO addRelBureauDto);

    R<IPage<RelBureauVO>> QueryRelBureau(QueryRelBureauListDTO queryRelBureauListDTO, IPage<RelBureauVO> page, BureauType bureauType);

    R<RelBureauEntity> queryRelBureauInfo(Long bureauId);

    R updateTaxBureau(UpdateRelBureauDTO updateRelBureauDTO);
}

