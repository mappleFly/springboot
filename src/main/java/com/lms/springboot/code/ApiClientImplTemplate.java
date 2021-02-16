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
 * @ClassName: apiClientImplTemplate
 * @Description: todo
 * @date 2021/2/15 4:36 下午
 */
public class ApiClientImplTemplate{

    private BaseInfo baseInfo;

    public ApiClientImplTemplate(BaseInfo baseInfo) {
        this.baseInfo = baseInfo;
    }

    private  String apiClientImplTemplate(String desc, List<String> method, Map<String, String> descMap){
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

        String path = entityBeanName.substring(0, 1).toLowerCase()+entityBeanName.substring(1);

        sb.append("package "+basePath+clientimpl+";\n\n\n");
        sb.append(
                "import com.anta.module.data.common.FeignParam;\n"
                        + "import com.anta.module.data.common.JsonParam;\n"
                        + "import com.anta.module.data.common.Page;\n"
                        + "import com.anta.module.data.common.RestResponse;\n"
                        + "import com.anta.module.data.common.Result;\n"
                        + "import com.anta.module.data.jpa.utils.DataUtils;\n"
                        + "import com.anta.module.mvc.controller.CloudController;\n"
                        + "import com.anta.module.util.CollectionUtils;\n"
                        + "import "+basePath+api+entityBeanName+"ApiClient;\n"
                        + "import "+basePath+entity+entityBeanName+";\n"
                        + "import "+basePath+service+entityBeanName+"Service;\n"
                        + "import org.springframework.beans.factory.annotation.Autowired;\n"
                        + "import org.springframework.web.bind.annotation.GetMapping;\n"
                        + "import org.springframework.web.bind.annotation.PostMapping;\n"
                        + "import org.springframework.web.bind.annotation.RequestBody;\n"
                        + "import org.springframework.web.bind.annotation.RequestMapping;\n"
                        + "import org.springframework.web.bind.annotation.RequestParam;\n"
                        + "import org.springframework.web.bind.annotation.RestController;\n"
                        + "import java.util.Arrays;\n"
                        + "import java.util.Calendar;\n"
                        + "import java.util.HashMap;\n"
                        + "import java.util.List;\n"
                        + "import java.util.Map;\n"
                        + "import java.util.Objects;");

        sb.append(
                "/**\n"
                        + " * @author lms\n"
                        + " * @version 1.0\n"
                        + " * @description "+desc+"接口实现\n"
                        + " * @date "+time+"\n"
                        + " */");
        sb.append("@RestController\n");
        sb.append("@RequestMapping(\"${eapcloud."+moduleVersion+"}/"+path+"\")");
        sb.append(
                "public class "+entityBeanName+"ApiClientImpl extends CloudController implements "+entityBeanName+"Client {\n\n\n");
        sb.append("@Autowired\n"+
                "private "+entityBeanName+"Service "+path+"Service;\n\n");
        sb.append("@Autowired(required = false)\n"
                +"protected UserAuthGroupServiceClient userAuthGroupServiceClient;\n\n");

        sb.append("@Override\n");
        sb.append(
                "public Page<"+entityBeanName+"> list(@RequestBody FeignParam<"+entityBaseBeanName+"> param) {\n"
                        + "        Map<String, Object> searchParams = param.getParams();\n"
                        + "        searchParams.put(\"EQ_isDelete\",0);\n"
                        + "        Map<String, Object> userGroupParams = userAuthGroupServiceClient.buildAuthFieldParamsWithBlank(\n"
                        + "                new UserAuthGroupParam(getClientCode(), getUserCode(), "+entityBeanName+".class));\n"
                        + "        searchParams.putAll(userGroupParams);\n"
                        + "        return "+path+"Service.findPage(param);\n"
                        + "    }");

        sb.append(
                "@Override\n"
                        + "    public String get(@RequestParam(\"id\") Long id) {\n"
                        + "        "+entityBeanName+" model = "+path+"Service.findById(id);\n"
                        + "        if (model == null) {\n"
                        + "            return dealJson(false, \"数据不存在：\");\n"
                        + "        }\n"
                        + "        return dealJson(true, DataUtils.toJson(model, \""+path+"\"));\n"
                        + "    }");

        sb.append(
                "@Override\n"
                        + "    public Result save(@RequestBody JsonParam<"+entityBeanName+"> param) {\n"
                        + "        "+entityBeanName+" model = param.getModel();\n"
                        + "        Long userId = getUserId();\n"
                        + "        String userName = getUserName();\n"
                        + "        model.setCreateUserId(userId);\n"
                        + "        model.setCreateUserName(userName);\n"
                        + "        model.setCreateTime(Calendar.getInstance());\n"
                        + "        "+path+"Service.save(model);\n"
                        + "        return Result.success();\n"
                        + "    }");

        sb.append(
                "@Override\n"
                        + "    public Result update(@RequestBody JsonParam<"+entityBeanName+"> param) {\n"
                        + "        "+entityBeanName+" model = param.getModel();\n"
                        + "        Long userId = getUserId();\n"
                        + "        String userName = getUserName();\n"
                        + "        model.setModifyUserId(userId);\n"
                        + "        model.setModifyUserName(userName);\n"
                        + "        model.setModifyTime(Calendar.getInstance());\n"
                        + "        "+path+"Service.save(model);\n"
                        + "        return Result.success();\n"
                        + "    }");

        sb.append(
                "@Override\n"
                        + "    public Result delete(@RequestParam(\"ids\") List<Long> ids) {\n"
                        + "        Map<String,Object> param = new HashMap<>();\n"
                        + "        param.put(\"IN_"+pk+"\",ids);\n"
                        + "        List<"+entityBeanName+"> list= "+path+"Service.findAll(param);\n"
                        + "        if(!CollectionUtils.isEmpty(list)){\n"
                        + "            list.forEach(k->k.setIsDelete(1));\n"
                        + "            "+path+"Service.saveAll(list);\n"
                        + "        }\n"
                        + "        return Result.success();\n"
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
                        sb.append("@Override");
                        sb.append(
                                "public " + returnType + " " + methodName + "(@RequestBody " + param + "){\n");
                        sb.append("  return "+path+"Service."+methodName+"("+param+");\n");
                        sb.append("}\n\n");
                    });
        }
        sb.append("}\n");
        return sb.toString();
    }

}
