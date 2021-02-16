package com.lms.springboot.code;

import cn.hutool.json.JSONUtil;

import java.text.DateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author lms
 * @PackageName: com.lms.springboot.code
 * @ClassName: EntityTemplate
 * @Description: todo
 * @date 2021/2/15 12:57 上午
 */
public class EntityTemplate{

    private BaseInfo baseInfo;

    public EntityTemplate(BaseInfo baseInfo) {
        this.baseInfo = baseInfo;
    }

    private String entityTemplate(String bean, String sql, String table, String desc){
        StringBuffer sb = new StringBuffer();
        String basePath = baseInfo.getBasePath();
        String api = baseInfo.getApi();
        String entity = baseInfo.getEntity();
        String entityBeanName = baseInfo.getEntityBeanName();
        String moduleVersion = baseInfo.getModuleVersion();
        String clientimpl = baseInfo.getClientimpl();
        String service = baseInfo.getService();
        String pk = baseInfo.getPk();

        String entityBaseBeanName = baseInfo.getEntityBaseBeanName();

        String time = DateFormat.getDateInstance().format(new Date());

        /**head start**/
        sb.append("package "+basePath+entity+";\n");
        sb.append("import javax.persistence.Column;\n");
        sb.append("import javax.persistence.Entity;\n");
        sb.append("import javax.persistence.EntityListeners;\n");
        sb.append("import javax.persistence.GeneratedValue;\n");
        sb.append("import javax.persistence.GenerationType;\n");
        sb.append("import javax.persistence.Id;\n");
        sb.append("import javax.persistence.Table;\n");
        sb.append("import javax.persistence.TableGenerator;\n");
        sb.append("import javax.persistence.Transient;\n");
        sb.append("import com.anta.module.data.jpa.entity.BaseAntaEntity;\n");
        sb.append("import javax.persistence.Transient;\n");
        /** head end* */
        sb.append(
                "/**\n"
                        + " * @author lms\n"
                        + " * @version 1.0\n"
                        + " * @description "+desc+"\n"
                        + " * @date "+time+"\n"
                        + " */");
        sb.append("@EntityListeners(AuditListener.class)\n");
        sb.append("@Entity\n");
        sb.append("@Table(name=\""+table+"\")\n");
        sb.append("public class "+entityBeanName+" extends "+entityBaseBeanName+"<Long> {\n");
        sb.append("private static final long serialVersionUID = 3075133696557657227L;\n");

        sb.append(
                "@Id\n"
                        + "@GeneratedValue(strategy = GenerationType.TABLE, generator = \""+entityBeanName+"_PK\")\n"
                        + "@TableGenerator(name = \""+entityBeanName+"_PK\", table = \"s_pkgenerator\", pkColumnName = \"PkGeneratorName\", valueColumnName = \"PkGeneratorValue\", pkColumnValue = \""+entityBeanName+"_PK\", allocationSize = 1)\n"
                        + "@Column(name = \""+pk+"\")\n"
                        + "protected Long "+pk+";\n");
        Map<String, String> map = JSONUtil.toBean(bean, Map.class);
        Map<String, String> sqlMap = JSONUtil.toBean(sql, Map.class);

        for (String s : map.keySet()) {
            sb.append(
                    "/**" + sqlMap.get(s) + "**/\n"
                            + "@Column(name = \"" + s + "\")\n"
                            + "protected " + map.get(s) + " " + s + ";");
        }
        for (String k : map.keySet()) {
            sb.append(
                    "public String get"+k.substring(0, 1).toUpperCase()+k.substring(1)+"() {\n"
                            + " return "+ k + ";\n"
                            +"}\n"
                            +"public void set"+k.substring(0, 1).toUpperCase()+k.substring(1)+"(String "+k+") {\n"
                            + "  this."+k+" = "+k+";\n"
                            +"}\n");
        }
        return sb.toString();
    }

}
