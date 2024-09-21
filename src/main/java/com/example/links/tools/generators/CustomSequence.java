package com.example.links.tools.generators;

import org.hibernate.annotations.IdGeneratorType;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@IdGeneratorType(MyIdGenerator.class)
@Retention(RUNTIME)
@Target({METHOD, FIELD})
public @interface CustomSequence {
    String name();
}
