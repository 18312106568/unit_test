package com.creditcloud.jpa.unit_test.designModel;

public class ResponsibilityDesign {
    /*
    public abstract class Handler {
        protected Handler successor = null;
        public void setSuccessor(Handler successor) {
            this.successor = successor;
        }
        public abstract void handle();
    }
    public class HandlerA extends Handler {
        @Override
        public void handle() {
            boolean handled = false;
            //...
            if (!handled && successor != null) {
                successor.handle();
            }
        }
    }
    public class HandlerB extends Handler {
        @Override
        public void handle() {
            boolean handled = false;
            //...
            if (!handled && successor != null) {
                successor.handle();
            }
        }
    }
    */


    public abstract class Handler {
        protected Handler successor = null;
        public void setSuccessor(Handler successor) {
            this.successor = successor;
        }
        public final void handle() {
            boolean handled = doHandle();
            if (successor != null && !handled) {
                successor.handle();
            }
        }
        protected abstract boolean doHandle();
    }
    public class HandlerA extends Handler {
        @Override
        protected boolean doHandle() {
            boolean handled = false;
            //...
            return handled;
        }
    }
    public class HandlerB extends Handler {
        @Override
        protected boolean doHandle() {
            boolean handled = false;
            //...
            return handled;
        }
    }


    public class HandlerChain {
        private Handler head = null;
        private Handler tail = null;
        public void addHandler(Handler handler) {
            handler.setSuccessor(null);
            if (head == null) {
                head = handler;
                tail = handler;
                return;
            }
            tail.setSuccessor(handler);
            tail = handler;
        }
        public void handle() {
            if (head != null) {
                head.handle();
            }
        }
    }
}
