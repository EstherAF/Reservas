/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.exceptions.handler;


import com.citius.reservas.exceptions.InputRequestValidationException;
import com.citius.reservas.exceptions.NotAvaliableException;
import com.citius.reservas.exceptions.NotPossibleInstancesException;
import com.citius.reservas.exceptions.UnknownResourceException;
import com.citius.reservas.exceptions.handler.errorResolver.GenericErrorResolver;
import com.citius.reservas.exceptions.handler.errorResolver.InputRequestValidationResolver;
import com.citius.reservas.exceptions.handler.errorResolver.NotAvaliableResourceResolver;
import com.citius.reservas.exceptions.handler.errorResolver.ThrowableResolver;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
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
import org.springframework.util.CollectionUtils;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

/**
 *
 * @author Esther Álvarez Feijoo
 */
public class ApplicationHandlerException extends AbstractHandlerExceptionResolver {

    private static final Logger log = LoggerFactory.getLogger(ApplicationHandlerException.class);

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private NotAvaliableResourceResolver notAvaliableResourceResolver;
    @Autowired
    private InputRequestValidationResolver inputRequestValidationResolver;
    @Autowired
    private ThrowableResolver throwableResolver;
    
    private Map<String, RestError> exceptionMappings;
    
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

    public void setExceptionMappings(Map<String, RestError> exceptionMappings) {
        this.exceptionMappings = exceptionMappings;
    }

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception ex) {

        ModelAndView m = new ModelAndView();

        Object error = this.getRestError(request, ex);

        ServletWebRequest webRequest = new ServletWebRequest(request, response);
        HttpInputMessage inputMessage = new ServletServerHttpRequest(webRequest.getRequest());
        List<MediaType> acceptedMediaTypes = inputMessage.getHeaders().getAccept();

        if (acceptedMediaTypes.contains(MediaType.APPLICATION_JSON)) {

            MappingJacksonJsonView view = new MappingJacksonJsonView();
            view.setObjectMapper(mapper);
            m.setView(view);
            m.addObject(null, error);

        } else {
            m.setViewName("redirect:/");
            m.addObject("error", error);
        }
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
                error = entry.getValue();
            }
        }

        if (error == null) {
            return null;
        }

        log.debug("Resolving to RestError template '" + error + "' for exception of type [" + ex.getClass().getName()
                + "], based on exception mapping [" + dominantMapping + "]");
        
        return populateError(error, request, ex);

    }
    
    public Object populateError(RestError error, HttpServletRequest request, Throwable exception){
        GenericErrorResolver resolver;
        
        if(exception.getClass().equals(NotAvaliableException.class)){
            resolver= this.notAvaliableResourceResolver;
        }else if(exception.getClass().equals(InputRequestValidationException.class)){
            resolver=this.inputRequestValidationResolver;
        }else{
            resolver=this.throwableResolver;
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

    @PostConstruct
    public void afterPropertiesSet() throws Exception {
        Map<String, RestError> map = new Hashtable<>();

        //HTTP REQUEST CAN'T BE HANDLED
        //when there is no handler method ("action" method) for a specific HTTP request.
        map.put(NoSuchRequestHandlingMethodException.class.getName(), new RestError(HttpStatus.NOT_FOUND, 10001, "error.notFound", "error.dev.notFound"));
        //CUSTOM: HTTP Request that can't be handled
        map.put(UnknownResourceException.class.getName(), new RestError(HttpStatus.NOT_FOUND, 10002, "error.notFound", "error.dev.notFound"));
        //when a request handler does not support a specific request method.
        map.put(HttpRequestMethodNotSupportedException.class.getName(), new RestError(HttpStatus.METHOD_NOT_ALLOWED, 10003, "error.httpFail", "error.dev.httpRequestMethodNotSupported"));

        //HTTP REQUEST HAS HANDLED, BUT READING REQUEST FAILS
        //when a client POSTs, PUTs, or PATCHes content of a type not supported by request handler.
        map.put(HttpMediaTypeNotSupportedException.class.getName(), new RestError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, 10011, "error.httpFail", "error.dev.mediaTypeNotSupported"));
        //HttpMessageConverter implementations when the read method fails.
        map.put(HttpMessageNotReadableException.class.getName(), new RestError(HttpStatus.BAD_REQUEST, 10012, "error.httpFail", "error.dev.messageNotReadable"));
        //indicates a missing parameter
        map.put(MissingServletRequestParameterException.class.getName(), new RestError(HttpStatus.BAD_REQUEST, 10013, "error.httpFail", "error.dev.missingServletRequestParameter"));

        //VALIDATION OF INPUT PARAMETERS
        //type mismatch when trying to set a bean property
        map.put(TypeMismatchException.class.getName(), new RestError(HttpStatus.BAD_REQUEST, 10021, "error.httpFail", "error.dev.typeMismatch"));
        //GENERAL: all Bean Validation "unexpected" problems
        map.put(javax.validation.ValidationException.class.getName(), new RestError(HttpStatus.BAD_REQUEST, 10022, "error.httpFail", "error.dev.validation"));

        //HTTP RESPONSE
        //When there isn't the request message
        map.put(NoSuchMessageException.class.getName(), new RestError(HttpStatus.SERVICE_UNAVAILABLE, 10031, "error.viewRenderFail", "error.dev.noSuchMessage"));
        //when the request handler cannot generate a response that is acceptable by the client.
        map.put(HttpMediaTypeNotAcceptableException.class.getName(), new RestError(HttpStatus.NOT_ACCEPTABLE, 10032, "error.notFound", "error.dev.httpMediaTypeNotAcceptable"));
        //When the file can't be found
        map.put(NoSuchMessageException.class.getName(), new RestError(HttpStatus.SERVICE_UNAVAILABLE, 10031, "error.viewRenderFail", "error.dev.fileNotFound"));


        //DATABASE
        map.put(org.hibernate.ObjectNotFoundException.class.getName(), new RestError(HttpStatus.NOT_FOUND, 10101, "error.db.objectNotFound", "error.dev.db.objectNotFound"));
        //insert or update data results in violation of an integrity constraint
        map.put(org.springframework.dao.DataIntegrityViolationException.class.getName(), new RestError(HttpStatus.CONFLICT, 10102, "error.db.dataIntegrityViolation", "error.dev.dataIntegrityViolation"));
        map.put(org.hibernate.exception.ConstraintViolationException.class.getName(), new RestError(HttpStatus.CONFLICT, 10102, "error.db.dataIntegrityViolation", "error.dev.dataIntegrityViolation"));
        map.put(javax.validation.ConstraintViolationException.class.getName(), new RestError(HttpStatus.CONFLICT, 10102, "error.db.dataIntegrityViolation", "error.dev.dataIntegrityViolation"));

        // 500
        map.put(javax.servlet.ServletException.class.getName(), new RestError(HttpStatus.INTERNAL_SERVER_ERROR, 10601, "error.default", "error.dev.ServletException"));
        map.put(Throwable.class.getName(), new RestError(HttpStatus.INTERNAL_SERVER_ERROR, 10600, "error.default", "error.dev.default"));

        //APPLICATION EXCEPTIONS
        map.put(NotAvaliableException.class.getName(), new RestError(HttpStatus.NOT_MODIFIED, 10901));
        map.put(NotPossibleInstancesException.class.getName(), new RestError(HttpStatus.NOT_MODIFIED, 10902, "error.default", "error.dev.default"));
        map.put(InputRequestValidationException.class.getName(), new RestError(HttpStatus.BAD_REQUEST, 10910));

        this.exceptionMappings = map;
    }
}
