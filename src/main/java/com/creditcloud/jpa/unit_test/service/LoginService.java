/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.service;

import com.creditcloud.jpa.unit_test.model.PtuiCheckVK;

/**
 *
 * @author MRB
 */
public interface LoginService {
    
    PtuiCheckVK isSafeLogin(String qq,String loginSig);
    
    Boolean tryLogin(String qq,String loginSig ,PtuiCheckVK vK);
}
