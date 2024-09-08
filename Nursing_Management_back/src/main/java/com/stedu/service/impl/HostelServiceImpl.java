package com.stedu.service.impl;

import com.stedu.bean.Hostel;
import com.stedu.exception.MyException;
import com.stedu.mapper.HostelMapper;
import com.stedu.service.HostelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HostelServiceImpl implements HostelService {
    @Autowired
    private HostelMapper hostelMapper;

    @Override
    public List<Hostel> selectByPage(String hno, String dname) {
        return hostelMapper.selectByPage(hno, dname);
    }

    @Override
    public boolean insert(Hostel hostel) throws MyException {
        return hostelMapper.insert(hostel) == 1;
    }

    @Override
    public boolean delete(Integer hid) throws MyException {
        return hostelMapper.delete(hid) == 1;
    }

    @Override
    public Hostel selectByHid(Integer hid) {
        return hostelMapper.selectByHid(hid);
    }

    @Override
    public List<Hostel> selectAll() {
        return hostelMapper.selectAll();
    }

    @Override
    public List<Integer> selectCidByHid(Integer hid) {
        return hostelMapper.selectCidByHid(hid);
    }

    @Override
    public void insertHidAndCid(Integer hid, Integer[] cids) throws MyException {
        if (cids == null || cids.length == 0) {
            hostelMapper.deleteCidAndHidByHid(hid);
        } else {
            hostelMapper.deleteCidAndHidByHid(hid);
            hostelMapper.insertHidAndCid(hid, cids);
        }

    }

    @Override
    public boolean update(Hostel hostel) {
        return hostelMapper.update(hostel) == 1;
    }
}
