package com.fyl.boot.web.controller.file;

import com.fyl.boot.api.file.FileService;
import com.fyl.boot.common.param.FileResourcesParam;
import com.fyl.boot.common.response.FileResourcesResponse;
import com.fyl.boot.common.response.Response;
import com.fyl.boot.common.validGroup.FileResourcesSaveValidGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 文件资源管理
 */
@RestController
@RequestMapping("file")
public class FileController {

    @Autowired
    private FileService service;

    /**
     * 查询
     *
     * @param dir 文件所属目录
     * @return
     */
    @RequestMapping("list")
    public Response list(String dir) {
        List<FileResourcesResponse> list = service.list(dir);
        return new Response(list);
    }

    /**
     * 保存
     *
     * @param param 请求参数
     * @return
     */
    @RequestMapping("save")
    public Response save(@Validated(FileResourcesSaveValidGroup.class) @RequestBody FileResourcesParam param) {
        return service.save(param);
    }
}
