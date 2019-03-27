package com.fyl.boot.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileResourcesResponse {

    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 路径
     */
    private String path;

    /**
     * 大小KB
     */
    private Integer size;

    /**
     * 类型
     */
    private String type;

    /**
     * 所属目录
     */
    private String dir;

    /**
     * 备注
     */
    private String remark;
}
