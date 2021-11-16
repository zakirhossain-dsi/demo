package com.example.demo.validator;

import com.example.demo.annotation.MultipartFileValid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

@Component
public class MultipartFileValidator implements ConstraintValidator<MultipartFileValid, MultipartFile> {

    private static final String ERROR_MESSAGE_FILE_SIZE = "file size should be less than or equal to %s MB";
    private static final String ERROR_MESSAGE_FILE_TYPE = "Given file type is not supported. Supported file types are %s ";
    private long maxSize;
    private static final long MEGABYTE = 1024L*1024L;
    private List<String> fileTypes;

    @Override
    public void initialize(MultipartFileValid annotation) {
        this.maxSize = annotation.maxSize() * MEGABYTE;
        this.fileTypes = Arrays.asList(annotation.fileTypes());
    }

    @Override
    public boolean isValid(final MultipartFile file, final ConstraintValidatorContext context) {
        var validationStatus = true;

        if(file.getSize() > maxSize){
            context.buildConstraintViolationWithTemplate(String.format(ERROR_MESSAGE_FILE_SIZE, maxSize / MEGABYTE))
                    .addConstraintViolation();
            validationStatus = false;
        }

        if(!fileTypes.contains(getFileExtension(file))){
            context.buildConstraintViolationWithTemplate(String.format(ERROR_MESSAGE_FILE_TYPE, fileTypes))
                .addConstraintViolation();
            validationStatus = false;
        }
        return validationStatus;
    }

    private String getFileExtension(MultipartFile file){

        var fileName = file.getOriginalFilename();
        if(StringUtils.isEmpty(fileName)){
            return "";
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
