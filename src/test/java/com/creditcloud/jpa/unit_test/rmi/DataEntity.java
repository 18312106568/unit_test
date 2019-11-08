package com.creditcloud.jpa.unit_test.rmi;

import lombok.Data;

import java.io.Serializable;

@Data
public class DataEntity implements Serializable {
    private String name;
}
