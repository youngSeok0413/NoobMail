package com.noobmail.noobmail.domain;

import org.json.JSONException;
import org.json.simple.parser.ParseException;

import java.util.Map;

public interface DTO {
    //do not need input
    public String columns();
    public String values() throws JSONException, ParseException; // string + 그 외의 것들 방식이 좀 "" 추가하는 작업 해줄 것
}
