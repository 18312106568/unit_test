/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 *
 * @author MRB
 */
@Data
public class DeclarationForm implements Serializable {

    /**
     * 进出口标示
     */
    @JsonProperty("importExportFlag")
    private String importExportFlag;

    /**
     * 业务类型
     */
    @JsonProperty("type")
    private String type;

    /**
     * 申报地海关
     */
    @JsonProperty("declearLocation")
    private String declearLocation;

    /**
     * 出口口岸
     */
    @JsonProperty("custom")
    private String custom;

    /**
     * 收发货人编码
     */
    @JsonProperty("companySocialCode")
    private String companySocialCode;

    /**
     * 收发货人名称
     */
    @JsonProperty("companyName")
    private String companyName;

    /**
     * 企业性质
     */
    @JsonProperty("enterprise")
    private String enterprise;

    /**
     * 生产销售单位编码
     */
    @JsonProperty("company2SocialCode")
    private String company2SocialCode;

    /**
     * 生产销售单位名称
     */
    @JsonProperty("company2Name")
    private String company2Name;

    /**
     * 申报单位编码
     */
    @JsonProperty("reportCompanySocialCode")
    private String reportCompanySocialCode;

    /**
     * 申报单位名称
     */
    @JsonProperty("reportCompanyName")
    private String reportCompanyName;

    /**
     * 运输方式
     */
    @JsonProperty("transportType")
    private String transportType;

    /**
     * 运输工具名称
     */
    @JsonProperty("transportTool")
    private String transportTool;

    /**
     * 航次??
     */
    @JsonProperty("transportNum")
    private String transportNum;

    /**
     * 提运单号
     */
    @JsonProperty("blno")
    private String blno;

    /**
     * 监管方式
     */
    @JsonProperty("jianguan")
    private String jianguan;

    /**
     * 征免性质
     */
    @JsonProperty("tax")
    private String tax;

    /**
     * 征税比例
     */
    @JsonProperty("payment")
    private String payment;

    /**
     * 纳税单位
     */
    @JsonProperty("nsdw")
    private String nsdw;

    /**
     * 运抵国（地区）
     */
    @JsonProperty("country")
    private String country;

    /**
     * 指运港
     */
    @JsonProperty("harbour")
    private String harbour;

    /**
     * 境内货源地
     */
    @JsonProperty("churchyard")
    private String churchyard;

    /**
     * 成交方式
     */
    @JsonProperty("dealMode")
    private String dealMode;

    /**
     * 运费类型
     */
    @JsonProperty("carriageType")
    private String carriageType;

    /**
     * 运费金额
     */
    @JsonProperty("carriagePrice")
    private String carriagePrice;

    /**
     * 运费币制
     */
    @JsonProperty("carriageCurrency")
    private String carriageCurrency;

    /**
     * 保费类型
     */
    @JsonProperty("premiumType")
    private String premiumType;

    /**
     * 保费金额
     */
    @JsonProperty("premiumPrice")
    private String premiumPrice;

    /**
     * 保费币制
     */
    @JsonProperty("premiumCurrency")
    private String premiumCurrency;

    /**
     * 件数
     */
    @JsonProperty("boxNum")
    private String boxNum;

    /**
     * 包装种类
     */
    @JsonProperty("boxType")
    private String boxType;

    /**
     * 毛重(KG)
     */
    @JsonProperty("grossWeight")
    private String grossWeight;

    /**
     * 净重(KG)
     */
    @JsonProperty("netWeight")
    private String netWeight;

    /**
     * 贸易国(地区)
     */
    @JsonProperty("tradingCountry")
    private String tradingCountry;

    /**
     * 关联报关单号
     */
    @JsonProperty("relativeEntryNo")
    private String relativeEntryNo;

    /**
     * 关联备案号
     */
    @JsonProperty("relativeEnrolNo")
    private String relativeEnrolNo;

    /**
     * 保税/监管场所
     */
    @JsonProperty("bsjgPlace")
    private String bsjgPlace;

    /**
     * 货场代码
     */
    @JsonProperty("yardCode")
    private String yardCode;
    
    /**
     * 报关单号
     */
    @JsonProperty("PrerecordSn")
    private String prerecordSn;
    
    /**
     * 报关时间
     */
    @JsonProperty("PrerecordSnTime")
    private Date prerecordSnTime; 
}
