package com.creditcloud.jpa.unit_test.designModel;

public class FiniteStateMachine {

    public enum State {
        SMALL(0),
        SUPER(1),
        FIRE(2),
        CAPE(3);
        private int value;
        private State(int value) {
            this.value = value;
        }
        public int getValue() {
            return this.value;
        }
    }
    public class MarioStateMachine {
        private int score;
        private State currentState;
        public MarioStateMachine() {
            this.score = 0;
            this.currentState = State.SMALL;
        }
        public void obtainMushRoom() {
            if (currentState.equals(State.SMALL)) {
                this.currentState = State.SUPER;
                this.score += 100;
            }
        }
        public void obtainCape() {
            if (currentState.equals(State.SMALL) || currentState.equals(State.SUPER) ) {
                this.currentState = State.CAPE;
                this.score += 200;
            }
        }
        public void obtainFireFlower() {
            if (currentState.equals(State.SMALL) || currentState.equals(State.SUPER) ) {
                this.currentState = State.FIRE;
                this.score += 300;
            }
        }
        public void meetMonster() {
            if (currentState.equals(State.SUPER)) {
                this.currentState = State.SMALL;
                this.score -= 100;
                return;
            }
            if (currentState.equals(State.CAPE)) {
                this.currentState = State.SMALL;
                this.score -= 200;
                return;
            }
            if (currentState.equals(State.FIRE)) {
                this.currentState = State.SMALL;
                this.score -= 300;
                return;
            }
        }
        public int getScore() {
            return this.score;
        }
        public State getCurrentState() {
            return this.currentState;
        }
    }


   /* public interface IMario {
        State getName();
        void obtainMushRoom(MarioStateMachine1 stateMachine);
        void obtainCape(MarioStateMachine1 stateMachine);
        void obtainFireFlower(MarioStateMachine1 stateMachine);
        void meetMonster(MarioStateMachine1 stateMachine);
    }
    public static class SmallMario implements IMario {
        private static final SmallMario instance = new SmallMario();
        private SmallMario() {}
        public static SmallMario getInstance() {
            return instance;
        }
        @Override
        public State getName() {
            return State.SMALL;
        }
        @Override
        public void obtainMushRoom(MarioStateMachine1 stateMachine) {
            stateMachine.setCurrentState(SuperMario.getInstance());
            stateMachine.setScore(stateMachine.getScore() + 100);
        }
        @Override
        public void obtainCape(MarioStateMachine1 stateMachine) {
            stateMachine.setCurrentState(CapeMario.getInstance());
            stateMachine.setScore(stateMachine.getScore() + 200);
        }
        @Override
        public void obtainFireFlower(MarioStateMachine1 stateMachine) {
            stateMachine.setCurrentState(FireMario.getInstance());
            stateMachine.setScore(stateMachine.getScore() + 300);
        }
        @Override
        public void meetMonster(MarioStateMachine1 stateMachine) {
            // do nothing...
        }
    }
    // 省略SuperMario、CapeMario、FireMario类...
    public  class MarioStateMachine1 {
        private int score;
        private IMario currentState;
        public MarioStateMachine1() {
            this.score = 0;
            this.currentState = SmallMario.getInstance();
        }
        public void obtainMushRoom() {
            this.currentState.obtainMushRoom(this);
        }
        public void obtainCape() {
            this.currentState.obtainCape(this);
        }
        public void obtainFireFlower() {
            this.currentState.obtainFireFlower(this);
        }
        public void meetMonster() {
            this.currentState.meetMonster(this);
        }
        public int getScore() {
            return this.score;
        }
        public State getCurrentState() {
            return this.currentState.getName();
        }
        public void setScore(int score) {
            this.score = score;
        }
        public void setCurrentState(IMario currentState) {
            this.currentState = currentState;
        }
    }*/


}
