package com.lms.springboot.code;

/**
 * @author lms
 * @PackageName: com.lms.springboot.code
 * @ClassName: BaseInfo
 * @Description: todo
 * @date 2021/2/15 12:56 上午
 */
public class BaseInfo {
    
    /**模块**/
    private  String moduleName = "purchasing";
    private  String moduleVersion = "purchasing-service";
    private  String basePath = "com.anta.srm."+moduleName;

    /**---------**/
    private   String entity = ".entity";
    private   String dao = ".dao";
    private   String service = ".service";
    private   String api = ".api";
    private   String clientimpl = ".clientimpl";
    /**--------**/


    /**---------**/
    private   String controller = ".controller";
    private   String jsModule = "stock";
    /**--------**/

    /******ean start*********/
    private   String entityBaseBeanName = "BaseAntaEntity";
    private   String entityBeanName = "ReturnDetails";

    /******ean end*********/

    private   String pk = "returndetailsid"; //id
    private   String pathPrefix = "cp";
    private   String jsPrefix = "Purchasing.stock";

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleVersion() {
        return moduleVersion;
    }

    public void setModuleVersion(String moduleVersion) {
        this.moduleVersion = moduleVersion;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getDao() {
        return dao;
    }

    public void setDao(String dao) {
        this.dao = dao;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getClientimpl() {
        return clientimpl;
    }

    public void setClientimpl(String clientimpl) {
        this.clientimpl = clientimpl;
    }

    public String getController() {
        return controller;
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    public String getJsModule() {
        return jsModule;
    }

    public void setJsModule(String jsModule) {
        this.jsModule = jsModule;
    }

    public String getEntityBaseBeanName() {
        return entityBaseBeanName;
    }

    public void setEntityBaseBeanName(String entityBaseBeanName) {
        this.entityBaseBeanName = entityBaseBeanName;
    }

    public String getEntityBeanName() {
        return entityBeanName;
    }

    public void setEntityBeanName(String entityBeanName) {
        this.entityBeanName = entityBeanName;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getPathPrefix() {
        return pathPrefix;
    }

    public void setPathPrefix(String pathPrefix) {
        this.pathPrefix = pathPrefix;
    }

    public String getJsPrefix() {
        return jsPrefix;
    }

    public void setJsPrefix(String jsPrefix) {
        this.jsPrefix = jsPrefix;
    }
}
