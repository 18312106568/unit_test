/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.entity;

import com.creditcloud.jpa.unit_test.entity.base.EntityId;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;

/**
 *
 * @author MRB
 */
@Entity
@Table(name = "TB_QQ_LOGIN")
@Data
public class QQLogin extends EntityId {
    
    @Column(name="UIN")
    private String uin ;
    
    @Column(name = "PASSWORD")
    private String password;
    
    @Column(name ="COOKIES")
    private String cookies ;
    
    @Column(name = "EXPIRE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expireTime;
    
    @Column(name = "CREATE_DATE" ,nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    
    @Column(name = "UPDATE_DATE" ,nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;
    
}
