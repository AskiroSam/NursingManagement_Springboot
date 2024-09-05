package com.stedu.service.impl;

import com.stedu.bean.Travel;
import com.stedu.mapper.TravelMapper;
import com.stedu.service.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TravelServiceImpl implements TravelService {
    @Autowired
    private TravelMapper travelMapper;

    @Override
    public List<Travel> selectAll() {
        return travelMapper.selectAll();
    }

    @Override
    public List<Integer> selectCidByTid(Integer tid) {
        return travelMapper.selectCidByTid(tid);
    }

    @Override
    public List<Integer> selectSidByTid(Integer tid) {
        return travelMapper.selectSidByTid(tid);
    }

    @Override
    //出问题后进行回滚
    @Transactional(rollbackFor = Exception.class)
    public void insertTidAndCid(Integer tid, Integer[] cids) {
        //清空旧关系
        travelMapper.deleteTidAndCidByTid(tid);
        //添加新关系
        travelMapper.insertTidAndCid(tid, cids);
    }

    @Override
    //出问题后进行回滚
    @Transactional(rollbackFor = Exception.class)
    public void insertTidAndSid(Integer tid, Integer[] sids) {
        //清空旧关系
        travelMapper.deleteTidAndSidByTid(tid);
        //添加新关系
        travelMapper.insertTidAndSid(tid, sids);
    }
}
