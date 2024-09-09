package com.stedu.service.impl;

import com.stedu.bean.Custom;
import com.stedu.bean.Department;
import com.stedu.bean.Hostel;
import com.stedu.exception.MyException;
import com.stedu.mapper.CustomMapper;
import com.stedu.mapper.DepartmentMapper;
import com.stedu.mapper.HostelMapper;
import com.stedu.service.HostelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HostelServiceImpl implements HostelService {
    @Autowired
    private HostelMapper hostelMapper;
    @Autowired
    private CustomMapper customMapper;

    @Override
    public List<Hostel> selectByPage(String hno, String dname) {
        List<Hostel> hostels = hostelMapper.selectByPage(hno, dname);

        for (Hostel hostel : hostels) {

            List<Integer> hDnumberList = hostelMapper.selectCidByHid(hostel.getHid());
            if (hDnumberList == null) {
                hDnumberList = new ArrayList<>();
            }

            int count = 0;
            for (Integer cid : hDnumberList) {
                Custom custom = customMapper.selectByCid(cid);
                if (custom != null && custom.getCstate() == 2) {
                    continue;
                }
                count++;

            }

            hostel.setDnumber(count);

            //更新数据库
            hostelMapper.updateDnumber(hostel.getDnumber(), hostel.getHid());

        }

        return hostels;
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
