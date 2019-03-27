package com.fyl.boot.common.param;

import com.fyl.boot.common.validGroup.FileResourcesEditValidGroup;
import com.fyl.boot.common.validGroup.FileResourcesSaveValidGroup;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class FileResourcesParam {

    private Long id;

    /**
     * 名称
     */
    @NotNull
    private String name;

    /**
     * 路径
     */
    @NotNull(message = "路径不能为空", groups = {FileResourcesSaveValidGroup.class, FileResourcesEditValidGroup.class})
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
    @NotNull(message = "目录不能为空", groups = {FileResourcesSaveValidGroup.class})
    private String dir;

    /**
     * 备注
     */
    private String remark;
}
