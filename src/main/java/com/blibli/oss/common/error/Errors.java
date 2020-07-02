package com.blibli.oss.common.error;

import com.blibli.oss.common.metadata.MetaData;
import com.blibli.oss.common.metadata.MetaDatas;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author Eko Kurniawan Khannedy
 */
@Slf4j
public class Errors {

  /**
   * Create errors from binding result with default locale
   *
   * @param result        binding result
   * @param messageSource message source
   * @return errors or empty map
   */
  public static Map<String, List<String>> from(BindingResult result, MessageSource messageSource) {
    return from(result, messageSource, Locale.getDefault());
  }

  /**
   * Create errors from binding result
   *
   * @param result        binding result
   * @param messageSource message source
   * @param locale        locale
   * @return errors or empty map
   */
  public static Map<String, List<String>> from(BindingResult result, MessageSource messageSource, Locale locale) {
    if (result.hasFieldErrors()) {
      Map<String, List<String>> map = new HashMap<>();

      for (FieldError fieldError : result.getFieldErrors()) {
        String field = fieldError.getField();

        if (!map.containsKey(fieldError.getField())) {
          map.put(field, new ArrayList<>());
        }

        String errorMessage = messageSource.getMessage(fieldError.getCode(), fieldError.getArguments(), fieldError.getDefaultMessage(), locale);
        map.get(field).add(errorMessage);
      }

      return map;
    } else {
      return Collections.emptyMap();
    }
  }

  /**
   * Get errors mapping from constrain violations
   *
   * @param constraintViolations constrain violations
   * @return errors
   */
  public static Map<String, List<String>> from(Set<ConstraintViolation<?>> constraintViolations) {
    Map<String, List<String>> map = new HashMap<>();

    constraintViolations.forEach(violation -> {
      for (String attribute : getAttributes(violation)) {
        putEntry(map, attribute, violation.getMessage());
      }
    });

    return map;
  }

  public static Map<String, Map<String, String>> getMetadata(Set<ConstraintViolation<?>> constraintViolations) {
    Map<String, Map<String, String>> metadata = new HashMap<>();

    constraintViolations.forEach(violation -> {
      try {
        Class<?> beanClass = violation.getLeafBean().getClass();

        String field = "";
        for (Path.Node node : violation.getPropertyPath()) {
          field = node.getName();
        }
        Field declaredField = beanClass.getDeclaredField(field);

        MetaDatas metaDatas = declaredField.getAnnotation(MetaDatas.class);
        Map<String, String> values = new HashMap<>();

        if (metaDatas != null) {
          for (MetaData metaData : metaDatas.value()) {
            values.put(metaData.key(), metaData.value());
          }
        }

        for (String attribute : getAttributes(violation)) {
          metadata.put(attribute, values);
        }
      } catch (Throwable throwable) {
        log.info(throwable.getMessage(), throwable);
      }
    });

    return metadata;
  }

  private static void putEntry(Map<String, List<String>> map, String key, String value) {
    if (!map.containsKey(key)) {
      map.put(key, new ArrayList<>());
    }
    map.get(key).add(value);
  }

  private static String[] getAttributes(ConstraintViolation<?> constraintViolation) {
    String[] values = (String[]) constraintViolation.getConstraintDescriptor().getAttributes().get("path");
    if (values == null || values.length == 0) {
      return getAttributesFromPath(constraintViolation);
    } else {
      return values;
    }
  }

  private static String[] getAttributesFromPath(ConstraintViolation<?> constraintViolation) {
    Path path = constraintViolation.getPropertyPath();

    StringBuilder builder = new StringBuilder();
    path.forEach(node -> {
      if (node.getName() != null) {
        if (builder.length() > 0) {
          builder.append(".");
        }

        builder.append(node.getName());
      }
    });

    return new String[]{builder.toString()};
  }

}
