package com.datajoy.admin_builder.view.dto;

import com.datajoy.admin_builder.code.CodeResponse;
import com.datajoy.admin_builder.view.domain.ViewAction;
import com.datajoy.admin_builder.view.domain.ViewCode;
import com.datajoy.admin_builder.view.domain.ViewObject;
import com.datajoy.admin_builder.view.domain.ViewObjectContent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor @Builder
public class ViewDto {
    private ViewObject viewObject;
    private ViewObjectContent viewObjectContent;
    private List<ViewAction> viewActions;
    private Map<String, List<CodeResponse>> codeMap;
}
