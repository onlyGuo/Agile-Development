package com.aiyi.tool.dev.entity;

import com.aiyi.core.annotation.po.FieldName;
import com.aiyi.core.annotation.po.ID;
import com.aiyi.core.annotation.po.TableName;
import com.aiyi.core.beans.Po;
import com.aiyi.tool.dev.common.CommonAttr;

import java.util.Date;

/**
 * @Auther: 郭胜凯
 * @Date: 2019-04-03 18:09
 * @Email 719348277@qq.com
 * @Description: 项目实体
 */
@TableName(name = "DEV_PROJECT")
public class ProjectVO extends Po {

    /**
     * 项目ID
     */
    @ID
    @FieldName(name = "ID")
    private String id;

    /**
     * 所属用户ID
     */
    @FieldName(name = "USER_ID")
    private String userId;

    /**
     * 项目封面
     */
    @FieldName(name = "COVER")
    private String cover;

    /**
     * 项目名称
     */
    @FieldName(name = "NAME")
    private String name;

    /**
     * 项目状态
     */
    @FieldName(name = "STATUS")
    private String status = CommonAttr.PROJECT.STATUS_OPEN;

    /**
     * 项目工作流ID
     */
    @FieldName(name = "FLOW_ID")
    private String flowId;

    /**
     * 项目创建时间
     */
    @FieldName(name = "CREATE_TIME")
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}