package com.lgyun.system.user.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author jun.
 * @date 2020/6/29.
 * @time 20:04.
 */
@Data
@ApiModel(value = "MakerEnterpriseRelationVO对象", description = "MakerEnterpriseRelationVO对象")
public class MakerEnterpriseRelationVO {
    private String enterpriseName;
    private String shopUserName;
    private String legalPerson;
    private String legalPersonCard;
    private String contact1Position;
    private String contact1Phone;
    private String socialCreditNo;
    private String bizLicenceUrl;
    private Long enterpriseId;
}
