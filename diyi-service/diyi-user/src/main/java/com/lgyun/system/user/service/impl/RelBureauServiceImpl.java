package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.RelBureauType;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.common.tool.DigestUtil;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.dto.AddRelBureauDTO;
import com.lgyun.system.user.dto.QueryRelBureauListDTO;
import com.lgyun.system.user.dto.UpdateRelBureauDTO;
import com.lgyun.system.user.entity.RelBureauEntity;
import com.lgyun.system.user.mapper.RelBureauMapper;
import com.lgyun.system.user.service.IRelBureauService;
import com.lgyun.system.user.vo.RelBureauVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * 税务局管理表 Service 实现
 *
 * @author tzq
 * @since 2020-10-20 18:47:56
 */
@Slf4j
@Service
@AllArgsConstructor
public class RelBureauServiceImpl extends BaseServiceImpl<RelBureauMapper, RelBureauEntity> implements IRelBureauService {

    @Override
    public RelBureauEntity findByEmployeeUserNameAndEmployeePwd(String relBureauUserName, String relBureauPwd, RelBureauType relBureauType) {
        QueryWrapper<RelBureauEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(RelBureauEntity::getRelBureauUserName, relBureauUserName)
                .eq(RelBureauEntity::getRelBureauPwd, relBureauPwd)
                .eq(RelBureauEntity::getRelBureauType, relBureauType);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public R addRelBureau(AddRelBureauDTO addRelBureauDto) {
        RelBureauEntity relBureauEntity = new RelBureauEntity();
        BeanUtils.copyProperties(addRelBureauDto, relBureauEntity);
        relBureauEntity.setRelBureauPwd(DigestUtil.encrypt(relBureauEntity.getRelBureauPwd()));
        boolean flag = this.save(relBureauEntity);
        if (flag) {
            return R.success("添加成功");
        }
        return R.fail("添加失败");
    }

    @Override
    public R<IPage<RelBureauVO>> QueryRelBureau(QueryRelBureauListDTO queryRelBureauListDTO, IPage<RelBureauVO> page, RelBureauType relBureauType) {
        if (queryRelBureauListDTO.getBeginDate().after(queryRelBureauListDTO.getEndDate())) {
            return R.fail("开始时间不能大于结束时间");
        }
        return R.data(page.setRecords(baseMapper.QueryRelBureau(queryRelBureauListDTO, page, relBureauType)));
    }

    @Override
    public R<RelBureauEntity> queryRelBureauInfo(Long bureauId) {
        RelBureauEntity relBureauEntity = baseMapper.selectById(bureauId);
        relBureauEntity.setRelBureauPwd("");
        return R.data(relBureauEntity);
    }

    @Override
    public R updateBureau(UpdateRelBureauDTO updateRelBureauDTO) {
        RelBureauEntity relBureauEntity = this.getById(updateRelBureauDTO.getBureauId());
        if (relBureauEntity == null) {
            return R.fail("您编辑的内容不存在");
        }
        BeanUtil.copyProperties(updateRelBureauDTO, relBureauEntity);
        if (!StringUtils.isBlank(updateRelBureauDTO.getPassWord())) {
            if (updateRelBureauDTO.getPassWord().length() < 6 || updateRelBureauDTO.getPassWord().length() > 18) {
                return R.fail("请输入6-18位的密码");
            }
            relBureauEntity.setRelBureauPwd(DigestUtil.encrypt(updateRelBureauDTO.getPassWord()));
        }
        boolean flag = this.updateById(relBureauEntity);
        if (flag) {
            return R.success("编辑成功");
        }
        return R.fail("编辑失败");
    }
}
