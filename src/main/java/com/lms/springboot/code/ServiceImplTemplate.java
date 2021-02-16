package com.lms.springboot.code;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author lms
 * @PackageName: com.lms.springboot.code
 * @ClassName: ServiceImplTemplate
 * @Description: todo
 * @date 2021/2/15 1:29 上午
 */
public class ServiceImplTemplate extends BaseInfo{

    private BaseInfo baseInfo;

    public ServiceImplTemplate(BaseInfo baseInfo) {
        this.baseInfo = baseInfo;
    }

    private String serviceTemplate(String desc, List<String> method){
        StringBuffer sb = new StringBuffer();
        String time = DateFormat.getDateInstance().format(new Date());
        String basePath = baseInfo.getBasePath();
        String api = baseInfo.getApi();
        String entity = baseInfo.getEntity();
        String entityBeanName = baseInfo.getEntityBeanName();
        String moduleVersion = baseInfo.getModuleVersion();
        String clientimpl = baseInfo.getClientimpl();
        String service = baseInfo.getService();
        String pk = baseInfo.getPk();

        String entityBaseBeanName = baseInfo.getEntityBaseBeanName();

        sb.append("package "+basePath+service+";\n\n\n");
        sb.append("import org.springframework.beans.factory.annotation.Autowired;\n");
        sb.append("import org.springframework.beans.factory.annotation.Qualifier;\n");
        sb.append("import org.springframework.stereotype.Service;\n\n\n");
        sb.append(
                "/**\n"
                        + " * @author lms\n"
                        + " * @version 1.0\n"
                        + " * @description "+desc+"接口实现\n"
                        + " * @date "+time+"\n"
                        + " */");
        sb.append("@Service\n");
        sb.append("public class ReturnDetailsServiceImpl extends JpaServiceImpl<ReturnDetails, Long> implements ReturnDetailsService {\n");
        /**自定义方法**/
        if(!CollectionUtil.isEmpty(method)){
            method.forEach(k->{
                Map<String, Object> bean = JSONUtil.toBean(k, Map.class);
                Object methodName = bean.get("methodName");
                Object returnType = bean.get("returnType");
                Object param = bean.get("param");
                Object returnRs = bean.get("returnRs");
                sb.append("@Override\n");
                sb.append("public "+returnType+" "+methodName+"("+param+"){\n");
                if(Objects.nonNull(returnRs)){
                    sb.append("return "+returnRs);
                }
                sb.append("}\n\n");
            });
        }
        sb.append("}\n");
        return sb.toString();
    }

}
