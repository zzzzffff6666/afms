package com.bjtu.afms.service;

import com.bjtu.afms.enums.UserStatus;
import com.bjtu.afms.mapper.UserMapper;
import com.bjtu.afms.model.User;
import com.bjtu.afms.model.UserExample;
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

    public User selectUser(int userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    public User selectUserForLogin(String phone) {
        UserExample example = new UserExample();
        example.createCriteria().andPhoneEqualTo(phone).andStatusEqualTo(UserStatus.WORK.getId());
        List<User> userList = userMapper.selectByExample(example);
        return CollectionUtils.isEmpty(userList) ? null : userList.get(0);
    }

    public List<User> selectUserList(User user, String orderByClause) {
        UserExample example = new UserExample();
        if (StringUtils.isNotBlank(orderByClause)) {
            example.setOrderByClause(orderByClause);
        }
        UserExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(user.getPhone())) {
            criteria.andPhoneLike(user.getPhone() + "%");
        }
        if (StringUtils.isNotBlank(user.getName())) {
            criteria.andNameLike("%" + user.getName() + "%");
        }
        if (user.getStatus() != null) {
            criteria.andStatusEqualTo(user.getStatus());
        }
        return userMapper.selectByExample(example);
    }
}
