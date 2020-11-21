package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.RelBureauType;
import com.lgyun.common.secure.BladeUser;
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
     * 查询当前相关局
     *
     * @param bladeUser
     * @return
     */
    R<RelBureauEntity> currentRelBureau(BladeUser bladeUser);

    /**
     * 根据userId查询相关局
     *
     * @param userId
     * @return
     */
    RelBureauEntity findByUserId(Long userId);

    /**
     * 根据账号密码查询相关局
     *
     * @param relBureauUserName
     * @param relBureauPwd
     * @param relBureauType
     * @return
     */
    RelBureauEntity findByEmployeeUserNameAndEmployeePwd(String relBureauUserName, String relBureauPwd, RelBureauType relBureauType);

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
     * @param relBureauType
     * @return
     */
    R<IPage<RelBureauVO>> QueryRelBureau(QueryRelBureauListDTO queryRelBureauListDTO, IPage<RelBureauVO> page, RelBureauType relBureauType);

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

