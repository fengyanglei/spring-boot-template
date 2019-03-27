package com.fyl.boot.api.file;

import com.fyl.boot.api.BaseService;
import com.fyl.boot.common.param.FileResourcesParam;
import com.fyl.boot.common.response.FileResourcesResponse;
import com.fyl.boot.common.response.Response;
import com.fyl.boot.dao.pojo.FileResources;

import java.util.List;

/**
 * 文件管理
 */
public interface FileService extends BaseService<FileResources> {

    /**
     * 查询
     *
     * @param dir 文件所属目录
     * @return
     */
    List<FileResourcesResponse> list(String dir);

    /**
     * 保存
     * @param param 请求参数
     * @return
     */
    Response save(FileResourcesParam param);

}
