package com.zua.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CategoryEcharts {
    private List<String> names = new ArrayList<>();
    private List<Integer> counts = new ArrayList<>();
}
