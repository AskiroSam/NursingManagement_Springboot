package com.stedu.service.impl;

import com.stedu.bean.RespBean;
import com.stedu.bean.Travel;
import com.stedu.bean.TravelProgressDTO;
import com.stedu.exception.MyException;
import com.stedu.mapper.TravelMapper;
import com.stedu.service.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class TravelServiceImpl implements TravelService {
    @Autowired
    private TravelMapper travelMapper;

    @Override
    public boolean delete(Integer tid) throws MyException {
        List<Integer> sids = travelMapper.selectSidByTid(tid);
        List<Integer> cids = travelMapper.selectCidByTid(tid);

        if (sids != null || cids != null) {
           throw new MyException("路线中还有成员，无法删除");
        }
        return travelMapper.delete(tid) == 1;
    }

    @Override
    public Travel selectByTid(Integer tid) {
        return travelMapper.selectByTid(tid);
    }

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
        if (cids == null || cids.length == 0) {
            // 清空数据表
            travelMapper.deleteTidAndCidByTid(tid);
        } else {
            // 清空旧关系
            travelMapper.deleteTidAndCidByTid(tid);
            // 添加新关系
            travelMapper.insertTidAndCid(tid, cids);
        }
    }

    @Override
    //出问题后进行回滚
    @Transactional(rollbackFor = Exception.class)
    public void insertTidAndSid(Integer tid, Integer[] sids) {
        if (sids == null || sids.length == 0) {
            // 清空数据表
            travelMapper.deleteTidAndSidByTid(tid);
        } else {
            //清空旧关系
            travelMapper.deleteTidAndSidByTid(tid);
            //添加新关系
            travelMapper.insertTidAndSid(tid, sids);
        }
    }

    //计算进度条的时间
    @Override
    public TravelProgressDTO calculateProgress(Travel travel) throws ParseException {
        Date tstart = travel.getTstart();  // 替换为你的 Travel 对象获取 tstart 的方式
        Date tend = travel.getTend();

        // 使用 SimpleDateFormat 来提取时分秒
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH");
        SimpleDateFormat minuteFormat = new SimpleDateFormat("mm");
        //开始时间
        String tStartHours = hourFormat.format(tstart);
        String tStartMinutes = minuteFormat.format(tstart);
        //结束时间
        String tEndtHours = hourFormat.format(tend);
        String tEndMinutes = minuteFormat.format(tend);


        String START_TIME = tStartHours + ":" + tStartMinutes; // 定义时间段开始时间
        String END_TIME = tEndtHours + ":" + tEndMinutes;   // 定义时间段结束时间
        // 获取当前时间
        Calendar now = Calendar.getInstance();
        // 创建格式化时间的工具
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        // 获取当前的小时和分钟
        String currentTimeString = timeFormat.format(now.getTime());
        Date currentTime = timeFormat.parse(currentTimeString);
        // 计算起始时间和结束时间
        Date startTime = timeFormat.parse(START_TIME);
        Date endTime = timeFormat.parse(END_TIME);
        // 计算总时间段的毫秒数
        long totalDuration = endTime.getTime() - startTime.getTime();
        // 计算当前时间相对于起始时间的进度（毫秒数）
        long elapsedDuration = currentTime.getTime() - startTime.getTime();
        // 如果当前时间在定义的时间段内
        double progressPercentage = 0;
        if (elapsedDuration > 0 && elapsedDuration <= totalDuration) {
            // 计算进度百分比
            progressPercentage = ((double) elapsedDuration / totalDuration) * 100;
            // 显示当前时间与进度百分比
            System.out.println("当前时间: " + currentTimeString);
            System.out.printf("进度: %.2f%%\n", progressPercentage);
        } else {
            System.out.println("当前时间不在定义的时间段内");
        }

        //保留两位小数
        progressPercentage = Math.round(progressPercentage * 100.0) / 100.0;
        System.out.println(progressPercentage);

        //// 返回进度数据
        TravelProgressDTO progressDTO = new TravelProgressDTO();
        progressDTO.setTid(travel.getTid());
        progressDTO.setProgress(progressPercentage);
        return progressDTO;
    }
}
