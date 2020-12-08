package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.RelBureauType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.dto.AddOrUpdateRelBureauDTO;
import com.lgyun.system.user.dto.RelBureauListDTO;
import com.lgyun.system.user.entity.RelBureauEntity;
import com.lgyun.system.user.vo.RelBureauInfoVO;
import com.lgyun.system.user.vo.RelBureauListVO;
import com.lgyun.system.user.vo.RelBureauUpdateDetailVO;

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
     * @param addOrUpdateRelBureauDto
     * @return
     */
    R<String> addOrUpdateRelBureau(AddOrUpdateRelBureauDTO addOrUpdateRelBureauDto);

    /**
     * 查询相关局编辑详情
     *
     * @param relBureauId
     * @return
     */
    R<RelBureauUpdateDetailVO> queryRelBureauUpdateDetail(Long relBureauId);

    /**
     * 根据相关局类型，用户名查询相关局
     *
     * @param relBureauType
     * @param relBureauUserName
     * @param relBureauId
     * @return
     */
    int queryRelBureauByTypeAndUserNameNum(RelBureauType relBureauType, String relBureauUserName, Long relBureauId);

    /**
     * 查询相关局
     *
     * @param relBureauListDTO
     * @param page
     * @return
     */
    R<IPage<RelBureauListVO>> queryRelBureauList(RelBureauListDTO relBureauListDTO, IPage<RelBureauListVO> page);

    /**
     * 查询相关局基础信息
     *
     * @param relBureauId
     * @return
     */
    R<RelBureauInfoVO> queryRelBureauInfo(Long relBureauId);

    /**
     * 查询相关局
     *
     * @param relBureauId
     * @return
     */
    int queryCountById(Long relBureauId);
}

