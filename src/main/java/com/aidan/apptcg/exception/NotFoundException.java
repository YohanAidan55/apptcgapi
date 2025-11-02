package com.aidan.apptcg.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

@Getter
public class NotFoundException extends RuntimeException {

    private final Class<?> entityClass;
    private final Object id;
    private final ProblemDetail problemDetail;

    public NotFoundException(Class<?> entityClass, Object id) {
        super(entityClass.getSimpleName() + " not found with id: " + id);

        this.entityClass = entityClass;
        this.id = id;

        this.problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        this.problemDetail.setTitle(entityClass.getSimpleName() + " not found");
        this.problemDetail.setDetail("Resource " + entityClass.getSimpleName() + " with id [" + id + "] was not found.");
    }

}

