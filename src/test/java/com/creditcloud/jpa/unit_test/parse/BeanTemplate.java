/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.parse;

import java.util.List;
import lombok.Data;

/**
 *
 * @author MRB
 */
@Data
public class BeanTemplate {
    private String packageName;
    private String className;
    private List<String> importList;
    private List<Field> fieldList;
}
