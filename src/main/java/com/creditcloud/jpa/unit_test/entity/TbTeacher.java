package com.creditcloud.jpa.unit_test.entity;

import com.creditcloud.jpa.unit_test.entity.base.EntityId;
import javax.persistence.*;
import java.util.Date;
import lombok.Data;


@Data
@Entity
@Table(name = "tb_teacher")
public class TbTeacher extends EntityId{

    @Column(name = "ID")
    private String id;

    @Column(name = "T_NAME")
    private String tName;

    @Column(name = "T_PASSWORD")
    private String tPassword;

    @Column(name = "SEX")
    private String sex;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "PIC_URL")
    private String picUrl;

    @Column(name = "SCHOOL_NAME")
    private String schoolName;

    @Column(name = "REGIST_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registDate;

    @Column(name = "REMARK")
    private String remark;

}

