package com.bjtu.afms.service;

import com.bjtu.afms.mapper.WorkplaceMapper;
import com.bjtu.afms.model.Workplace;
import com.bjtu.afms.model.WorkplaceExample;
import com.bjtu.afms.web.param.query.WorkplaceQueryParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class WorkplaceService {

    @Resource
    private WorkplaceMapper workplaceMapper;

    public boolean insertWorkplace(Workplace workplace) {
        return workplaceMapper.insertSelective(workplace) == 1;
    }

    public boolean deleteWorkplace(int workplaceId) {
        return workplaceMapper.deleteByPrimaryKey(workplaceId) == 1;
    }

    public boolean updateWorkplace(Workplace workplace) {
        return workplaceMapper.updateByPrimaryKeySelective(workplace) == 1;
    }

    public Workplace selectWorkplace(int workplaceId) {
        return workplaceMapper.selectByPrimaryKey(workplaceId);
    }

    public List<Workplace> selectWorkplaceList(WorkplaceQueryParam param) {
        WorkplaceExample example = new WorkplaceExample();
        if (StringUtils.isNotBlank(param.getOrderBy())) {
            example.setOrderByClause(param.getOrderBy());
        }
        WorkplaceExample.Criteria criteria = example.createCriteria();
        if (param.getName() != null) {
            criteria.andNameLike("%" + param.getName() + "%");
        }
        return workplaceMapper.selectByExample(example);
    }
}
