package com.aiyi.tool.dev.service;

import com.aiyi.tool.dev.entity.ProjectVO;

import java.util.List;

/**
 * @Auther: 郭胜凯
 * @Date: 2019-04-03 18:22
 * @Email 719348277@qq.com
 * @Description: 项目业务管理类
 */
public interface ProjectService {

    /**
     * 条件列出项目列表
     * @param search
     *      搜索条件
     * @return
     */
    List<ProjectVO> list(ProjectVO search);

    /**
     * 创建项目
     * @param project
     *      项目信息
     * @return
     */
    ProjectVO create(ProjectVO project);

    /**
     * 更新项目信息
     * @param project
     *      项目信息
     */
    void update(ProjectVO project);

    /**
     * 关闭项目
     * @param projectId
     *      项目ID
     */
    void close(String projectId);

    /**
     * 打开项目
     * @param projectId
     *      项目ID
     */
    void open(String projectId);

    /**
     * 给项目添加成员
     * @param projectId
     *      项目ID
     * @param userIds
     *      要添加的成员列表
     */
    void addMember(String projectId, List<String> userIds);

    /**
     * 给项目移除一个成员
     * @param projectId
     *      项目ID
     * @param userId
     *      成员ID
     */
    void removeMember(String projectId, String userId);
}