package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.BureauType;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.dto.AddRelBureauDTO;
import com.lgyun.system.user.dto.QueryRelBureauListDTO;
import com.lgyun.system.user.dto.UpdateRelBureauDTO;
import com.lgyun.system.user.entity.RelBureauEntity;
import com.lgyun.system.user.vo.RelBureauVO;

/**
 * 相关局管理表 Service 接口
 *
 * @author tzq
 * @since 2020-10-20 18:47:56
 */
public interface IRelBureauService extends BaseService<RelBureauEntity> {

    /**
     * 添加相关局
     *
     * @param addRelBureauDto
     * @return
     */
    R addRelBureau(AddRelBureauDTO addRelBureauDto);

    /**
     * 查询相关局
     *
     * @param queryRelBureauListDTO
     * @param page
     * @param bureauType
     * @return
     */
    R<IPage<RelBureauVO>> QueryRelBureau(QueryRelBureauListDTO queryRelBureauListDTO, IPage<RelBureauVO> page, BureauType bureauType);

    /**
     * 查询相关局信息
     *
     * @param bureauId
     * @return
     */
    R<RelBureauEntity> queryRelBureauInfo(Long bureauId);

    /**
     * 编辑相关局
     *
     * @param updateRelBureauDTO
     * @return
     */
    R updateBureau(UpdateRelBureauDTO updateRelBureauDTO);
}

