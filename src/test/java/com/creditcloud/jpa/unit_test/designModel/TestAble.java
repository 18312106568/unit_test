package com.creditcloud.jpa.unit_test.designModel;

import org.junit.Test;

public class TestAble {
    public class Transaction {
        private String id;
        private Long buyerId;
        private Long sellerId;
        private Long productId;
        private String orderId;
        private Long createTimestamp;
        private Double amount;
        private String walletTransactionId;
        protected boolean isExpired() {
            return true;
        }

        public boolean isTrue(){
            return true;
        }

        // ...get() methods...

        public Transaction(String preAssignedId, Long buyerId, Long sellerId, Long productId, String orderId) {
            if (preAssignedId != null && !preAssignedId.isEmpty()) {
                this.id = preAssignedId;
            } else {
                this.id = null;
            }
            if (!this.id.startsWith("t_")) {
                this.id = "t_" + preAssignedId;
            }
            this.buyerId = buyerId;
            this.sellerId = sellerId;
            this.productId = productId;
            this.orderId = orderId;
            this.createTimestamp = System.currentTimeMillis();
        }
    }

    @Test
    public void testRefactor(){
        Transaction transaction = new Transaction(
                "123",123l,123l,0l,"123"){
            @Override
            public boolean isTrue() {
                return super.isTrue();
            }
        };
    }
}
