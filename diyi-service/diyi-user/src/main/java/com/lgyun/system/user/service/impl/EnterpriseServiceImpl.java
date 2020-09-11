package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.RelationshipType;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.admin.QueryEnterpriseListPaymentDTO;
import com.lgyun.system.user.dto.admin.QueryServiceProviderListPaymentDTO;
import com.lgyun.system.user.entity.EnterpriseEntity;
import com.lgyun.system.user.entity.MakerEnterpriseEntity;
import com.lgyun.system.user.mapper.EnterpriseMapper;
import com.lgyun.system.user.oss.AliyunOssService;
import com.lgyun.system.user.service.IEnterpriseProviderService;
import com.lgyun.system.user.service.IEnterpriseService;
import com.lgyun.system.user.service.IMakerEnterpriseService;
import com.lgyun.system.user.vo.EnterprisesDetailVO;
import com.lgyun.system.user.vo.MakerEnterpriseRelationVO;
import com.lgyun.system.user.vo.ServiceProviderIdNameListVO;
import com.lgyun.system.user.vo.admin.QueryEnterpriseListPaymentVO;
import com.lgyun.system.user.vo.admin.QueryServiceProviderListPaymentVO;
import com.lgyun.system.user.vo.admin.QueryEnterpriseIdAndNameListVO;
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
 * @author tzq
 * @since 2020-06-26 17:21:05
 */
@Slf4j
@Service
@AllArgsConstructor
public class EnterpriseServiceImpl extends BaseServiceImpl<EnterpriseMapper, EnterpriseEntity> implements IEnterpriseService {

    private AliyunOssService ossService;
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
            makerEnterpriseRelationVO.setLegalPersonName("***");
            makerEnterpriseRelationVO.setLegalPersonIdCard("*********");
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
    public R<IPage<ServiceProviderIdNameListVO>> getServiceProviders(Query query, Long enterpriseId) {
        return enterpriseProviderService.getServiceProviderByEnterpriseId(Condition.getPage(query.setDescs("create_time")), enterpriseId, null);
    }

    /**
     * 查询商户基本信息
     *
     * @param enterpriseId
     * @return
     */
    @Override
    public R<EnterpriseResponse> getBasicEnterpriseResponse(Long enterpriseId) {
        EnterpriseEntity enterpriseEntity = getById(enterpriseId);
        EnterpriseResponse enterpriseResponse = new EnterpriseResponse();
        BeanUtils.copyProperties(enterpriseEntity, enterpriseResponse);
        return R.data(enterpriseResponse);
    }

    /**
     * 上传商户营业执照
     *
     * @param enterpriseId
     * @param file
     * @return
     */
    @Override
    public void uploadEnterpriseLicence(Long enterpriseId, MultipartFile file) throws Exception {

        //判断文件内容是否为空
        if (file.isEmpty()) {
            throw new RuntimeException("上传文件不能为空");
        }
        // 查询上传文件的后缀
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        // 上传文件中
        String url = ossService.uploadSuffix(file.getBytes(), suffix);

        EnterpriseEntity enterpriseEntity = getById(enterpriseId);
        log.info("[uploadEnterpriseLicence]enterpriseId={} url={}", enterpriseEntity.getId(), url);
        // 更新营业执照
        enterpriseEntity.setBizLicenceUrl(url);
        this.save(enterpriseEntity);
    }

    @Override
    public R<EnterprisesDetailVO> getEnterpriseDetailById(Long enterpriseId) {
        return R.data(baseMapper.getEnterpriseDetailById(enterpriseId));
    }

    @Override
    public R<IPage<QueryEnterpriseListPaymentVO>> queryEnterpriseList(QueryEnterpriseListPaymentDTO queryEnterpriseListPaymentDTO, IPage<QueryEnterpriseListPaymentVO> page) {
        return R.data(page.setRecords(baseMapper.queryEnterpriseList(queryEnterpriseListPaymentDTO, page)));
    }

    @Override
    public R<IPage<QueryServiceProviderListPaymentVO>> queryServiceProviderList(QueryServiceProviderListPaymentDTO queryServiceProviderListPaymentDTO, IPage<QueryServiceProviderListPaymentVO> page) {
        return R.data(page.setRecords(baseMapper.queryServiceProviderList(queryServiceProviderListPaymentDTO, page)));
    }

    @Override
    public R<IPage<QueryEnterpriseIdAndNameListVO>> queryEnterpriseIdAndNameList(String enterpriseName, IPage<QueryEnterpriseIdAndNameListVO> page) {
        return R.data(page.setRecords(baseMapper.queryEnterpriseIdAndNameList(enterpriseName, page)));
    }

}
