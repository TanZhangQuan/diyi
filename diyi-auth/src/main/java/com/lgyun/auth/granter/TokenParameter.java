package com.lgyun.auth.granter;

import lombok.Data;
import com.lgyun.common.support.Kv;

/**
 * TokenParameter
 * 不区分key大小写的
 *
 * @author liangfeihu
 * @since 2020/6/6 01:00
 */
@Data
public class TokenParameter {

	private Kv args = Kv.init();

}
