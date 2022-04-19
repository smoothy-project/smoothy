package solutions.thex.smoothy.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.io.JsonEOFException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import solutions.thex.smoothy.exception.PayloadIsNotSatisfactoryException;
import solutions.thex.smoothy.generator.ApplicationDescription;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Aspect
@Component
public class BeforeGenerateAspects {

    @Before(value = "execution(* solutions.thex.smoothy.controller.GeneratorController.generate(..)) && args(request, payload)",//
            argNames = "joinPoint,request,payload")
    public void beforeGenerate(JoinPoint joinPoint, HttpServletRequest request, String payload) {
        logRequest(joinPoint, request, payload);
        validatePayload(payload);
    }

    private void logRequest(JoinPoint joinPoint, HttpServletRequest request, String payload) {
        log.info("{}: request: {}, ip: {}, user-agent: {}\n{}",//
                joinPoint.toShortString(),//
                request.getRequestURI(),//
                request.getRemoteAddr(),//
                request.getHeader("User-Agent"),//
                payload);
    }

    private void validatePayload(String payload) {
        if (isJSONValid(payload)) {
            try {
                checkRequires(new ObjectMapper().readValue(payload, ApplicationDescription.class));
            } catch (JsonEOFException e) {
                log.error("solutions.thex.smoothy.aspect.BeforeGenerateAspects.validatePayload(): {}", e.getMessage());
                throw new PayloadIsNotSatisfactoryException(e.getMessage());
            } catch (MismatchedInputException e) {
                log.error("solutions.thex.smoothy.aspect.BeforeGenerateAspects.validatePayload(): {}", e.getMessage());
                throw new PayloadIsNotSatisfactoryException("Missing external type id property 'applicationType'!");
            } catch (JsonProcessingException e) {
                log.error("solutions.thex.smoothy.aspect.BeforeGenerateAspects.validatePayload(): {}", e.getMessage());
                throw new PayloadIsNotSatisfactoryException("Error while parsing JSON!");
            }
        } else {
            try {
                checkRequires(new ObjectMapper(new YAMLFactory()).readValue(payload, ApplicationDescription.class));
            } catch (JsonProcessingException e) {
                log.error("solutions.thex.smoothy.aspect.BeforeGenerateAspects.validatePayload(): {}", e.getMessage());
                throw new PayloadIsNotSatisfactoryException("Error while parsing YAML!");
            }
        }
    }

    private boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }

    private void checkRequires(ApplicationDescription applicationDescription) throws JsonProcessingException {
        if (applicationDescription.getName() == null || applicationDescription.getName().isEmpty())
            throw new PayloadIsNotSatisfactoryException("Application name is required!");
    }

}
