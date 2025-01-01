package com.datajoy.admin_builder.apibuilder.entity;

import com.datajoy.admin_builder.apibuilder.entity.code.EntityResultCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Builder
public class EntityResult {
    private EntityResultCode resultCode;
    private List<EntityResultElement> resultElements;

    public static EntityResult createEntityResult(EntityResultCode resultCode, List<EntityResult.EntityResultElement> results) {
        return EntityResult.builder()
                .resultCode(resultCode)
                .resultElements(results)
                .build();
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class EntityResultElement {
        private String seq;
        private EntityResultCode resultCode;
        private String message;
        private List<Map<String, Object>> results;

        public static EntityResultElement success(String seq, List<Map<String, Object>> results) {
            return EntityResult.EntityResultElement.builder()
                    .seq(seq)
                    .resultCode(EntityResultCode.SUCCESS)
                    .message("성공하였습니다.")
                    .results(results)
                    .build();
        }

        public static EntityResultElement failed(String seq, String failedMsg) {
            return EntityResult.EntityResultElement.builder()
                    .seq(seq)
                    .resultCode(EntityResultCode.FAILURE)
                    .message(failedMsg)
                    .results(new ArrayList<>())
                    .build();
        }
    }
}
