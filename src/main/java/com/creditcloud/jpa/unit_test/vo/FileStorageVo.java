package com.creditcloud.jpa.unit_test.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Data
public class FileStorageVo implements Serializable {

    private Date lastSysnTime;

    private Date lastSearchTime;

    private Integer fileSize;

    private Map<String,Long> fileDatas;

}
