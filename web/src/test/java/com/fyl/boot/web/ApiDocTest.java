package com.fyl.boot.web;

import com.fyl.boot.common.constant.HttpStatus;
import com.power.common.util.DateTimeUtil;
import com.power.doc.builder.ApiDocBuilder;
import com.power.doc.model.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * ApiDoc测试
 */
public class ApiDocTest {

    //文件输出路径
    private final static String OUT_PATH = "../javadoc";
    //服务url
    private final static String SERVER_URL = "http://localhost:8080";

    /**
     * 简单型接口，不需要指定请求头，并且项目是maven的.
     */
    @Test
    public void testBuilderControllersApiSimple() {
        //将生成的文档输出到OUT_PATH目录下，isStrict严格模式下api-doc会检测Controller的接口注释
        ApiDocBuilder.builderControllersApi(OUT_PATH, false);

    }

    /**
     * 生成接口文档
     */
    @Test
    public void builderApi() {
        ApiConfig config = new ApiConfig();
        config.setOutPath(OUT_PATH);
        config.setServerUrl(SERVER_URL);
        config.setStrict(false);//true会严格要求注释
        config.setAllInOne(true);//true会将文档合并导出到一个markdown
        //加载外部代码,用于获取注释
        config.setSourcePaths(
                SourcePath.path().setPath("src/main/java").setDesc("本module代码")
                , SourcePath.path().setPath("../common/src/main/java").setDesc("外部代码")
        );
        //错误码
        List<ApiErrorCode> errorCodeList = new ArrayList<>();
        for (HttpStatus codeEnum : HttpStatus.values()) {
            ApiErrorCode errorCode = new ApiErrorCode();
            errorCode.setValue(String.valueOf(codeEnum.value())).setDesc(codeEnum.getReasonPhrase());
            errorCodeList.add(errorCode);
        }
        config.setErrorCodes(errorCodeList);
        //文档变更记录
        config.setRevisionLogs(
                RevisionLog.getLog().setVersion("V1.0").setRevisionTime("2019/05/01").setStatus("创建").setAuthor("feng").setRemarks("初版"),
                RevisionLog.getLog().setVersion("V2.0").setRevisionTime("2019/05/10").setStatus("新增").setAuthor("lei").setRemarks("新增文件接口")
        );

        //生成
        ApiDocBuilder.builderControllersApi(config);
    }

    /**
     * 包括设置请求头，缺失注释的字段批量在文档生成期使用定义好的注释
     */
    @Test
    public void testBuilderControllersApi() {
        ApiConfig config = new ApiConfig();
        config.setServerUrl(SERVER_URL);
        config.setStrict(false);//true会严格要求注释，推荐设置true
        config.setAllInOne(true);//true会将文档合并导出到一个markdown
        config.setOutPath(OUT_PATH);
        // @since 1.2,如果不配置该选项，则默认匹配全部的controller,
        // 如果需要配置有多个controller可以使用逗号隔开
        config.setPackageFilters("com.fyl.boot.web.controller");
        //不指定SourcePaths默认加载代码为项目src/main/java下的,如果项目的某一些实体来自外部代码可以一起加载
        config.setSourcePaths(
                SourcePath.path().setPath("src/main/java").setDesc("本项目代码")
                , SourcePath.path().setPath("../common/src/main/java").setDesc("外部代码")
        );

        //设置请求头，如果没有请求头，可以不用设置
        config.setRequestHeaders(
                ApiReqHeader.header().setName("access_token").setType("string").setDesc("Basic auth credentials"),
                ApiReqHeader.header().setName("user_uuid").setType("string").setDesc("User Uuid key")
        );
        //对于外部jar的类，编译后注释会被擦除，无法获取注释，但是如果量比较多请使用setSourcePaths来加载外部代码
        //如果有这种场景，则自己添加字段和注释，api-doc后期遇到同名字段则直接给相应字段加注释
        config.setCustomResponseFields(
                CustomRespField.field().setName("success").setDesc("成功返回true,失败返回false"),
                CustomRespField.field().setName("message").setDesc("接口响应信息"),
                CustomRespField.field().setName("data").setDesc("接口响应数据"),
                CustomRespField.field().setName("code").setValue("00000").setDesc("响应代码")
        );

        //设置项目错误码列表，设置自动生成错误列表,
        List<ApiErrorCode> errorCodeList = new ArrayList<>();
        for (HttpStatus codeEnum : HttpStatus.values()) {
            ApiErrorCode errorCode = new ApiErrorCode();
            errorCode.setValue(String.valueOf(codeEnum.value())).setDesc(codeEnum.getReasonPhrase());
            errorCodeList.add(errorCode);
        }
        //如果没需要可以不设置
        config.setErrorCodes(errorCodeList);

        //非必须只有当setAllInOne设置为true时文档变更记录才生效，https://gitee.com/sunyurepository/ApplicationPower/issues/IPS4O
        config.setRevisionLogs(
                RevisionLog.getLog().setRevisionTime("2018/12/15").setAuthor("chen").setRemarks("测试").setStatus("创建").setVersion("V1.0"),
                RevisionLog.getLog().setRevisionTime("2018/12/16").setAuthor("chen2").setRemarks("测试2").setStatus("修改").setVersion("V2.0")
        );

        long start = System.currentTimeMillis();
        ApiDocBuilder.builderControllersApi(config);
        long end = System.currentTimeMillis();
        DateTimeUtil.printRunTime(end, start);
    }

}
