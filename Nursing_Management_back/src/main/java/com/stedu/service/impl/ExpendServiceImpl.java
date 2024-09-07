package com.stedu.service.impl;

import com.stedu.bean.Department;
import com.stedu.bean.Expend;
import com.stedu.mapper.ExpendMapper;
import com.stedu.service.ExpendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ExpendServiceImpl implements ExpendService {
    @Autowired
    private ExpendMapper expendMapper;

    @Override
    public boolean update(Expend e) {
        return expendMapper.update(e) == 1;
    }

    @Override
    public Expend selectByEid(Integer eid) {
        return expendMapper.selectByEid(eid);
    }

    @Override
    public List<Expend> selectAll() {
        return expendMapper.selectAll();
    }

    @Override
    public Map<Integer, String> getExpendMap() {
        List<Expend> expends = expendMapper.selectAll();
        return expends.stream()
                .collect(Collectors.toMap(Expend::getEid, Expend::getEgrade));
    }
}
