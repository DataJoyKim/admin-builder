package com.datajoy.admin_builder.message;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Data
public class RequestMessage {
    private Map<String, List<Map<String, Object>>> body;
    private Header header;

    @Data
    public static class Header {
        private String workflowId;
    }
}
