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
 * @ClassName: controllerTemplate
 * @Description: todo
 * @date 2021/2/15 4:54 下午
 */
public class ControllerTemplate extends BaseInfo {

    private BaseInfo baseInfo;

    public ControllerTemplate(BaseInfo baseInfo) {
        this.baseInfo = baseInfo;
    }

    private String controllerTemplate(String desc, List<String> method, Map<String, String> descMap){
        StringBuffer sb = new StringBuffer();
        String time = DateFormat.getDateInstance().format(new Date());
        String basePath = baseInfo.getBasePath();
        String api = baseInfo.getApi();
        String entity = baseInfo.getEntity();
        String entityBeanName = baseInfo.getEntityBeanName();
        String moduleVersion = baseInfo.getModuleVersion();
        String clientimpl = baseInfo.getClientimpl();
        String service = baseInfo.getService();
        String pathPrefix = baseInfo.getPathPrefix();

        String controller = baseInfo.getController();

        String path = entityBeanName.substring(0, 1).toLowerCase()+entityBeanName.substring(1);

        sb.append("package "+basePath+controller+";\n\n\n");
        sb.append(
                "import com.anta.module.data.common.FeignParam;\n"
                        + "import com.anta.module.data.common.Page;\n"
                        + "import com.anta.module.data.common.RestResponse;\n"
                        + "import com.anta.module.data.common.Result;\n"
                        + "import com.anta.module.util.StringUtils;\n"
                        + "import com.anta.srm.commons.utils.CloudReportController;\n"
                        + "import "+basePath+api+"."+entityBeanName+"ApiClient;\n"
                        + "import "+basePath+entity+entityBeanName+";\n"
                        + "import org.springframework.beans.factory.annotation.Autowired;\n"
                        + "import org.springframework.web.bind.annotation.GetMapping;\n"
                        + "import org.springframework.web.bind.annotation.PostMapping;\n"
                        + "import org.springframework.web.bind.annotation.RequestBody;\n"
                        + "import org.springframework.web.bind.annotation.RequestMapping;\n"
                        + "import org.springframework.web.bind.annotation.RequestParam;\n"
                        + "import org.springframework.web.bind.annotation.RestController;\n"
                        + "import java.util.ArrayList;\n"
                        + "import java.util.List;\n"
                        + "import java.util.Map;");

        sb.append(
                "/**\n"
                        + " * @author lms\n"
                        + " * @version 1.0\n"
                        + " * @description "+desc+"接口实现\n"
                        + " * @date "+time+"\n"
                        + " */");
        sb.append("@RestController\n");
        sb.append("@RequestMapping(\"/"+pathPrefix+"/"+path+"\")\n");
        sb.append(
                "public class "+entityBeanName+"Controller extends CloudController {\n\n\n");

        sb.append("@Autowired(required = false)\n"
                +"protected "+entityBeanName+"ApiClient "+path+"ApiClient;\n\n");

        sb.append(
                "/**\n"
                        + "     * @author lms\n"
                        + "     * @descripton 列表\n"
                        + "     * @date "+time+"\n"
                        + "     **/\n"
                        + "    @PostMapping(\"/list\")\n"
                        + "    public Page<"+entityBeanName+"> list(){\n"
                        + "        Map<String, Object> searchParams = buildParams();\n"
                        + "        Page<"+entityBeanName+"> page = new Page<>(getStart(), getLimit(), getSort(), getDir());\n"
                        + "        FeignParam<"+entityBeanName+"> feign=new FeignParam<>();\n"
                        + "        feign.setPage(page);\n"
                        + "        feign.setParams(searchParams);\n"
                        + "        return "+path+"ApiClient.list(feign);\n"
                        + "    }");

        sb.append(
                " /**\n"
                        + "     *\n"
                        + "     * 删除选中的数据\n"
                        + "     * @param ids 选中的数据的ID\n"
                        + "     * @return\n"
                        + "     */\n"
                        + "    @PostMapping(\"/delete\")\n"
                        + "    public Result delete(@RequestParam(\"ids\") String ids) {\n"
                        + "        List<Long> list = new ArrayList<>();\n"
                        + "        if(!StringUtils.isEmpty(ids)){\n"
                        + "            String[] str = ids.split(\",\");\n"
                        + "            for(String key:str){\n"
                        + "                list.add(Long.parseLong(key));\n"
                        + "            }\n"
                        + "        }\n"
                        + "        return "+path+"ApiClient.delete(list);\n"
                        + "    }");

        sb.append(
                " /**\n"
                        + "     * 新建\n"
                        + "     * \n"
                        + "     * @return \"NONE\"\n"
                        + "     */\n"
                        + "    @PostMapping(value = \"/save\")\n"
                        + "    public Result save(@RequestBody JsonParam<"+entityBeanName+"> param) {\n"
                        + "        return "+path+"ApiClient.save(param);\n"
                        + "    }");
        sb.append(
                "/**\n"
                        + "     * 修改\n"
                        + "     * \n"
                        + "     * @return \"NONE\"\n"
                        + "     */\n"
                        + "    @PostMapping(value = \"/update\")\n"
                        + "    public Result update(@RequestBody JsonParam<"+entityBeanName+"> param) {\n"
                        + "        return "+path+"ApiClient.update(param);\n"
                        + "    }\n");

        sb.append(
                "/**\n"
                        + "     * 获取数据\n"
                        + "     * \n"
                        + "     * @return\n"
                        + "     */\n"
                        + "    @PostMapping(value = \"/get\")\n"
                        + "    public Result get(Long id) {\n"
                        + "        "+entityBeanName+" model = "+path+"ApiClient.get(id);\n"
                        + "        if (model == null) {\n"
                        + "            return Result.error(getText(\"common.valid.dataNotExist\"));\n"
                        + "        }\n"
                        + "        return Result.success(DataUtils.toJson(model, \""+path+"\"));\n"
                        + "    }");

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
                        sb.append("@PostMapping(value = \"/"+methodName+"\")");
                        sb.append(
                                "public " + returnType + " " + methodName + "(@RequestBody " + param + "){\n");
                        sb.append("  return " + path + "ApiClient." + methodName + "(" + param + ");\n");
                        sb.append("}\n\n");
                    });
        }
        sb.append("}\n");
        return sb.toString();
    }

}
