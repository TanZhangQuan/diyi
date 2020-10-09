package com.lgyun.system.order.vo;

import com.lgyun.system.order.entity.PayMakerEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author jun.
 * @date 2020/7/18.
 * @time 20:55.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PayMakerVO extends PayMakerEntity {

    /**
     * 门征发票代码
     */
    private String voiceTypeNo;

    /**
     * 门征发票号码
     */
    private String voiceSerialNo;

    /**
     * 门征发票Url
     */
    private String makerVoiceUrl;

    /**
     * 门征发票完税证明代码
     */
    private String taxVoiceTypeNo;

    /**
     * 门征发票完税证明号码
     */
    private String  taxVoiceSerialNo;

    /**
     * 完税证明Url
     */
    private String makerTaxUrl;
}
