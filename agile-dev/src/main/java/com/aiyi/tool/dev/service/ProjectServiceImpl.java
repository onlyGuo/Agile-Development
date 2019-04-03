package com.aiyi.tool.dev.service;

import com.aiyi.core.beans.Method;
import com.aiyi.core.beans.WherePrams;
import com.aiyi.core.sql.where.C;
import com.aiyi.core.util.thread.ThreadUtil;
import com.aiyi.tool.dev.dao.ProjectDao;
import com.aiyi.tool.dev.entity.ProjectVO;
import com.aiyi.tool.dev.utils.Vali;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: 郭胜凯
 * @Date: 2019-04-03 18:36
 * @Email 719348277@qq.com
 * @Description: 项目管理业务实现类
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @Resource
    private ProjectDao projectDao;

    @Override
    public List<ProjectVO> list(ProjectVO search) {
        WherePrams where = Method.where("USER_ID", C.EQ, ThreadUtil.getUserId());
        // 除了我的项目外, 还有我参与的项目
        List<Serializable> idList = new ArrayList<>();
        where.and("ID", C.IN, idList);

        if (!Vali.isEpt(search.getName())){
            where.and("NAME", C.LIKE, search.getName());
        }
        if (!Vali.isEpt(search.getStatus())){
            where.and("STATUS", C.EQ, search.getStatus());
        }

        return projectDao.list(where);
    }

    @Override
    public ProjectVO create(ProjectVO project) {
        return null;
    }

    @Override
    public void update(ProjectVO project) {

    }

    @Override
    public void close(String projectId) {

    }

    @Override
    public void open(String projectId) {

    }

    @Override
    public void addMember(String projectId, List<String> userIds) {

    }

    @Override
    public void removeMember(String projectId, String userId) {

    }
}