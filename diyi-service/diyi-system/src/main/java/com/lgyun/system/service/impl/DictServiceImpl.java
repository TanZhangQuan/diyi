package com.lgyun.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgyun.common.exception.CustomException;
import com.lgyun.common.node.ForestNodeMerger;
import com.lgyun.common.tool.Func;
import com.lgyun.common.tool.StringPool;
import com.lgyun.system.entity.Dict;
import com.lgyun.system.mapper.DictMapper;
import com.lgyun.system.service.IDictService;
import com.lgyun.system.vo.DictVO;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static com.lgyun.common.cache.CacheNames.DICT_LIST;
import static com.lgyun.common.cache.CacheNames.DICT_VALUE;

/**
 * 服务实现类
 *
 * @author liangfeihu
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements IDictService {

    @Override
    public IPage<DictVO> selectDictPage(IPage<DictVO> page, DictVO dict) {
        return page.setRecords(baseMapper.selectDictPage(page, dict));
    }

    @Override
    public List<DictVO> tree() {
        return ForestNodeMerger.merge(baseMapper.tree());
    }

    @Override
    @Cacheable(cacheNames = DICT_VALUE, key = "#code+'_'+#dictKey")
    public String getValue(String code, Integer dictKey) {
        return Func.toStr(baseMapper.getValue(code, dictKey), StringPool.EMPTY);
    }

    @Override
    @Cacheable(cacheNames = DICT_LIST, key = "#code")
    public List<Dict> getList(String code) {
        return baseMapper.getList(code);
    }

    @Override
    @CacheEvict(cacheNames = {DICT_LIST, DICT_VALUE}, allEntries = true)
    public boolean submit(Dict dict) {
        LambdaQueryWrapper<Dict> lqw = Wrappers.<Dict>query().lambda().eq(Dict::getCode, dict.getCode()).eq(Dict::getDictKey, dict.getDictKey());
        Integer cnt = baseMapper.selectCount((Func.isEmpty(dict.getId())) ? lqw : lqw.notIn(Dict::getId, dict.getId()));
        if (cnt > 0) {
            throw new CustomException("当前字典键值已存在!");
        }
        return saveOrUpdate(dict);
    }

    @Override
	@Transactional(rollbackFor = Exception.class)
    public void saveList() {
        String[] list = new String[]{"技术服务", "技术咨询", "软件开发服务", "软件咨询服务", "软件维护服务", "软件测试服务", "电路设计服务", "电路测试服务",
                "相关电路技术支持服务", "信息系统服务", "业务流程管理服务", "信息系统增值服务", "工程设计服务", "专业设计服务", "文印晒图服务", "其他设计服务", "知识产权服务", "广告代理服务",
                "展览服务", "装卸搬运服务", "文化服务", "体育服务", "咨询服务", "市场推广", "渠道搭建及使用", "音视频服务", "教育信息咨询", "其他企业管理服务", "家政服务（非员工制）"};
        String[] code = new String[]{"现代服务*技术服务", "研发技术服务＊技术咨询", "信息技术服务*软件开发服务", "信息技术服务＊软件咨询服务", "信息技术服务＊软件维护服务", "信息技术服务＊软件测试服务",
                "信息技术服务＊电路设计服务", "信息技术服务＊电路测试服务", "信息技术服务＊相关电路技术支持服务", "信息技术服务＊信息系统服务", "信息技术服务＊业务流程管理服务", "信息技术服务＊信息系统增值服务",
                "设计服务＊工程设计服务", "设计服务＊专业设计服务", "设计服务＊文印晒图服务", "设计服务＊其他设计服务", "设计服务＊知识产权服务", "广告服务＊广告代理服务", "会议展览服务＊展览服务",
                "物流辅助服务*装卸搬运服务", "生活服务＊文化服务", "生活服务＊体育服务", "其他咨询服务*咨询服务", "现代服务＊市场推广", "现代服务＊渠道搭建及使用", "现代服务＊音视频服务", "其他咨询服务*教育信息咨询",
                "企业管理服务*其他企业管理服务", "现代服务*家政服务"};
        String[] describe = new String[]{"指气象服务、地震服务、海洋服务、测绘服务、城市规划、环境与生态监测服务等专项技术服务。", "提供信息、建议、策划、顾问等服务的活动，包括软件、技术等方面的咨询；对技术项目提供可行性论证、技术预测和测试、技术培训、专题技术调查、分析报告和专业知识咨询等。",
                "对基础软件、应用软件、嵌入式软件等软件提供的开发服务。", "对基础软件、应用软件、嵌入式软件等软件提供的开发服务。", "对基础软件、应用软件、嵌入式软件等软件提供的开发服务。",
                "对基础软件、应用软件、嵌入式软件等软件提供的开发服务。", "指提供集成电路、电子电路产品等提供的设计服务。", "指对集成电路、电子电路产品等提供的测试服务。", "指对集成电路、电子电路产品等提供的技术支持服务。",
                "是指提供信息系统集成、网络管理、网站内容维护、桌面管理与维护、信息系统应用、基础信息技术管理平台整合、信息技术基础设施管理、数据中心、托管中心、信息安全服务、在线杀毒、虚拟主机等业务活动。包括网站对非自有的网络游戏提供的网络运营服务。",
                "依托信息技术提供的人力资源管理、财务经济管理、审计管理、税务管理、物流信息管理、经营信息管理和呼叫中心等服务的活动。","利用信息系统资源为用户附加提供的信息技术服务。包括数据处理、分析和整合、数据库管理、数据备份、数据存储、容灾服务、电子商务平台等。",
                "为房屋建筑物工程、煤炭工程、化工石化医药工程、石油天然气工程、电力工程、冶金工程、机械工程、商业工程、仓储工程、粮食工程、核工业工程、电子通信广电工程、轻纺工程、建材工程、铁道工程、公路工程、水运工程、民航工程、市政工程、农林工程、水利工程、海洋工程、体育及休闲娱乐工程、室内装饰、专项工程等提供的设计服务。",
                "工业设计、造型设计、服装设计、环境设计、平面设计、包装设计、动漫设计、网游设计、展示设计、网站设计、机械设计、广告设计等。", "文印晒图服务", "创意策划等其他设计服务", "指处理知识产权事务的业务活动。包括对专利、商标、著作权、软件、集成电路布图设计的登记、鉴定、评估、认证、检索服务。",
                "指广告经营者接受广告或广告发布者委托，从事广告媒介安排等经营活动。", "为博览会、专业技术产品展览、生活消费品展览、文化产品展览、进出口交易展会、一般产品展会、产品订货展会等提供的展览服务。",
                "使用装卸搬运工具或人力、畜力将货物在运输工具之间、装卸现场之间或者运输工具与装卸现场之间进行装卸和搬运的业务活动。", "为满足社会公众文化生活需求提供的各种服务。包括文艺创作、文艺表演、文化比赛、图书馆的图书和资料借阅、档案馆的档案管理、文物及非物质遗产保护、组织举办宗教活动、科技活动、文化活动。",
                "组织举办体育比赛、体育表演、体育活动，提供体育训练、体育指导、体育管理的业务活动。", "提供信息、建议、策划、顾问等服务的活动。包括翻译服务、市场调查服务、金融、软件、技术、财务、税收、法律、内部管理、业务运作、流程管理、健康等方面的咨询。",
                "通过线上推广和线下推广等方式提高甲方（包括但不限于甲方App\\网站等）知名度和点击量，其中线上推广方式包括但不限于在各类社交平台或线上社群发帖、转帖、发优惠券等，线下推广方式包括但不限于贴海报、发传单、发赠品/试用品等。",
                "乙方按照甲方约定具体服务事项和成果要求，将渠道搭建及使用事项进行精准配置和安排，包含但不限于协调安排项目服务人员通过个人对特定市场的影响力，使用其个人渠道资源根据甲方要求促成指定项目的具体成果。",
                "主播挑选、主播培训、输出直播内容。甲方有权要求乙方自行提供、配置与直播服务有关的环境和设备并承担与此直接相关的费用，包括但不限于网络、电脑、麦克风、音视频装置、设备等。",
                "提供教育文化领域的专业信息咨询、指导建议、策划教育资讯活动等。", "提供教育文化领域的专业信息咨询、指导建议、策划教育资讯活动等。", "提供总部管理、市场管理、日常综合管理等服务。", "提供居家清洁护理服务。"};
        System.out.println(list.length+"--------"+code.length+"*****************"+describe.length);
        Dict dict = null;
        for (int i = 0; i < list.length; i++) {
            dict = new Dict();
            dict.setCode("industry");
            dict.setParentId(1319927704303849587L);
            dict.setDictKey(i + 1);
            dict.setDictValue(list[i]);
            dict.setSort(i + 1);
            dict.setStatus(1);
            dict.setDescribes(describe[i]);
            dict.setRemark(code[i]);
            dict.setIsDeleted(0);
            dict.setCreateTime(new Date());
            dict.setUpdateTime(new Date());
            baseMapper.insert(dict);
        }
    }

    @Override
    public List<Dict> getParentList(Long parentId) {
        return baseMapper.getParentList(parentId);
    }

    @Override
    public Dict getDict(String code) {
        return baseMapper.getDict(code);
    }

}
