/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.game;

import java.util.concurrent.locks.ReentrantLock;
import lombok.Data;

/**
 *
 * @author MRB
 */
@Data
public class Player {
   private ReentrantLock lock;
   private Integer id;
   
   public Player(Integer id){
       this.id = id;
       lock = new ReentrantLock();
   }
}
