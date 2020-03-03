package com.mindgeek.samplecode.util;

import com.mindgeek.samplecode.domainobject.AppUserPrincipal;
import com.mindgeek.samplecode.exception.DataValidationException;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AppUtil {

    public AppUserPrincipal getCurrentUser(){
        return (AppUserPrincipal)  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    /**
     * Collect validation error while binding
     * @param bindingResult used in case of binding error
     * @throws DataValidationException in case any error found while validation
     */
    public void validateBindingResult(BindingResult bindingResult) throws DataValidationException{
        if(bindingResult.hasErrors()){
            throw new DataValidationException(bindingResult.getAllErrors()
                    .stream()
                    .map((errorObject) -> {
                        return errorObject.getDefaultMessage();
                    }).collect(Collectors.toList()).toString());
        }
    }


}
