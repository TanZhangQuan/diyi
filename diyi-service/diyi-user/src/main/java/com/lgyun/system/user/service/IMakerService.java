package com.lgyun.system.user.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.CertificationState;
import com.lgyun.common.enumeration.RelationshipType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.IdcardVerifyDTO;
import com.lgyun.system.user.dto.ImportMakerListDTO;
import com.lgyun.system.user.dto.MakerAddDTO;
import com.lgyun.system.user.dto.MakerListIndividualDTO;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.excel.MakerExcel;
import com.lgyun.system.user.vo.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * Service 接口
 *
 * @author tzq
 * @since 2020-06-26 17:21:06
 */
public interface IMakerService extends IService<MakerEntity> {

    /**
     * 根据ID查询创客
     *
     * @param id
     * @return
     */
    int queryCountById(Long id);

    /**
     * 查询当前创客
     *
     * @param bladeUser
     * @return
     */
    R<MakerEntity> currentMaker(BladeUser bladeUser);

    /**
     * 查询创客基本信息
     *
     * @param makerId
     * @return
     */
    R<MakerInfoVO> queryMakerInfo(Long makerId);

    /**
     * 查询当前创客详情
     *
     * @param makerId
     * @return
     */
    R<MakerDetailVO> queryCurrentMakerDetail(Long makerId);

    /**
     * 新建创客
     *
     * @param openid
     * @param sessionKey
     * @param purePhoneNumber
     * @param loginPwd
     * @return
     */
    MakerEntity makerSave(String openid, String sessionKey, String purePhoneNumber, String loginPwd);

    /**
     * 新建创客
     *
     * @param purePhoneNumber
     * @param name
     * @param idcardNo
     * @param bankCardNo
     * @param bankName
     * @param subBankName
     * @param enterpriseId
     * @return
     */
    MakerEntity makerSave(String purePhoneNumber, String name, String idcardNo, String bankCardNo, String bankName, String subBankName, Long enterpriseId);

    /**
     * 修改创客
     *
     * @param makerEntity
     * @param openid
     * @param sessionKey
     */
    void makerUpdate(MakerEntity makerEntity, String openid, String sessionKey);

    /**
     * 根据微信手机号码查询创客
     *
     * @param phoneNumber
     * @return
     */
    MakerEntity findByPhoneNumber(String phoneNumber);

    /**
     * 根据微信手机号码查询创客是否存在
     *
     * @param phoneNumber
     * @return
     */
    int findCountByPhoneNumber(String phoneNumber);

    /**
     * 根据手机号码密码查询创客
     *
     * @param phoneNumber
     * @param loginPwd
     * @return
     */
    MakerEntity findByPhoneNumberAndLoginPwd(String phoneNumber, String loginPwd);

    /**
     * 查询当前创客身份证实名认证的照片
     *
     * @param makerEntity
     * @return
     */
    R<IdcardOcrVO> queryIdcardOcr(MakerEntity makerEntity);

    /**
     * 身份证信息获取
     *
     * @param idcardPic
     * @param makerEntity
     * @return
     * @throws Exception
     */
    R<JSONObject> idcardOcr(String idcardPic, MakerEntity makerEntity) throws Exception;

    /**
     * 身份证认证
     *
     * @param idcardVerifyDTO
     * @param makerEntity
     * @return
     */
    R idcardVerify(IdcardVerifyDTO idcardVerifyDTO, MakerEntity makerEntity) throws Exception;

    /**
     * 手机号认证
     *
     * @param makerEntity
     * @return
     * @throws Exception
     */
    R<String> mobileVerify(MakerEntity makerEntity) throws Exception;

    /**
     * 银行卡认证
     *
     * @param bankCardNo
     * @param makerEntity
     * @return
     * @throws Exception
     */
    R<String> bankCardVerify(String bankCardNo, MakerEntity makerEntity) throws Exception;

    /**
     * 活体认证
     *
     * @param makerEntity
     * @return
     * @throws Exception
     */
    R<String> faceOcr(MakerEntity makerEntity) throws Exception;

    /**
     * 活体认证异步回调
     *
     * @param request
     * @return
     * @throws Exception
     */
    R faceOcrNotify(HttpServletRequest request) throws Exception;

    /**
     * 根据userId查询创客
     *
     * @param userId
     * @return
     */
    MakerEntity findByUserId(Long userId);

    /**
     * 上传创客视频
     *
     * @param makerEntity
     * @param applyShortVideo
     * @return
     */
    R<String> uploadMakerVideo(MakerEntity makerEntity, String applyShortVideo);

    /**
     * 根据创客姓名分页查询
     *
     * @param query
     * @param makerName
     * @return
     */
    R<IPage<MakerWorksheetVO>> getMakerName(Query query, String makerName);

    /**
     * 查询当前创客所有实名认证状态
     *
     * @param makerId
     * @return
     */
    R<MakerRealNameAuthenticationStateVO> getRealNameAuthenticationState(Long makerId);

    /**
     * 导入创客数据
     *
     * @param importMakerListDTOList
     * @param enterpriseId
     * @return
     */
    R<String> importMaker(List<ImportMakerListDTO> importMakerListDTOList, Long enterpriseId);

    /**
     * 新增单个创客
     *
     * @param makerAddDto
     * @param enterpriseId
     * @return
     */
    R<String> makerAdd(MakerAddDTO makerAddDto, Long enterpriseId);

    /**
     * 根据身份证号查找创客
     *
     * @param idcardNo
     * @return
     */
    MakerEntity findByIdcardNo(String idcardNo);

    /**
     * 根据创客ID查询创客详情
     *
     * @param makerId
     * @return
     */
    R<EnterpriseMakerDetailVO> queryMakerDetail(Long makerId);

    /**
     * 平台端上传创客授权视频
     *
     * @param makerId
     * @param videoUrl
     * @return
     */
    R<String> saveAdminMakerVideo(Long makerId, String videoUrl);


    /**
     * 查询所有创客
     */
    R getMakerAll(Long makerId, String makerName, IPage<MakerEntity> page);

    /**
     * 读取Excel表获取创客列表
     *
     * @param file
     * @return
     * @throws IOException
     */
    R<List<MakerExcel>> readMakerListExcel(MultipartFile file) throws IOException;

    /**
     * 查询商户关联的创客
     *
     * @param enterpriseId
     * @param makerListIndividualDTO
     * @param page
     * @return
     */
    R<IPage<MakerListIndividualVO>> queryMakerListIndividual(Long enterpriseId, MakerListIndividualDTO makerListIndividualDTO, IPage<MakerListIndividualVO> page);

    /**
     * 根据条件查询所有创客
     *
     * @param enterpriseId
     * @param serviceProviderId
     * @param relationshipType
     * @param certificationState
     * @param keyword
     * @param page
     * @return
     */
    R<IPage<MakerListVO>> queryMakerList(Long enterpriseId, Long serviceProviderId, RelationshipType relationshipType, CertificationState certificationState, String keyword, IPage<MakerListVO> page);

    /**
     * 根据创客支付明细查询创客名称
     *
     * @param payMakerId
     * @return
     */
    String queryMakerName(Long payMakerId);
}

