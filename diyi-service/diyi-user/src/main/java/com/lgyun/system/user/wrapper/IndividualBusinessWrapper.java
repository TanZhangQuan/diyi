package com.lgyun.system.user.wrapper;

import com.lgyun.common.enumeration.BodyType;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.common.tool.SpringUtil;
import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.system.user.entity.IndividualBusinessEntity;
import com.lgyun.system.user.service.IEnterpriseReportService;
import com.lgyun.system.user.vo.IndividualBusinessVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author liangfeihu
 * @since 2020-07-02 17:44:02
 */
public class IndividualBusinessWrapper extends BaseEntityWrapper<IndividualBusinessEntity, IndividualBusinessVO> {

	private static IEnterpriseReportService enterpriseReportService;

	static {
		enterpriseReportService = SpringUtil.getBean(IEnterpriseReportService.class);
	}

	public static IndividualBusinessWrapper build() {
        return new IndividualBusinessWrapper();
    }

	@Override
	public IndividualBusinessVO entityVO(IndividualBusinessEntity individualBusiness) {

		if (individualBusiness == null){
			return null;
		}

		//查询年审信息
		String reportResultFiles = enterpriseReportService.findReportResultFiles(BodyType.INDIVIDUALBUSINESS, individualBusiness.getId());
		IndividualBusinessVO individualBusinessVO = BeanUtil.copy(individualBusiness, IndividualBusinessVO.class);
		individualBusinessVO.setReportResultFiles(reportResultFiles);

		return individualBusinessVO;
	}

}
