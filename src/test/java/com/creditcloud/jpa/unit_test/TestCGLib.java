/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test;

import com.creditcloud.jpa.unit_test.proxy.Ship;
import com.creditcloud.jpa.unit_test.proxy.ShipProxy;
import org.junit.Test;

/**
 *
 * @author MRB
 */
public class TestCGLib {
    
    @Test
    public void testProxy(){
        ShipProxy Proxy = new ShipProxy();
        Ship ship = (Ship)Proxy.getProxy(Ship.class);
        ship.travel();
    }
}
