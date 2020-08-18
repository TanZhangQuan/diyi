package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.common.enumeration.RelationshipType;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.vo.TransactionMonthVO;
import com.lgyun.system.user.entity.EnterpriseEntity;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.entity.MakerEnterpriseEntity;
import com.lgyun.system.user.entity.User;
import com.lgyun.system.user.mapper.EnterpriseMapper;
import com.lgyun.system.user.oss.AliyunOssService;
import com.lgyun.system.user.service.*;
import com.lgyun.system.user.vo.EnterpriseStatisticalVO;
import com.lgyun.system.user.vo.MakerEnterpriseRelationVO;
import com.lgyun.system.user.vo.ServiceProviderIdNameListVO;
import com.lgyun.system.user.vo.enterprise.EnterpriseResponse;
import com.lgyun.system.user.wrapper.EnterpriseWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service 实现
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:05
 */
@Slf4j
@Service
@AllArgsConstructor
public class EnterpriseServiceImpl extends BaseServiceImpl<EnterpriseMapper, EnterpriseEntity> implements IEnterpriseService {

    private IUserService userService;
    private AliyunOssService ossService;
    private IEnterpriseWorkerService enterpriseWorkerService;
    private IMakerEnterpriseService makerEnterpriseService;
    private IEnterpriseProviderService enterpriseProviderService;

    @Override
    public MakerEnterpriseRelationVO getEnterpriseName(String enterpriseName) {
        QueryWrapper<EnterpriseEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(EnterpriseEntity::getEnterpriseName, enterpriseName);
        EnterpriseEntity enterpriseEntity = baseMapper.selectOne(queryWrapper);
        return EnterpriseWrapper.build().makerEnterpriseRelationVO(enterpriseEntity);
    }

    @Override
    public R<MakerEnterpriseRelationVO> getEnterpriseId(Long enterpriseId, Long makerId) {
        MakerEnterpriseEntity enterpriseIdAndMakerIdLian = makerEnterpriseService.getEnterpriseIdAndMakerIdAndRelationshipType(enterpriseId, makerId, RelationshipType.RELEVANCE);
        MakerEnterpriseEntity enterpriseIdAndMakerIdZhu = makerEnterpriseService.getEnterpriseIdAndMakerIdAndRelationshipType(enterpriseId, makerId, RelationshipType.ATTENTION);
        QueryWrapper<EnterpriseEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(EnterpriseEntity::getId, enterpriseId);

        EnterpriseEntity enterpriseEntity = baseMapper.selectOne(queryWrapper);

        MakerEnterpriseRelationVO makerEnterpriseRelationVO = EnterpriseWrapper.build().makerEnterpriseRelationVO(enterpriseEntity);
        if (makerEnterpriseRelationVO == null) {
            return R.fail("商户不存在");
        }

        if (null == enterpriseIdAndMakerIdLian && null != enterpriseIdAndMakerIdZhu) {
            //TODO
            makerEnterpriseRelationVO.setContact1Phone("138********");
            makerEnterpriseRelationVO.setBizLicenceUrl("*");
            makerEnterpriseRelationVO.setLegalPerson("***");
            makerEnterpriseRelationVO.setLegalPersonCard("*********");
            makerEnterpriseRelationVO.setSocialCreditNo("*******");
            makerEnterpriseRelationVO.setContact1Position("********");
            makerEnterpriseRelationVO.setShopUserName("*****");
            makerEnterpriseRelationVO.setRelationshipType(RelationshipType.ATTENTION);
            return R.data(makerEnterpriseRelationVO);
        } else if (null != enterpriseIdAndMakerIdLian && null == enterpriseIdAndMakerIdZhu) {
            makerEnterpriseRelationVO.setRelationshipType(RelationshipType.RELEVANCE);
            return R.data(makerEnterpriseRelationVO);
        } else if (null == enterpriseIdAndMakerIdLian && null == enterpriseIdAndMakerIdZhu) {
            makerEnterpriseRelationVO.setRelationshipType(RelationshipType.NORELATION);
            return R.data(makerEnterpriseRelationVO);
        } else {
            makerEnterpriseRelationVO.setRelationshipType(RelationshipType.RELEVANCE);
            return R.data(makerEnterpriseRelationVO);
        }
    }

    @Override
    public R<EnterpriseStatisticalVO> statistical(Long enterpriseId) {
        return R.data(baseMapper.statistical(enterpriseId));
    }

    @Override
    public R<IPage<ServiceProviderIdNameListVO>> getServiceProviders(Query query, Long enterpriseId) {
        return enterpriseProviderService.getServiceProviderByEnterpriseId(Condition.getPage(query.setDescs("create_time")), enterpriseId, null);
    }

    @Override
    public R<TransactionMonthVO> queryEnterprisePayMoney(Long enterpriseId) {
        return R.data(baseMapper.queryEnterprisePayMoney(enterpriseId));
    }

    /**
     * 获取商户基本信息
     *
     * @param bladeUser
     * @return
     */
    @Override
    public EnterpriseResponse getBasicEnterpriseResponse(BladeUser bladeUser) {
        EnterpriseEntity enterpriseEntity = getLoginUserInfo(bladeUser);
        EnterpriseResponse enterpriseResponse = new EnterpriseResponse();
        BeanUtils.copyProperties(enterpriseEntity, enterpriseResponse);
        return enterpriseResponse;
    }

    private EnterpriseEntity getLoginUserInfo(BladeUser bladeUser) {
        if (bladeUser == null || bladeUser.getUserId() == null) {
            throw new RuntimeException("账号未登陆");
        }

        User user = userService.getById(bladeUser.getUserId());
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        if (!(UserType.ENTERPRISE.equals(user.getUserType()))) {
            throw new RuntimeException("用户类型有误");
        }

        EnterpriseWorkerEntity enterpriseWorkerEntity = enterpriseWorkerService.findByUserId(bladeUser.getUserId());
        if (enterpriseWorkerEntity == null) {
            throw new RuntimeException("账号未注册");
        }

        EnterpriseEntity enterpriseEntity = this.getById(enterpriseWorkerEntity.getEnterpriseId());
        if (enterpriseEntity == null) {
            throw new RuntimeException("商户不存在");
        }

        if (!(AccountState.NORMAL.equals(enterpriseEntity.getEnterpriseState()))) {
            throw new RuntimeException("商户状态非正常，请联系客服");
        }

        if (!(AccountState.NORMAL.equals(enterpriseWorkerEntity.getEnterpriseWorkerState()))) {
            throw new RuntimeException("账号状态非正常，请联系客服");
        }
        return enterpriseEntity;
    }

    /**
     * 上传商户营业执照
     *
     * @param bladeUser
     * @param file
     * @return
     */
    @Override
    public void uploadEnterpriseLicence(BladeUser bladeUser, MultipartFile file) throws Exception {
        EnterpriseEntity enterpriseEntity = getLoginUserInfo(bladeUser);

        //判断文件内容是否为空
        if (file.isEmpty()) {
            throw new RuntimeException("上传文件不能为空");
        }
        // 获取上传文件的后缀
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        // 上传文件中
        String url = ossService.uploadSuffix(file.getBytes(), suffix);

        log.info("[uploadEnterpriseLicence]enterpriseId={} url={}", enterpriseEntity.getId(), url);
        // 更新营业执照
        enterpriseEntity.setBizLicenceUrl(url);
        this.save(enterpriseEntity);
    }

}
