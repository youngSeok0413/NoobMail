package com.noobmail.noobmail.domain;

import java.util.Map;

public interface DTO {
    //do not need input
    public String columns(Map<String, String> sets);
    public String values(Map<String, String> sets) ; // string + 그 외의 것들 방식이 좀 "" 추가하는 작업 해줄 것
}
