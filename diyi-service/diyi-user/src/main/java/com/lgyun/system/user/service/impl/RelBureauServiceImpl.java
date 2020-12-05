package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.common.enumeration.RelBureauType;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.common.tool.DigestUtil;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.dto.AddOrUpdateRelBureauDTO;
import com.lgyun.system.user.dto.RelBureauListDTO;
import com.lgyun.system.user.entity.RelBureauEntity;
import com.lgyun.system.user.mapper.RelBureauMapper;
import com.lgyun.system.user.service.IRelBureauService;
import com.lgyun.system.user.vo.RelBureauInfoVO;
import com.lgyun.system.user.vo.RelBureauListVO;
import com.lgyun.system.user.vo.RelBureauUpdateDetailVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 相关局管理表 Service 实现
 *
 * @author tzq
 * @since 2020-10-20 18:47:56
 */
@Slf4j
@Service
@AllArgsConstructor
public class RelBureauServiceImpl extends BaseServiceImpl<RelBureauMapper, RelBureauEntity> implements IRelBureauService {

    @Override
    public R<RelBureauEntity> currentRelBureau(BladeUser bladeUser) {

        if (bladeUser == null || bladeUser.getUserId() == null) {
            return R.fail("用户未登录");
        }

        if (!(UserType.RELBUREAU.equals(bladeUser.getUserType()))) {
            return R.fail("用户类型有误");
        }

        RelBureauEntity relBureauEntity = getById(bladeUser.getUserId());
        if (relBureauEntity == null) {
            return R.fail("相关局不存在");
        }

        if (!(AccountState.NORMAL.equals(relBureauEntity.getRelBureauState()))) {
            return R.fail("账号状态非正常，请联系客服");
        }

        return R.data(relBureauEntity);
    }

    @Override
    public RelBureauEntity findByEmployeeUserNameAndEmployeePwd(String relBureauUserName, String relBureauPwd, RelBureauType relBureauType) {
        QueryWrapper<RelBureauEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(RelBureauEntity::getRelBureauUserName, relBureauUserName)
                .eq(RelBureauEntity::getRelBureauPwd, relBureauPwd)
                .eq(RelBureauEntity::getRelBureauType, relBureauType);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R addOrUpdateRelBureau(AddOrUpdateRelBureauDTO addOrUpdateRelBureauDto) {

        RelBureauEntity relBureauEntity;
        if (addOrUpdateRelBureauDto.getRelBureauId() != null) {

            relBureauEntity = getById(addOrUpdateRelBureauDto.getRelBureauId());
            if (relBureauEntity == null) {
                return R.fail("相关局不存在");
            }

            if (StringUtils.isNotBlank(addOrUpdateRelBureauDto.getRelBureauPwd())) {
                if (addOrUpdateRelBureauDto.getRelBureauPwd().length() < 6 || addOrUpdateRelBureauDto.getRelBureauPwd().length() > 18) {
                    return R.fail("请输入长度为6-18位的密码");
                }

                addOrUpdateRelBureauDto.setRelBureauPwd(DigestUtil.encrypt(addOrUpdateRelBureauDto.getRelBureauPwd()));
            } else {
                addOrUpdateRelBureauDto.setRelBureauPwd(relBureauEntity.getRelBureauPwd());
            }

            int oldRelBureauNum = queryRelBureauByTypeAndUserNameNum(addOrUpdateRelBureauDto.getRelBureauType(), addOrUpdateRelBureauDto.getRelBureauUserName(), relBureauEntity.getId());
            if (oldRelBureauNum > 0) {
                return R.fail("已存在相同账号");
            }

        } else {

            relBureauEntity = new RelBureauEntity();

            if (StringUtils.isBlank(addOrUpdateRelBureauDto.getRelBureauPwd())) {
                return R.fail("请输入密码");
            }

            if (addOrUpdateRelBureauDto.getRelBureauPwd().length() < 6 || addOrUpdateRelBureauDto.getRelBureauPwd().length() > 18) {
                return R.fail("请输入长度为6-18位的密码");
            }

            addOrUpdateRelBureauDto.setRelBureauPwd(DigestUtil.encrypt(addOrUpdateRelBureauDto.getRelBureauPwd()));

            int oldRelBureauNum = queryRelBureauByTypeAndUserNameNum(addOrUpdateRelBureauDto.getRelBureauType(), addOrUpdateRelBureauDto.getRelBureauUserName(), null);
            if (oldRelBureauNum > 0) {
                return R.fail("已存在相同账号");
            }

        }

        BeanUtils.copyProperties(addOrUpdateRelBureauDto, relBureauEntity);
        saveOrUpdate(relBureauEntity);

        return R.success("操作成功");
    }

    @Override
    public R<RelBureauUpdateDetailVO> queryRelBureauUpdateDetail(Long relBureauId) {
        return R.data(baseMapper.queryRelBureauUpdateDetail(relBureauId));
    }

    @Override
    public int queryRelBureauByTypeAndUserNameNum(RelBureauType relBureauType, String relBureauUserName, Long relBureauId) {
        QueryWrapper<RelBureauEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(RelBureauEntity::getRelBureauType, relBureauType)
                .eq(RelBureauEntity::getRelBureauUserName, relBureauUserName)
                .ne(relBureauId != null, RelBureauEntity::getId, relBureauId);

        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    public R<IPage<RelBureauListVO>> queryRelBureauList(RelBureauType relBureauType, RelBureauListDTO relBureauListDTO, IPage<RelBureauListVO> page) {

        if (relBureauListDTO.getBeginDate() != null && relBureauListDTO.getEndDate() != null) {
            if (relBureauListDTO.getBeginDate().after(relBureauListDTO.getEndDate())) {
                return R.fail("开始时间不能大于结束时间");
            }
        }

        return R.data(page.setRecords(baseMapper.queryRelBureauList(relBureauType, relBureauListDTO, page)));
    }

    @Override
    public R<RelBureauInfoVO> queryRelBureauInfo(Long relBureauId) {
        return R.data(baseMapper.queryRelBureauInfo(relBureauId));
    }

    @Override
    public int queryCountById(Long relBureauId) {
        QueryWrapper<RelBureauEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(RelBureauEntity::getId, relBureauId);

        return baseMapper.selectCount(queryWrapper);
    }

}
