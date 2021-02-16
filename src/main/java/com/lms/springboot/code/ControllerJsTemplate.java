package com.lms.springboot.code;

import java.util.Map;

/**
 * @author lms
 * @PackageName: com.lms.springboot.code
 * @ClassName: ControllerJsTemplate
 * @Description: todo
 * @date 2021/2/16 12:19 上午
 */
public class ControllerJsTemplate{

    private BaseInfo baseInfo;

    public ControllerJsTemplate(BaseInfo baseInfo) {
        this.baseInfo = baseInfo;
    }

    private String controllerJsTemplate(String desc, Map<String,String> store, String sql, String bean){

        String basePath = baseInfo.getBasePath();
        String api = baseInfo.getApi();
        String entity = baseInfo.getEntity();
        String entityBeanName = baseInfo.getEntityBeanName();
        String moduleVersion = baseInfo.getModuleVersion();
        String clientimpl = baseInfo.getClientimpl();
        String service = baseInfo.getService();
        String pk = baseInfo.getPk();
        String jsPrefix = baseInfo.getJsPrefix();

        String entityBaseBeanName = baseInfo.getEntityBaseBeanName();


        StringBuffer sb = new StringBuffer();
        String path = entityBaseBeanName.substring(0,1).toLowerCase()+entityBeanName.substring(1);

        sb.append("/**\n" +
                " * @class {Purchasing.stock.ReturnDetailsViewModel}\n" +
                " * @extend {Ext.ux.app.ViewModel}\n" +
                " * "+desc+"\n" +
                " */");
        sb.append(
                "Ext.define('"+jsPrefix+"."+entityBeanName+"Controller',{\n"
                        + "\textend:'Ext.ux.app.ViewController',\n"
                        + "    alias:'controller."+path+"Controller',\n");
        sb.append("/**\n" +
                "\t * 列表双击事件\n" +
                "\t * @return {Boolean}\n" +
                "\t */\n" +
                "\trowdblclickFn:function(){\n" +
                "\t\treturn false;\n" +
                "\t},");
        sb.append(
                "/**\n"
                        + "\t * @method vpInstanceAfter\n"
                        + "\t * 窗体实例化之后\n"
                        + "\t */\n"
                        + "    vpInstanceAfter: function() {\n"
                        + "    \ttry{\n"
                        + "    \t\tvar me = this;\n"
                        + "    \t\tvar viewModel = me.getViewModel();\n"
                        + "    \t\tvar vp = viewModel.getVp();\n"
                        + "    \t\tvar searchBtn = vp.grid.getTopToolbar().query('*[name=search]')[0];\n"
                        + "    \t\tif (!Ext.isEmpty(searchBtn)) {\n"
                        + "    \t\t\tsearchBtn.handler();\n"
                        + "    \t\t\tviewModel.getSearchWin().center();\n"
                        + "    \t\t}\n"
                        + "    \t\tformalItemGrid = me;\n"
                        + "    \t}catch(e){\n"
                        + "    \t\tconsole.log(e)\n"
                        + "    \t}\n"
                        + "    },");

        sb.append(
                "/**\n"
                        + "\t * @method gridStoreLoad\n"
                        + "\t * 列表加载事件\n"
                        + "\t */\n"
                        + "\tvpAfterRender: function() {\n"
                        + "\t\ttry{\n"
                        + "\t\t\tvar me = this;\n"
                        + "\t\t\tvar viewModel = me.getViewModel();\n"
                        + "\t\t\tvar vp = viewModel.getVp();\n"
                        + "\t\t\tif (undefined != vp.searchWin) {\n"
                        + "\t\t\t\tvp.searchWin.formPanel.form.reset();\n"
                        + "\t\t\t}\n"
                        + "\t\t}catch(e){\n"
                        + "    \t\tif (!Ext.isIE) {\n"
                        + "\t\t\t\tconsole.log(e)\n"
                        + "\t\t\t}\n"
                        + "    \t}\n"
                        + "\t},");
        sb.append("/**\n" +
                "\t * @method setFormValueAfter\n" +
                "\t * 表单初始化后触发\n" +
                "\t */\n" +
                "\tsetFormValueAfter: function( formPanel, viewType, json ){\n" +
                "\t\ttry{\n" +
                "\t\t\tvar form = formPanel.getForm();\n" +
                "\t\t\tform.findField(\"canChargeOffNum\").setValue(json.data.canChargeOffNum);\n" +
                "\t\t}catch(e){\n" +
                "    \t\tif (!Ext.isIE) {\n" +
                "\t\t\t\tconsole.log(e)\n" +
                "\t\t\t}\n" +
                "    \t}\n" +
                "\t},");

        sb.append(
                "editAfter: function() {\n"
                        + "\t\ttry{\n"
                        + "\t\t\tvar me = this;\n"
                        + "\t\t\tvar viewModel = me.getViewModel();\n"
                        + "\t\t\tvar vp = viewModel.getVp();\n"
                        + "\t\t\tvar tbar = vp.editWin.formPanel.getTopToolbar().find('name', 'submit')[0];\n"
                        + "\t\t\ttbar.hide();\n"
                        + "\t\t}catch(e){\n"
                        + "    \t\tif (!Ext.isIE) {\n"
                        + "\t\t\t\tconsole.log(e)\n"
                        + "\t\t\t}\n"
                        + "    \t}\n"
                        + "\t},\n"
                        + "\tviewAfter: function() {\n"
                        + "\t\ttry{\n"
                        + "\t\t\tvar me = this;\n"
                        + "\t\t\tvar viewModel = me.getViewModel();\n"
                        + "\t\t\tvar vp = viewModel.getVp();\n"
                        + "\t\t\tvar tbar = vp.editWin.formPanel.getTopToolbar().find('name', 'submit')[0];\n"
                        + "\t\t\ttbar.hide();\n"
                        + "\t\t}catch(e){\n"
                        + "    \t\tif (!Ext.isIE) {\n"
                        + "\t\t\t\tconsole.log(e)\n"
                        + "\t\t\t}\n"
                        + "    \t}\n"
                        + "\t},");
        return sb.toString();
    }

}
