package com.stedu.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TravelProgressDTO {
    private Integer tid;         // 线路 ID
    private Double progress;    // 进度百分比
}
