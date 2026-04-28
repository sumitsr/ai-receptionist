package com.srivision.voice;
public record TenantConfig(String streamHost, String tenantId, String businessNumber) {
    public String getStreamHost() { return streamHost; }
    public String getTenantId() { return tenantId; }
    public String getBusinessNumber() { return businessNumber; }
}
