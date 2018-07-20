/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.entity;

import com.creditcloud.jpa.unit_test.entity.base.EntityId;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author MRB
 */
@Entity
@Table(name = "TB_PRODUCT")
@Data
public class Product extends EntityId {

    /**
     * 总价值 ,每个产品的价值为1W
     */
    @Column(name = "TOTAL_AMOUNT")
    private Integer totalAmount;
}
