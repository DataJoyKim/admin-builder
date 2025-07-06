package com.datajoy.admin_builder.datasource.restserver;

import com.datajoy.admin_builder.datasource.ConnectValidation;
import com.datajoy.admin_builder.datasource.LookupKey;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Component
public class DataSourceRestServerValidator {
    public ConnectValidation validateConnect(DataSourceRestServer metadata, Map<LookupKey, RestClient> dataSourceMap) {
        ConnectValidation validate = new ConnectValidation();

        LookupKey lookupKey = LookupKey.generateKey(metadata.getDataSourceName());

        RestClient restClient = dataSourceMap.get(lookupKey);

        try {
            ResponseEntity<String> response = restClient.get()
                    .retrieve()
                    .toEntity(String.class);

            validate.setResult(response.getStatusCode().is2xxSuccessful());
        }
        catch (Exception e) {
            validate.setResult(false);
            validate.setErrorStack(e);
        }

        return validate;
    }
}
