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
 * @ClassName: apiClientTemplate
 * @Description: todo
 * @date 2021/2/15 3:52 下午
 */
public class ApiClientTemplate{

    private BaseInfo baseInfo;

    public ApiClientTemplate(BaseInfo baseInfo) {
        this.baseInfo = baseInfo;
    }

    private  String apiClientTemplate(String desc, List<String> method, Map<String, String> descMap){
        StringBuffer sb = new StringBuffer();
        String time = DateFormat.getDateInstance().format(new Date());
        String basePath = baseInfo.getBasePath();
        String api = baseInfo.getApi();
        String entity = baseInfo.getEntity();
        String entityBeanName = baseInfo.getEntityBeanName();
        String moduleVersion = baseInfo.getModuleVersion();


        sb.append("package "+basePath+api+";\n\n\n");
        sb.append(
                "import com.anta.module.data.common.FeignParam;\n"
                        + "import com.anta.module.data.common.JsonParam;\n"
                        + "import com.anta.module.data.common.Page;\n"
                        + "import com.anta.module.data.common.RestResponse;\n"
                        + "import com.anta.module.data.common.Result;\n"
                        + "import "+basePath+entity+entityBeanName+";\n"
                        + "import org.springframework.cloud.openfeign.FeignClient;\n"
                        + "import org.springframework.web.bind.annotation.GetMapping;\n"
                        + "import org.springframework.web.bind.annotation.PostMapping;\n"
                        + "import org.springframework.web.bind.annotation.RequestBody;\n"
                        + "import org.springframework.web.bind.annotation.RequestParam;\n"
                        + "import java.util.List;\n"
                        + "import java.util.Map;");

        sb.append(
              "/**\n"
            + " * @author lms\n"
            + " * @version 1.0\n"
            + " * @description "+desc+"接口\n"
            + " * @date "+time+"\n"
            + " */");
        sb.append(
                "@FeignClient(name = \""+moduleVersion+"-${"+moduleVersion+".version}/${eapcloud."+moduleVersion+"}/"+(entityBeanName.substring(0, 1).toLowerCase()+entityBeanName.substring(1))+"\")\n");
        sb.append("public interface "+entityBeanName+"ApiClient {\n");
        sb.append(
                "@PostMapping(\"/list\")\n"
                        + "public Page<"+entityBeanName+"> list(@RequestBody FeignParam<"+entityBeanName+"> param);\n");

        sb.append(
                "/**\n"
                + "* 根据传入的ID的信息查询数据\n"
                + "* @param id 选中的数据的ID\n"
                + "* @return 所需要的数据\n"
                + "*/\n"
                + "@PostMapping(\"/get\")\n"
                + "public String get(@RequestParam(\"id\") Long id);");

        sb.append(
                " /**\n"
                + "* 修改数据\n"
                + "* @param param 前端传递的参数\n"
                + "* @return\n"
                + "*/\n"
                + "@PostMapping(\"/update\")\n"
                + "public Result update(@RequestBody JsonParam<"+entityBeanName+"> param);");

        sb.append(
                "/**\n"
                + "* 保存数据\n"
                + "* @param param 前端传入的数据\n"
                + "* @return 成功或失败\n"
                + "*/\n"
                + "@PostMapping(\"/save\")\n"
                + "public Result save(@RequestBody JsonParam<"+entityBeanName+"> param);\n");

        sb.append(
                "/**\n"
                + "*\n"
                + "* 删除选中的数据\n"
                + "*\n"
                + "* @param ids 选中的数据的ID\n"
                + "* @return\n"
                + "*/\n"
                + "@PostMapping(\"/delete\")\n"
                + "public Result delete(@RequestParam(\"ids\") List<Long> ids);");

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
                                + "* @Description "
                                + descMap.get(methodName)
                                + "\n"
                                + "* @Date "
                                + time
                                + "\n"
                                + "*/");
                        sb.append("@PostMapping(\"/"+methodName+"\")");
                        sb.append("public " + returnType + " " + methodName + "(@RequestBody " + param + "){\n");
                        sb.append("}\n\n");
                    });
        }
        sb.append("}\n");
        return sb.toString();
    }

}
