/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.exceptions.handler;

import com.citius.reservas.business.AccessBusiness;
import com.citius.reservas.controllers.customModel.LoginStatus;
import com.citius.reservas.exceptions.*;
import com.citius.reservas.exceptions.handler.errorResolver.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.util.CollectionUtils;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

/**
 *
 * @author Esther Álvarez Feijoo
 */
public class ApplicationHandlerException extends AbstractHandlerExceptionResolver {

    private static final Logger log = LoggerFactory.getLogger(ApplicationHandlerException.class);
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    protected AccessBusiness accessBusiness;
    @Autowired
    private NotAvaliableResourceResolver notAvaliableResourceResolver;
    @Autowired
    private InputRequestValidationResolver inputRequestValidationResolver;
    @Autowired
    private ThrowableResolver throwableResolver;
    private static final Map<String, RestError> exceptionMappings;

    static {
        exceptionMappings = new HashMap<>();
        //HTTP REQUEST CAN'T BE HANDLED
        //when there is no handler method ("action" method) for a specific HTTP request.
        exceptionMappings.put(NoSuchRequestHandlingMethodException.class.getName(), new RestError(HttpStatus.NOT_FOUND, 10001, "error.notFound", "error.dev.notFound"));
        //CUSTOM: HTTP Request that can't be handled
        exceptionMappings.put(UnknownResourceException.class.getName(), new RestError(HttpStatus.NOT_FOUND, 10002, "error.notFound", "error.dev.notFound"));
        //when a request handler does not support a specific request method.
        exceptionMappings.put(HttpRequestMethodNotSupportedException.class.getName(), new RestError(HttpStatus.METHOD_NOT_ALLOWED, 10003, "error.httpFail", "error.dev.httpRequestMethodNotSupported"));

        //HTTP REQUEST HAS HANDLED, BUT READING REQUEST FAILS
        //when a client POSTs, PUTs, or PATCHes content of a type not supported by request handler.
        exceptionMappings.put(HttpMediaTypeNotSupportedException.class.getName(), new RestError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, 10011, "error.httpFail", "error.dev.mediaTypeNotSupported"));
        //HttpMessageConverter implementations when the read method fails.
        exceptionMappings.put(HttpMessageNotReadableException.class.getName(), new RestError(HttpStatus.BAD_REQUEST, 10012, "error.httpFail", "error.dev.messageNotReadable"));
        //indicates a missing parameter
        exceptionMappings.put(MissingServletRequestParameterException.class.getName(), new RestError(HttpStatus.BAD_REQUEST, 10013, "error.httpFail", "error.dev.missingServletRequestParameter"));

        //VALIDATION OF INPUT PARAMETERS
        //type mismatch when trying to set a bean property
        exceptionMappings.put(TypeMismatchException.class.getName(), new RestError(HttpStatus.BAD_REQUEST, 10021, "error.httpFail", "error.dev.typeMismatch"));
        //GENERAL: all Bean Validation "unexpected" problems
        exceptionMappings.put(javax.validation.ValidationException.class.getName(), new RestError(HttpStatus.BAD_REQUEST, 10022, "error.httpFail", "error.dev.validation"));

        //HTTP RESPONSE
        //When there isn't the request message
        exceptionMappings.put(NoSuchMessageException.class.getName(), new RestError(HttpStatus.SERVICE_UNAVAILABLE, 10031, "error.viewRenderFail", "error.dev.noSuchMessage"));
        //when the request handler cannot generate a response that is acceptable by the client.
        exceptionMappings.put(HttpMediaTypeNotAcceptableException.class.getName(), new RestError(HttpStatus.NOT_ACCEPTABLE, 10032, "error.notFound", "error.dev.httpMediaTypeNotAcceptable"));
        //When the file can't be found
        exceptionMappings.put(NoSuchMessageException.class.getName(), new RestError(HttpStatus.SERVICE_UNAVAILABLE, 10031, "error.viewRenderFail", "error.dev.fileNotFound"));


        //DATABASE
        exceptionMappings.put(org.hibernate.ObjectNotFoundException.class.getName(), new RestError(HttpStatus.NOT_FOUND, 10101, "error.db.objectNotFound", "error.dev.db.objectNotFound"));
        //insert or update data results in violation of an integrity constraint
        exceptionMappings.put(org.springframework.dao.DataIntegrityViolationException.class.getName(), new RestError(HttpStatus.CONFLICT, 10102, "error.db.dataIntegrityViolation", "error.dev.dataIntegrityViolation"));
        exceptionMappings.put(org.hibernate.exception.ConstraintViolationException.class.getName(), new RestError(HttpStatus.CONFLICT, 10102, "error.db.dataIntegrityViolation", "error.dev.dataIntegrityViolation"));
        exceptionMappings.put(javax.validation.ConstraintViolationException.class.getName(), new RestError(HttpStatus.CONFLICT, 10102, "error.db.dataIntegrityViolation", "error.dev.dataIntegrityViolation"));

        // 500
        exceptionMappings.put(javax.servlet.ServletException.class.getName(), new RestError(HttpStatus.INTERNAL_SERVER_ERROR, 10601, "error.default", "error.dev.ServletException"));
        exceptionMappings.put(Throwable.class.getName(), new RestError(HttpStatus.INTERNAL_SERVER_ERROR, 10600, "error.default", "error.dev.default"));

        //AccessDenied
        exceptionMappings.put(AccessDeniedException.class.getName(), new RestError(HttpStatus.METHOD_NOT_ALLOWED, 10201, "error.accessDenied", "error.dev.accessDenied"));
        exceptionMappings.put(org.springframework.security.authentication.AuthenticationCredentialsNotFoundException.class.getName(), new RestError(HttpStatus.METHOD_NOT_ALLOWED, 10202, "error.accessDenied", "error.dev.accessDenied"));
        exceptionMappings.put(org.springframework.security.core.AuthenticationException.class.getName(), new RestError(HttpStatus.METHOD_NOT_ALLOWED, 10202, "error.accessDenied", "error.dev.accessDenied"));
        
        //APPLICATION EXCEPTIONS
        exceptionMappings.put(NotAvaliableException.class.getName(), new RestError(HttpStatus.NOT_MODIFIED, 10901));
        exceptionMappings.put(NotPossibleInstancesException.class.getName(), new RestError(HttpStatus.NOT_MODIFIED, 10902, "error.default", "error.dev.default"));
        exceptionMappings.put(InputRequestValidationException.class.getName(), new RestError(HttpStatus.BAD_REQUEST, 10910));
    }

    public ApplicationHandlerException() throws Exception {
    }

    public ObjectMapper getMapper() {
        return mapper;
    }

    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public Map<String, RestError> getExceptionMappings() {
        return exceptionMappings;
    }

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception ex) {

        Object error = this.getRestError(request, ex);

        ServletWebRequest webRequest = new ServletWebRequest(request, response);
        HttpInputMessage inputMessage = new ServletServerHttpRequest(webRequest.getRequest());
        List<MediaType> acceptedMediaTypes = inputMessage.getHeaders().getAccept();

        if (acceptedMediaTypes.contains(MediaType.APPLICATION_JSON)) {
            return this.generateJsonView(error);
        } else {
            return this.generateJspView(error, request);
        }
    }

    private ModelAndView generateJsonView(Object error) {
        try {
            mapper.writeValueAsString(error);
        } catch (IOException ex) {
            logger.error(ex);
        }
        return null;
    }

    private ModelAndView generateJspView(Object error, HttpServletRequest request) {
        ModelAndView m = new ModelAndView();

        if (error.getClass().equals(RestError.class)
                && ((RestError) error).getStatus().equals(HttpStatus.METHOD_NOT_ALLOWED)) {

            LoginStatus loginStatus = this.accessBusiness.getLoginStatus();

            //Not found
            if (!loginStatus.isLoggedIn()) {
                m.setViewName("redirect:/login");
                return m;
            } else if (loginStatus.isLoggedIn() && !loginStatus.isAdmin()) {
                Exception exception = new UnknownResourceException();
                error = this.getRestError(request, exception);
            }
        }

        m.setViewName("redirect:/");
        m.addObject("error", error);

        return m;
    }

    private Object getRestError(HttpServletRequest request, Exception ex) {
        Map<String, RestError> mappings = this.exceptionMappings;

        if (CollectionUtils.isEmpty(mappings)) {
            return null;
        }

        RestError error = null;
        String dominantMapping = null;
        int deepest = Integer.MAX_VALUE;
        for (Map.Entry<String, RestError> entry : mappings.entrySet()) {
            String key = entry.getKey();
            int depth = getDepth(key, ex);
            if (depth >= 0 && depth < deepest) {
                deepest = depth;
                dominantMapping = key;
                error = new RestError(entry.getValue());
            }
        }

        if (error == null) {
            return null;
        }

        log.debug("Resolving to RestError template '" + error + "' for exception of type [" + ex.getClass().getName()
                + "], based on exception mapping [" + dominantMapping + "]");

        return populateError(error, request, ex);

    }

    public Object populateError(RestError error, HttpServletRequest request, Throwable exception) {
        GenericErrorResolver resolver;

        if (exception.getClass().equals(NotAvaliableException.class)) {
            resolver = this.notAvaliableResourceResolver;
        } else if (exception.getClass().equals(InputRequestValidationException.class)) {
            resolver = this.inputRequestValidationResolver;
        } else {
            resolver = this.throwableResolver;
        }

        return resolver.populateError(error, request, exception);
    }

    /**
     * Return the depth to the superclass matching. 0 means ex matches exactly.
     * Returns -1 if there's no match. Otherwise, returns depth. Lowest depth
     * wins.
     */
    private int getDepth(String exceptionName, Exception ex) {
        return getDepth(exceptionName, ex.getClass(), 0);
    }

    /*Recursive method*/
    private int getDepth(String exceptionName, Class exceptionClass, int depth) {

        if (exceptionClass.getName().contains(exceptionName)) {
            return depth;
        }

        // If haven't found
        if (exceptionClass.equals(Throwable.class)) {
            return -1;
        }

        return getDepth(exceptionName, exceptionClass.getSuperclass(), depth + 1);
    }
//    @PostConstruct
//    public void afterPropertiesSet() throws Exception {
//        
//    }
}
