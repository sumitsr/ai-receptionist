package com.srivision.voice;
import org.result4j.Result;
import org.springframework.stereotype.Service;
@Service
public class TenantConfigService {
    public Result<TenantConfig, String> resolveConfig(String callSid) {
        return callSid != null 
            ? Result.success(new TenantConfig("localhost:8080", "tenant-001", "+448001234567"))
            : Result.failure("Invalid CallSid");
    }
}
