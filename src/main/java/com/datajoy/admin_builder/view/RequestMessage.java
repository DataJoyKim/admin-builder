package com.datajoy.admin_builder.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.util.Map;

@Getter
public class RequestMessage {
    private BodyMessage body;
    private HeaderMessage header;

    public static RequestMessage createRequestMessage(ObjectMapper mapper, Map<String, String> params) {
        String requestMessage = params.get("requestMessage");
        try {
            return mapper.readValue(requestMessage, RequestMessage.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Getter
    public static class BodyMessage{
        private String objectCd;
    }

    @Getter
    public static class HeaderMessage{
        private String companyCd;
        private String localeCd;
    }
}
