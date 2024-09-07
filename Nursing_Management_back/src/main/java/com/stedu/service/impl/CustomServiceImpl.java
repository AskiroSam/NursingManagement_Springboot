package com.stedu.service.impl;

import com.stedu.bean.Custom;
import com.stedu.mapper.CustomMapper;
import com.stedu.service.CustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomServiceImpl implements CustomService {
    @Autowired
    private CustomMapper customMapper;

    @Override
    public List<Custom> selectByPage(String cname, String cgender, String caddress) {
        return customMapper.selectByPage(cname, cgender, caddress);
    }

    @Override
    public List<Custom> selectAll() {
        return customMapper.selectAll();
    }

    @Override
    public boolean insert(Custom c) {
        c.setCstate(1);
        return customMapper.insert(c) == 1;
    }

    @Override
    public boolean update(Custom c) {
        return customMapper.update(c) == 1;
    }

    @Override
    public boolean delete(Integer cid) {
        return customMapper.setCstate(cid, 2) == 1;
    }

    @Override
    public Custom selectByCid(Integer cid) {
        return customMapper.selectByCid(cid);
    }

    @Override
    public List<Custom> selectByDid(Integer did) {
        return customMapper.sclectByDid(did);
    }

    @Override
    public boolean updateFid(Integer cid, Integer fid) {
        return customMapper.updateFid(cid, fid) == 1;
    }
}
