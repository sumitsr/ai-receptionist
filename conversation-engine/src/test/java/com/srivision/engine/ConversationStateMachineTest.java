package com.srivision.engine;

import com.srivision.engine.model.CallContext;
import com.srivision.engine.model.ConversationState;
import com.srivision.engine.model.Intent;
import com.srivision.engine.model.TransitionError;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.result4j.Result;
import static org.assertj.core.api.Assertions.assertThat;

class ConversationStateMachineTest {

    private final ConversationStateMachine machine = new ConversationStateMachine();

    @Test
    void shouldTransitionFromGreetingToQualifyOnNewBooking() {
        CallContext context = Instancio.create(CallContext.class);
        context.setCurrentState(ConversationState.GREETING);

        Result<ConversationState, TransitionError> result = machine.advance(context, Intent.NEW_BOOKING);

        assertThat(result.isSuccess()).isTrue();
        assertThat(result.get()).isEqualTo(ConversationState.QUALIFY);
    }
}
