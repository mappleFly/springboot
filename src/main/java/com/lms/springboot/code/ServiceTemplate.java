package com.lms.springboot.code;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author lms
 * @PackageName: com.lms.springboot.code
 * @ClassName: serviceTemplate
 * @Description: todo
 * @date 2021/2/15 1:35 上午
 */
public class ServiceTemplate{

    private BaseInfo baseInfo;


    public ServiceTemplate(BaseInfo baseInfo) {
        this.baseInfo = baseInfo;
    }

    private String serviceTemplate(String desc, List<String> method, Map<String, String> descMap){
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
        sb.append(
                          "import com.anta.module.data.common.FeignParam;\n"
                        + "import com.anta.module.data.common.Page;\n"
                        + "import com.anta.module.data.common.RestResponse;\n"
                        + "import com.anta.module.data.common.Result;\n"
                        + "import com.anta.module.data.jpa.service.JpaService;\n"
                        + "import "+basePath+entity+entityBeanName+";\n"
                        + "import java.util.List;\n"
                        + "import java.util.Map;\n\n\n");

        sb.append(
                "/**\n"
                        + " * @author lms\n"
                        + " * @version 1.0\n"
                        + " * @description "+desc+"接口\n"
                        + " * @date "+time+"\n"
                        + " */");
        sb.append("public interface "+entityBeanName+"Service extends JpaService<"+entityBeanName+",Long> {\n");
        /**自定义方法**/
        if(!CollectionUtil.isEmpty(method)){
            method.forEach(
                    k -> {
                        Map<String, Object> bean = JSONUtil.toBean(k, Map.class);
                        Object methodName = bean.get("methodName");
                        Object returnType = bean.get("returnType");
                        Object param = bean.get("param");
                        sb.append(
                                "/**\n"
                                        + "* @Author lms\n"
                                        + "* @Version  1.0\n"
                                        + "* @Description "+descMap.get(methodName)+"\n"
                                        + "* @Date "+time+"\n"
                                        + "*/");
                        sb.append("public " + returnType + " " + methodName + "(" + param + "){\n");
                        sb.append("}\n\n");
                    });
        }
        sb.append("}\n");
        return sb.toString();
    }

}
