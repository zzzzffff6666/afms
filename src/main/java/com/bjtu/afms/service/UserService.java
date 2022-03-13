package com.bjtu.afms.service;

import com.bjtu.afms.enums.UserStatus;
import com.bjtu.afms.mapper.UserMapper;
import com.bjtu.afms.model.User;
import com.bjtu.afms.model.UserExample;
import com.bjtu.afms.web.param.query.UserQueryParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    public int insertUser(User user) {
        return userMapper.insertSelective(user);
    }

    public int deleteUser(int userId) {
        return userMapper.deleteByPrimaryKey(userId);
    }

    public int updateUser(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }

    public int updatePassword(int id, String password, String salt) {
        User user = new User();
        user.setId(id);
        user.setSalt(salt);
        user.setPassword(password);
        return updateUser(user);
    }

    public User selectUser(int userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    public User selectUserForLogin(String phone) {
        UserExample example = new UserExample();
        example.createCriteria().andPhoneEqualTo(phone).andStatusEqualTo(UserStatus.WORK.getId());
        List<User> userList = userMapper.selectForLogin(example);
        return CollectionUtils.isEmpty(userList) ? null : userList.get(0);
    }

    public List<User> selectUserList(UserQueryParam param) {
        UserExample example = new UserExample();
        if (StringUtils.isNotBlank(param.getOrderBy())) {
            example.setOrderByClause(param.getOrderBy());
        }
        UserExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(param.getPhone())) {
            criteria.andPhoneLike(param.getPhone() + "%");
        }
        if (StringUtils.isNotBlank(param.getName())) {
            criteria.andNameLike("%" + param.getName() + "%");
        }
        if (param.getStatus() != null) {
            criteria.andStatusEqualTo(param.getStatus());
        }
        return userMapper.selectByExample(example);
    }

    public boolean exist(String phone) {
        UserExample example = new UserExample();
        example.createCriteria().andPhoneEqualTo(phone);
        return userMapper.countByExample(example) > 0;
    }

    public List<User> selectUserListByIdList(List<Integer> idList) {
        UserExample example = new UserExample();
        example.createCriteria().andIdIn(idList).andStatusEqualTo(UserStatus.WORK.getId());
        return userMapper.selectByExample(example);
    }
}
