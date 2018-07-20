/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.repository;

import com.creditcloud.jpa.unit_test.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author MRB
 */
public interface ProductRepository extends JpaRepository<Product, String> {
}
