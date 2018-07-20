package com.creditcloud.jpa.unit_test.vo;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author xiaozefeng
 */
@Setter
@Getter
@ToString
public class BaseResult implements Serializable {
    private static final long serialVersionUID = 3135570349775380437L;


    @JsonProperty("errmsg")
    private String errmsg;

    @JsonProperty("data")
    private List<Object> data;

    @JsonProperty("err")
    private int err;

    public BaseResult() {
    }

    private BaseResult(String msg,  List<Object> data, int code) {
        this.errmsg = msg;
        this.data = data;
        this.err = code;
    }


    public static BaseResult ok( List<Object> data) {
        return new BaseResult("success", data, 200);
    }

    public static BaseResult ok() {
        return new BaseResult("success", null, 200);
    }

    public static BaseResult error(String msg) {
        return new BaseResult(msg, null, 500);
    }

}
