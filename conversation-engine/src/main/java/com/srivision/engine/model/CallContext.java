package com.srivision.engine.model;

public class CallContext {
    private ConversationState currentState;

    public ConversationState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(ConversationState currentState) {
        this.currentState = currentState;
    }
}
