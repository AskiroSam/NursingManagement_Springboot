package com.stedu.service.impl;

import com.stedu.bean.Custom;
import com.stedu.bean.Department;
import com.stedu.bean.Expend;
import com.stedu.mapper.ExpendMapper;
import com.stedu.service.ExpendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
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

        List<Expend> expends = expendMapper.selectAll();

        //来判断各级费用的人数并更新数据
        for (Expend expend : expends) {

            if (expend.getCustomList() == null) {
                expend.setCustomList(new ArrayList<>());
            }

            //过滤掉状态为2的客户（离院）
            List<Custom> activeCustoms = expend.getCustomList().stream()
                    .filter(custom -> custom.getCstate() != 2)
                    .collect(Collectors.toList());

            //计算过滤后的总人数
            int size = activeCustoms.size();
            Integer totalEnumber = size;
            expend.setEnumber(totalEnumber);

            //计算工资总和
            Long l = expendMapper.selectTotal(expend);
            expend.setEincome(l);

            //更新数据库
            expendMapper.updateExpend(expend.getEnumber(), expend.getEid(), expend.getEincome());
        }

        return expends;
    }

    @Override
    public Map<Integer, String> getExpendMap() {
        List<Expend> expends = expendMapper.selectAll();
        return expends.stream()
                .collect(Collectors.toMap(Expend::getEid, Expend::getEgrade));
    }
}
