/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 *
 * @author MRB
 */
@Data
public class DeclarationCountForm implements Serializable {

    /**
     * 报关单号
     */
    @JsonProperty("create_time")
    private Long createTime;

    /**
     * 报关时间
     */
    @JsonProperty("SnCount")
    private Integer snCount;
}
