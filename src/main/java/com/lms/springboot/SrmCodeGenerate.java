package com.lms.springboot;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author lms
 * @PackageName: com.lms.springboot
 * @ClassName: SrmCodeGenerate
 * @Description: srm代码生成
 * @date 2021/2/14 10:45 下午
 */
public class SrmCodeGenerate {
    public static void main(String[] args) {

        //FileWriter
        String sql = "{returnappnum:'退货申请号'," +
                "boxcode:'箱码'" +
                "asncode:'asn'" +
                "materialcode:'物料编码(货号)'" +
                "size:'尺码'}";

        String bean = "{returnappnum:'String'" +
                "returnapprow:'Integer'" +
                "boxcode:'String'" +
                "asncode:'String'" +
                "materialCode:'String'" +
                "size:'Dictionary'}";

        String desc = "退货明细";
        String table = "d_cp_returndetails";


  }
}
