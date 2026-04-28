package com.srivision.voice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/voice")
public class TwilioWebhookController {

    private final TenantConfigService tenantConfigService;

    public TwilioWebhookController(TenantConfigService tenantConfigService) {
        this.tenantConfigService = tenantConfigService;
    }

    @PostMapping(value = "/inbound", produces = "application/xml")
    public Mono<ResponseEntity<String>> handleInbound(@RequestParam("CallSid") String callSid) {
        return tenantConfigService.resolveConfig(callSid)
            .map(config -> buildTwimlStream(config.getStreamHost(), callSid))
            .map(ResponseEntity::ok)
            .orElseGet(this::handleResolutionFailure);
    }

    private String buildTwimlStream(String host, String callSid) {
        return String.format("<Response><Connect><Stream url=\"wss://%s/media-stream\">" +
            "<Parameter name=\"callSid\" value=\"%s\"/></Stream></Connect></Response>", host, callSid);
    }

    private Mono<ResponseEntity<String>> handleResolutionFailure() {
        return Mono.just(ResponseEntity.badRequest().body("<Response><Reject/></Response>"));
    }
}
