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

    // 最大分配数量
    private static final int MAX_ALLOCATION_CUSTOM = 5;

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
        // 假设要验证的字段是 hostel 对象的 hno 属性
        String hno = hostel.getHno();

        // 验证 hno 是否为 4 位整数
        if (hno == null || !hno.matches("\\d{4}")) {
            throw new MyException("hno 必须是 4 位整数");
        }

        return hostelMapper.insert(hostel) == 1;
    }

    @Override
    public boolean delete(Integer hid) throws MyException {
        Hostel hostel = hostelMapper.selectByHid(hid);
        if (hostel.getDnumber() != 0) {
            throw new MyException("宿舍中还有客户，无法停用");
        }
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
            if (cids.length > MAX_ALLOCATION_CUSTOM) {
                throw new MyException("分配数量超过最大限制");
            }
            hostelMapper.deleteCidAndHidByHid(hid);
            hostelMapper.insertHidAndCid(hid, cids);
        }

    }

    @Override
    public boolean update(Hostel hostel) {
        return hostelMapper.update(hostel) == 1;
    }
}
