package com.lgyun.system.user.wrapper;

import com.lgyun.common.enumeration.BodyType;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.common.tool.SpringUtil;
import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.system.user.entity.IndividualEnterpriseEntity;
import com.lgyun.system.user.service.IEnterpriseReportService;
import com.lgyun.system.user.vo.IndividualEnterpriseVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author tzq
 * @since 2020-07-02 17:44:02
 */
public class IndividualEnterpriseWrapper extends BaseEntityWrapper<IndividualEnterpriseEntity, IndividualEnterpriseVO> {

	private static IEnterpriseReportService enterpriseReportService;

	static {
		enterpriseReportService = SpringUtil.getBean(IEnterpriseReportService.class);
	}

    public static IndividualEnterpriseWrapper build() {
        return new IndividualEnterpriseWrapper();
    }

	@Override
	public IndividualEnterpriseVO entityVO(IndividualEnterpriseEntity individualEnterprise) {

		if (individualEnterprise == null){
			return null;
		}

		//查询年审信息
		String reportResultFiles = enterpriseReportService.findReportResultFiles(BodyType.INDIVIDUALENTERPRISE, individualEnterprise.getId());
		IndividualEnterpriseVO individualEnterpriseVO = BeanUtil.copy(individualEnterprise, IndividualEnterpriseVO.class);
		individualEnterpriseVO.setReportResultFiles(reportResultFiles);

		return individualEnterpriseVO;
	}

}
