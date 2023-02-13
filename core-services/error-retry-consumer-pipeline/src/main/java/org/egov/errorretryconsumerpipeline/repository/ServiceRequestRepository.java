package org.egov.errorretryconsumerpipeline.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.egov.tracer.model.CustomException;
import org.egov.tracer.model.ServiceCallException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Repository
@Slf4j
public class ServiceRequestRepository {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private RestTemplate restTemplate;

 /*
    @Autowired
    public ServiceRequestRepository(ObjectMapper mapper, RestTemplate restTemplate) {
        this.mapper = mapper;
        this.restTemplate = restTemplate;
    }
*/

    public Object fetchResult(StringBuilder uri, Object request) {
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        Object response = null;
        try {
            response = restTemplate.postForObject(uri.toString(), request, Map.class);
        }catch(HttpClientErrorException e) {
            log.error("External Service threw an Exception: ",e);
            throw new ServiceCallException(e.getResponseBodyAsString());
        }catch(Exception e) {
            log.error("Exception while fetching from searcher: ",e);
            throw new CustomException("EG_RETRY_ERR", "Error while retrying consumption of failed resource");
        }

        return response;
    }
}
