package com.datajoy.admin_builder.customcode;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomCodeService {
    private final CustomCodeRepository customCodeRepository;

    public CustomCodeResult execute(String codeName, CustomCodeRequest params) {
        try {
            Optional<CustomCode> customCodeOptional = customCodeRepository.findByCodeName(codeName);
            if(customCodeOptional.isEmpty()) {
                throw new RuntimeException("요청한 리소스가 존재하지않습니다. [code:"+codeName+"]");
            }

            CustomCode customCode = customCodeOptional.get();
            Binding binding = new Binding();
            binding.setVariable("params", params.getContents());

            GroovyShell shell = new GroovyShell(binding);

            Object result = shell.evaluate(customCode.getScript());

            return CustomCodeResult.createSuccessMessage(result);
        }
        catch (Exception e) {
            return CustomCodeResult.createErrorMessage(e.getMessage());
        }
    }
}
