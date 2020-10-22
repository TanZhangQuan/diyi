package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.BureauType;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.common.tool.DigestUtil;
import com.lgyun.system.user.dto.admin.AddRelBureauDTO;
import com.lgyun.system.user.dto.admin.QueryRelBureauListDTO;
import com.lgyun.system.user.dto.admin.UpdateRelBureauDTO;
import com.lgyun.system.user.vo.admin.RelBureauNoticeVO;
import com.lgyun.system.user.vo.admin.RelBureauVO;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.mapper.RelBureauMapper;
import com.lgyun.system.user.entity.RelBureauEntity;
import com.lgyun.system.user.service.IRelBureauService;

/**
 * 税务局管理表 Service 实现
 *
 * @author liangfeihu
 * @since 2020-10-20 18:47:56
 */
@Slf4j
@Service
@AllArgsConstructor
public class RelBureauServiceImpl extends BaseServiceImpl<RelBureauMapper, RelBureauEntity> implements IRelBureauService {

    /**
     * 添加税务局管理
     * @param addRelBureauDto
     * @return
     */
    @Override
    public R addRelBureau(AddRelBureauDTO addRelBureauDto) {
        RelBureauEntity relBureauEntity = new RelBureauEntity();
        BeanUtils.copyProperties(addRelBureauDto,relBureauEntity);
        relBureauEntity.setRelBpwd(DigestUtil.encrypt(relBureauEntity.getRelBpwd()));
        boolean flag = this.save(relBureauEntity);
        if (flag) {
            return R.success("添加成功！");
        }
        return R.fail("添加失败！");
    }

    /**
     * 查询税务局管理
     * @param queryRelBureauListDTO
     * @param page
     * @return
     */
    @Override
    public R<IPage<RelBureauVO>> QueryRelBureau(QueryRelBureauListDTO queryRelBureauListDTO, IPage<RelBureauVO> page, BureauType bureauType) {
        return R.data(page.setRecords(baseMapper.QueryRelBureau(queryRelBureauListDTO,page, bureauType)));
    }

    /**
     * 根据ID查询税务局管理的信息
     * @param bureauId
     * @return
     */
    @Override
    public R<RelBureauEntity> queryRelBureauInfo(Long bureauId) {
        RelBureauEntity relBureauEntity = baseMapper.selectById(bureauId);
        relBureauEntity.setRelBpwd("");
        return R.data(relBureauEntity);
    }

    /**
     * 编辑税务局管理的信息
     * @param updateRelBureauDTO
     * @return
     */
    @Override
    public R updateBureau(UpdateRelBureauDTO updateRelBureauDTO) {
        RelBureauEntity relBureauEntity = this.getById(updateRelBureauDTO.getBureauId());
        if (relBureauEntity == null) {
            return R.fail("您编辑的内容不存在！");
        }
        BeanUtil.copyProperties(updateRelBureauDTO,relBureauEntity);
        if (!StringUtils.isBlank(updateRelBureauDTO.getPassWord())) {
            if (updateRelBureauDTO.getPassWord().equals(updateRelBureauDTO.getConfirmPassword())) {
                relBureauEntity.setRelBpwd(DigestUtil.encrypt(updateRelBureauDTO.getConfirmPassword()));
            }
            log.error("输入的2次密码不一样");
            return R.fail("您输入的2次密码不一样，请重新输入！");
        }
        if (!StringUtils.isBlank(updateRelBureauDTO.getConfirmPassword())) {
            if (updateRelBureauDTO.getConfirmPassword().equals(updateRelBureauDTO.getPassWord())) {
                relBureauEntity.setRelBpwd(DigestUtil.encrypt(updateRelBureauDTO.getConfirmPassword()));
            }
            log.error("输入的2次密码不一样");
            return R.fail("你输入的2次密码不一样，请重新输入！");
        }
        boolean flag = this.updateById(relBureauEntity);
        if (flag) {
            return R.success("编辑成功！");
        }
        return R.fail("编辑失败！");
    }
}
