package com.fyl.boot.dao.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "file_resources")
public class FileResources {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(generator = "JDBC")
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

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 1=删除
     */
    private Boolean deleted;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取名称
     *
     * @return name - 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取路径
     *
     * @return path - 路径
     */
    public String getPath() {
        return path;
    }

    /**
     * 设置路径
     *
     * @param path 路径
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 获取大小KB
     *
     * @return size - 大小KB
     */
    public Integer getSize() {
        return size;
    }

    /**
     * 设置大小KB
     *
     * @param size 大小KB
     */
    public void setSize(Integer size) {
        this.size = size;
    }

    /**
     * 获取类型
     *
     * @return type - 类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置类型
     *
     * @param type 类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取所属目录
     *
     * @return dir - 所属目录
     */
    public String getDir() {
        return dir;
    }

    /**
     * 设置所属目录
     *
     * @param dir 所属目录
     */
    public void setDir(String dir) {
        this.dir = dir;
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取修改时间
     *
     * @return update_time - 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置修改时间
     *
     * @param updateTime 修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取1=删除
     *
     * @return deleted - 1=删除
     */
    public Boolean getDeleted() {
        return deleted;
    }

    /**
     * 设置1=删除
     *
     * @param deleted 1=删除
     */
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}