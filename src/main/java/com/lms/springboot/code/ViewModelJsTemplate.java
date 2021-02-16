package com.lms.springboot.code;

import cn.hutool.json.JSONUtil;

import java.util.Map;

/**
 * @author lms
 * @PackageName: com.lms.springboot.code
 * @ClassName: ViewModelJsTemplate
 * @Description: todo
 * @date 2021/2/16 12:09 上午
 */
public class ViewModelJsTemplate{

    private BaseInfo baseInfo;

    public ViewModelJsTemplate(BaseInfo baseInfo) {
        this.baseInfo = baseInfo;
    }

    private String viewModelJsTemplate(String desc, Map<String,String> store, String sql, String bean){
        StringBuffer sb = new StringBuffer();
        String basePath = baseInfo.getBasePath();
        String api = baseInfo.getApi();
        String pathPrefix = baseInfo.getPathPrefix();
        String entityBeanName = baseInfo.getEntityBeanName();
        String moduleName = baseInfo.getModuleName();
        String jsPrefix = baseInfo.getJsPrefix();
        String entityBaseBeanName = baseInfo.getEntityBaseBeanName();
        String pk = baseInfo.getPk();

        String path = entityBaseBeanName.substring(0,1).toLowerCase()+entityBeanName.substring(1);

        sb.append("/**\n" +
                " * @class {Purchasing.stock.ReturnDetailsViewModel}\n" +
                " * @extend {Ext.ux.app.ViewModel}\n" +
                " * "+desc+"\n" +
                " */");
        sb.append(
                "Ext.define('"+jsPrefix+"."+entityBeanName+"ViewModel',{\n"
                        + "\textend:'Ext.ux.app.ViewModel',\n"
                        + "    alias:'viewmodel."+path+"ViewModel',\n"
                        + "    requires:[\n"
                        + "   \t\"Ext.ux.form.DateTimeField\",\n"
                        + "   \t\"Ext.srm.ux.UxFileUtils\",\n"
                        + "   \t\"Ext.srm.form.PurchasingOrganizationComboGrid\",\n"
                        + "   \t\"Ext.srm.form.PurchasingGroupComboGrid\",\n"
                        + "   \t\"Ext.srm.form.AntaTextField\"\n"
                        + "    ],");
        sb.append("config:{\n");
        sb.append("   stores:{\n");
        /***数据字典***/
        store.keySet().forEach(
                k -> sb.append(
                        k+"Store:Ext.create(\"Ext.srm.dictionary.DictionaryStore\", {\n"
                                + "\t\t\t\tgroupCode:\""+store.get(k)+"\"\n"
                                + "\t\t\t}),"));
        sb.append("},\n");

        /**data**/
        sb.append("data: {\n");
        sb.append("dealUrl: path_"+moduleName+" + '/"+pathPrefix+"/"+path+"',\n");
        sb.append("isExtend:true,\n");
        sb.append("moduleName: $('"+path+".title'),\n");
        sb.append("vp_triggerField: '触发字段'\n");
        sb.append("vp_hideListBtn: [],//add,view,edit\n");
        sb.append("vp_billTypeCode: '"+path.toUpperCase()+"'");
        sb.append("vp_listEditStateFn: [{\n" +
                "\t\t\t\t\"edit\": function (r) {\n" +
                "\t\t\t\t\treturn true;\n" +
                "\t\t\t\t}\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"view\": true\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"delete\": function (r) {\n" +
                "\t\t\t\t\treturn true;\n" +
                "\t\t\t\t}\n" +
                "\t\t\t}],\n");
        sb.append("vp_activeTab:  0,\n");
        sb.append("vp_tabHeight:300,\n");
        sb.append("maximized : true,\n");
        sb.append("playListMode: 'normal',\n");
        sb.append("initStatesStr:'',\n");
        sb.append("\t\tcontrollerClassName: '"+jsPrefix+"."+path+"Controller',\n");
        sb.append("\t\tsw_isShowStatus: true,\n");
        sb.append("sw_searchWin : {\n" +
                "\t\t\twidth : 900,\n" +
                "\t\t\theight : 450\n" +
                "\t\t},\n");
        sb.append("sw_Width: 800,\n");
        sb.append("sw_columnWidth: '0.5',\n");
        sb.append("isAudit: false,\n");
        sb.append("vp_hideSubTab : true,//list隐藏子标签\n");
        sb.append("vp_sm :{\n" + "\t\t\tsingleSelect:false\n" + "\t\t},\n");
        sb.append("\t\tmethodName: 'list',\n");
        sb.append(
                "vp_addListBtn: [{\n"
                        + "\t\t\tname: 'priceRefresh',\n"
                        + "\t\t\ttext: \"DEMO\",\n"
                        + "\t\t\tindex: 2,\n"
                        + "\t\t\ticonCls: 'icon-edit',\n"
                        + "\t\t\tbuild : '#{power.priceRefresh}',\n"
                        + "\t\t\thandler: 'priceRefreshHandle'\n"
                        + "\t\t}],");
        sb.append("vp_gridStore:  {\n" +
                "\t\t\tidProperty: '"+pk+"',\n" +
                "\t\t\turl:  '#{dealUrl}/list',\n" +
                "\t\t\ttimeout:9999999,\n" +
                "\t\t\tautoLoad:false,\n" +
                "\t\t\tsort: 'createTime',\n" +
                "\t\t\tdir: 'desc',\n" +
                "\t\t\tlisteners: {\n" +
                "                load: function(st, rds, opts) {\n" +
                "                    //填入列头\n" +
                "\n" +
                "                }\n" +
                "            }\n" +
                "\t\t},");

        /**列表属性***/
        sb.append("vp_gridColumn: [");
        Map<String, String> map = JSONUtil.toBean(sql, Map.class);
        Map<String, String> beanType = JSONUtil.toBean(bean, Map.class);
        map.keySet().forEach(k->{
            sb.append("\t\t\t{Qheader: \""+map.get(k)+"\"    , header: $(\""+path+"."+k+"\"), dataIndex: \""+k+"\", width: 100,tipable:true},\n");
        });
        sb.append("\t\t],\n");

        sb.append("ew_height:250,\n");
        sb.append("\tew_columnWidth:'0.33',\n");

        /**编辑form表单**/
        sb.append("ew_editFormItems : [\n");

        sb.append(
                "{\n"
                        + "\t\t\t\t\txtype:\"hidden\",\n"
                        + "\t\t\t\t\tfieldLabel:$(\""+path+"."+pk+"\"),\n"
                        + "\t\t\t\t\tname:\"model."+pk+"\"\n"
                        + "\t\t\t\t}");

        beanType
                .keySet()
                .forEach(
                        k -> {
                            String type = beanType.get(k);
                            if (type.equals("Dictionary")) {
                                // 数据字典
                                sb.append(
                                        "{\n"
                                                + "\t\t\t\tQfieldLabel:\""+ map.get(k) + "\",\n"
                                                + "\t\t\t\tfieldLabel:$(\"" + path + "." + k + "\"),\n"
                                                + "\t\t\t\tname:\"model." + k + "\",\n"
                                                + "\t\t\t\txtype: \"datadictComboGrid\",\n"
                                                + "\t\t\t\tclearable : true,\n"
                                                + "\t\t\t\tbind: {\n"
                                                + "\t\t\t\t\tstore: '{" + store.get(k) + "}'\n"
                                                + "\t\t\t\t},\n"
                                                + "\t\t\t\tdisplayField: \"itemName\",\n"
                                                + "\t\t\t\tvalueField: 'itemCode',\n"
                                                + "\t\t\t\t//displayValue: 'itemCode',\n"
                                                + "\t\t\t\tinnerTpl: true,\n"
                                                + "\t\t\t\tallowBlank:false,\n"
                                                + "\t\t\t}");
                            } else {
                                sb.append(
                                        "{\n"
                                                + "\t\t\t\t\tQfieldLabel:\""+map.get(k)+"\",\n"
                                                + "\t\t\t\t\tfieldLabel:$(\""+path+"."+k+"\"),\n"
                                                + "\t\t\t\t\tname:\"model."+k+"\",\n"
                                                + "        \tallowBlank:true,\n"
                                                + "        \tew_columnWidth: .2,\n"
                                                + "\t\t\t\t\tmaxLength:150\n"
                                                + "\t\t\t\t}");
                            }
                        });
        sb.append("{\n" +
                "\t\t\t\t\tQfieldLabel:\"创建人id\",\n" +
                "\t\t\t\t\tfieldLabel:$(\""+path+".createuserid\"),\n" +
                "\t\t\t\t\tname:\"model.createUserId\",\n" +
                "\t\t\t\t\thidden: true,\n" +
                "\t\t\t\t\tmaxLength:10\n" +
                "\t\t\t\t},{\n" +
                "\t\t\t\t\tQfieldLabel:\"创建人名字\",\n" +
                "\t\t\t\t\tfieldLabel:$(\""+path+".createusername\"),\n" +
                "\t\t\t\t\tname:\"model.createUserName\",\n" +
                "\t\t\t\t\thidden: true,\n" +
                "\t\t\t\t\tmaxLength:300\n" +
                "\n" +
                "\t\t\t\t},{\n" +
                "\t\t\t\t\tQfieldLabel:\"创建时间\",\n" +
                "\t\t\t\t\tfieldLabel:$(\""+path+".createtime\"),\n" +
                "\t\t\t\t\tname:\"model.createTime\",\n" +
                "\t\t\t\t\tanchor:\"90%\",\n" +
                "\t\t\t\t\txtype:\"datefield\",\n" +
                "\t\t\t\t\thidden: true,\n" +
                "\t\t\t\t\tformat:\"Y-m-d H:i:s\"\n" +
                "\t\t\t\t},{\n" +
                "\t\t\t\t\tQfieldLabel:\"修改人id\",\n" +
                "\t\t\t\t\tfieldLabel:$(\""+path+".modifyuserid\"),\n" +
                "\t\t\t\t\tname:\"model.modifyUserId\",\n" +
                "\t\t\t\t\thidden: true,\n" +
                "\t\t\t\t\tmaxLength:10\n" +
                "\t\t\t\t},{\n" +
                "\t\t\t\t\tQfieldLabel:\"修改人名字\",\n" +
                "\t\t\t\t\tfieldLabel:$(\""+path+".modifyusername\"),\n" +
                "\t\t\t\t\tname:\"model.modifyUserName\",\n" +
                "\t\t\t\t\thidden: true,\n" +
                "\t\t\t\t\tmaxLength:300\n" +
                "\t\t\t\t},{\n" +
                "\t\t\t\t\tQfieldLabel:\"修改时间\",\n" +
                "\t\t\t\t\tfieldLabel:$(\""+path+".modifytime\"),\n" +
                "\t\t\t\t\tname:\"model.modifyTime\",\n" +
                "\t\t\t\t\tanchor:\"90%\",\n" +
                "\t\t\t\t\txtype:\"datefield\",\n" +
                "\t\t\t\t\thidden: true,\n" +
                "\t\t\t\t\tformat:\"Y-m-d H:i:s\"\n" +
                "\t\t\t\t},{\n" +
                "\t\t\t\t\tQfieldLabel:\"客户端编码\",\n" +
                "\t\t\t\t\tfieldLabel:$(\""+path+".clientcode\"),\n" +
                "\t\t\t\t\tname:\"model.clientcode\",\n" +
                "\t\t\t\t\thidden: true,\n" +
                "\t\t\t\t\tmaxLength:150\n" +
                "\t\t\t\t},{\n" +
                "\t\t\t\t\tQfieldLabel:\"删除标记\",\n" +
                "\t\t\t\t\tfieldLabel:$(\""+path+".isdelete\"),\n" +
                "\t\t\t\t\tname:\"model.isDelete\",\n" +
                "\t\t\t\t\thidden: true,\n" +
                "\t\t\t\t\tmaxLength:2\n" +
                "\t\t\t\t}");
        sb.append("\t\t\t],  \n");

        sb.append("vp_gridCfg:{\n" +
                "\t\t\tstateId : s_userCode + '_"+path+"',\n" +
                "\t\t\tstateHeader : true,\n" +
                "\t\t\tforceFit : false,\n" +
                "\t\t\tstateful: true,\n" +
                "\t\t\tableExporter:true,\n" +
                "\t\t\trn:true //序列列隐藏\n" +
                "\t\t},");
        sb.append("\t\t\tsw_searchFormItems:[{\n");
        sb.append(
                "{\n"
                        + "\t\t\t\t\tfieldLabel : $('porder.documentDate'),\n"
                        + "\t\t\t\t\tname : 'filter_GE_purchaseOrderTime',\n"
                        + "\t\t\t\t\txtype : 'datefield',\n"
                        + "\t\t\t\t\tformat : 'Y-m-d'\n"
                        + "\t\t\t\t}, {\n"
                        + "\t\t\t\t\tfieldLabel : $('label.to'),\n"
                        + "\t\t\t\t\tname : 'filter_LE_purchaseOrderTime',\n"
                        + "\t\t\t\t\txtype : 'datefield',\n"
                        + "\t\t\t\t\tformat : 'Y-m-d'\n"
                        + "\t\t\t\t},");
        sb.append("{\n" +
                "\t\t\t\t\tfieldLabel : $('label.createUserName'),\n" +
                "\t\t\t\t\tname : 'filter_LIKE_createUserName'\n" +
                "\t\t\t\t},");
        sb.append("\t\t\t\t]\n");

        sb.append("\t\t},\n");
        sb.append("     }\n");
        sb.append("});");
        return sb.toString();
    }

}
