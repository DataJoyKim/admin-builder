package com.datajoy.admin_builder.message;

import groovy.lang.Binding;
import groovy.lang.GroovyRuntimeException;
import groovy.lang.GroovyShell;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Slf4j
@Service
@RequiredArgsConstructor
public class MessageProcessorService {
    private final MessageProcessorRepository messageProcessorRepository;

    public MessageProcessorResult execute(String processorName, MessageProcessorRequest params) {
        try {
            Optional<MessageProcessor> customCodeOptional = messageProcessorRepository.findByProcessorName(processorName);
            if(customCodeOptional.isEmpty()) {
                throw new RuntimeException("요청한 리소스가 존재하지않습니다. [code:"+processorName+"]");
            }

            MessageProcessor messageProcessor = customCodeOptional.get();
            Binding binding = new Binding();
            binding.setVariable("params", params.getContents());

            GroovyShell shell = new GroovyShell(binding);

            Object result = shell.evaluate(messageProcessor.getScript());

            return MessageProcessorResult.createSuccessMessage(result);
        }
        catch (GroovyRuntimeException e) {
            log.error("error",e);
            return MessageProcessorResult.createErrorMessage("E-CCD-001", e.getMessage());
        }
        catch (Exception e) {
            log.error("error",e);
            return MessageProcessorResult.createErrorMessage("E-CCD-999", e.getMessage());
        }
    }
}
