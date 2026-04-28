package com.srivision.engine;

import com.srivision.engine.model.CallContext;
import com.srivision.engine.model.ConversationState;
import com.srivision.engine.model.Intent;
import com.srivision.engine.model.TransitionError;
import org.result4j.Result;
import org.springframework.stereotype.Component;

@Component
public class ConversationStateMachine {

    public Result<ConversationState, TransitionError> advance(CallContext ctx, Intent intent) {
        return validateContext(ctx)
            .flatMap(validCtx -> determineNext(validCtx.getCurrentState(), intent))
            .map(newState -> applyState(ctx, newState));
    }

    private Result<CallContext, TransitionError> validateContext(CallContext ctx) {
        return ctx != null ? Result.success(ctx) : Result.failure(TransitionError.INVALID_CONTEXT);
    }

    private Result<ConversationState, TransitionError> determineNext(ConversationState current, Intent intent) {
        // Stubbed state resolution mapping
        if (current == ConversationState.GREETING && intent == Intent.NEW_BOOKING) {
            return Result.success(ConversationState.QUALIFY);
        }
        return Result.failure(TransitionError.UNEXPECTED_INTENT);
    }

    private ConversationState applyState(CallContext ctx, ConversationState newState) {
        ctx.setCurrentState(newState);
        return newState;
    }
}
