/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.vo.tp;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.Data;

/**
 *
 * @author MRB
 */
@Data
public class CrackVo implements Serializable{
    @JsonProperty("appeal_time")
    private String appealTime;
    
    @JsonProperty("appeal_state")
    private Double appealState;
    
    @JsonProperty("zone")
    private String zone;
    
    @JsonProperty("extend")
    private Object extend;
    
    @JsonProperty("reduce_state")
    private Double reduceState;
    
    @JsonProperty("reduce_state")
    private String reason;
    
    @JsonProperty("start_stmp")
    private Double startStmp;
    
    @JsonProperty("game_name")
    private String gameName;
    
    @JsonProperty("free_state")
    private Double freeState;
    
    @JsonProperty("free_state")
    private Double duration;
    
    @JsonProperty("game_id")
    private Double gameId;
    
    @JsonProperty("type")
    private String type;
    
    @JsonProperty("reduced")
    private Double reduced;
    
    @JsonProperty("reduce_percent")
    private Double reducePercent;
}
