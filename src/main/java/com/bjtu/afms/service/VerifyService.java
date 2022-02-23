package com.bjtu.afms.service;

import com.bjtu.afms.enums.VerifyStatus;
import com.bjtu.afms.mapper.VerifyMapper;
import com.bjtu.afms.model.Verify;
import com.bjtu.afms.model.VerifyExample;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class VerifyService {

    @Resource
    private VerifyMapper verifyMapper;

    public int insertVerify(Verify verify) {
        return verifyMapper.insertSelective(verify);
    }

    public int deleteVerify(String phone) {
        return verifyMapper.deleteByPrimaryKey(phone);
    }

    public int updateVerify(Verify verify) {
        return verifyMapper.updateByPrimaryKeySelective(verify);
    }

    public int insertOrUpdate(Verify verify) {
        VerifyExample example = new VerifyExample();
        example.createCriteria().andPhoneEqualTo(verify.getPhone());
        if (verifyMapper.countByExample(example) > 0) {
            return verifyMapper.updateByPrimaryKeySelective(verify);
        } else {
            return verifyMapper.insertSelective(verify);
        }
    }

    public Verify selectVerify(String phone) {
        return verifyMapper.selectByPrimaryKey(phone);
    }

    public boolean matchVerify(String phone, String code) {
        VerifyExample example = new VerifyExample();
        example.createCriteria().andPhoneEqualTo(phone)
                .andExpireTimeGreaterThan(new Date())
                .andStatusEqualTo(VerifyStatus.VALID.getId());
        List<Verify> verifies = verifyMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(verifies)) {
            return false;
        }
        Verify verify = verifies.get(0);
        if (code.equals(verify.getCode())) {
            verify.setStatus(VerifyStatus.VERIFIED.getId());
            verify.setVerifyTime(new Date());
            verifyMapper.updateByPrimaryKeySelective(verify);
            return true;
        }
        return false;
    }
}
