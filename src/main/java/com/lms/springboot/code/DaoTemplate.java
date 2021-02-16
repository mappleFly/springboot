package com.lms.springboot.code;

import java.text.DateFormat;
import java.util.Date;

/**
 * @author lms
 * @PackageName: com.lms.springboot.code
 * @ClassName: DaoTemplate
 * @Description: todo
 * @date 2021/2/15 1:10 上午
 */
public class DaoTemplate{

    private BaseInfo baseInfo;

    public DaoTemplate(BaseInfo baseInfo) {
        this.baseInfo = baseInfo;
    }

    private String daoTemplate(String desc){
        StringBuffer sb = new StringBuffer();
        String basePath = baseInfo.getBasePath();
        String api = baseInfo.getApi();
        String entity = baseInfo.getEntity();
        String entityBeanName = baseInfo.getEntityBeanName();
        String moduleVersion = baseInfo.getModuleVersion();
        String clientimpl = baseInfo.getClientimpl();
        String service = baseInfo.getService();
        String pathPrefix = baseInfo.getPathPrefix();

        String dao = baseInfo.getDao();


        String time = DateFormat.getDateInstance().format(new Date());
        String daoName = entityBeanName+"Dao";

        sb.append("package "+basePath+dao+";\n\n\n");
        sb.append("import "+basePath+entity+entityBeanName+";\n");
        sb.append("import com.anta.module.data.jpa.dao.JpaDao;\n");
        sb.append("import org.springframework.stereotype.Repository;\n\n\n\n");
        sb.append(
                "/**\n"
                        + " * @ClassName: "+daoName+"\n"
                        + " * @Author: lms\n"
                        + " * @Description: "+desc+" dao\n"
                        + " * @Date: "+time+"\n"
                        + " * @Version: 1.0\n"
                        + " */");
        sb.append("@Repository\n");
        sb.append("public interface "+daoName+" extends JpaDao<"+entityBeanName+",Long> {\n");
        sb.append("}\n");

        return sb.toString();
    }

}
