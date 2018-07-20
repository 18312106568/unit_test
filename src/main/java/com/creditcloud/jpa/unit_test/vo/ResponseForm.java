/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Map;
import lombok.Data;

/**
 *
 * @author MRB
 */
@Data
public class ResponseForm  implements Serializable {
    /**
     * 三方机构代码
     */
    @JsonProperty("COOP_CODE")
    private String COOP_CODE;

    /**
     * 请求代码
     */
    @JsonProperty("CODE")
    private Integer CODE;

    /**
     * 请求失败信息
     */
    @JsonProperty("MSG")
    private String MSG;
    
    /**
     * 签名
     */
    @JsonProperty("SIGNATURE")
    private String SIGNATURE;
    
    /**
     * 签名
     */
    @JsonProperty("CONTENTS")
    private DeclarationCountForm CONTENTS;
    
}
