package com.bjtu.afms.service;

import com.bjtu.afms.mapper.LogMapper;
import com.bjtu.afms.model.Log;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LogService {

    @Resource
    private LogMapper logMapper;

    public void insertLog(Log log) {

    }
}
