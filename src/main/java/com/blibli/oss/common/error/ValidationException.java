package com.blibli.oss.common.error;

import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * @author Eko Kurniawan Khannedy
 */
@SuppressWarnings("unchecked")
public class ValidationException extends RuntimeException {

  private Set<ConstraintViolation<?>> constraintViolations;

  public ValidationException(Set constraintViolations) {
    this(Errors.from(constraintViolations).toString(), constraintViolations);
  }

  public ValidationException(String message, Set constraintViolations) {
    this(message, null, constraintViolations);
  }

  public ValidationException(String message, Throwable cause, Set constraintViolations) {
    super(message, cause);
    this.constraintViolations = constraintViolations;
  }

  public Set<ConstraintViolation<?>> getConstraintViolations() {
    return constraintViolations;
  }
}
