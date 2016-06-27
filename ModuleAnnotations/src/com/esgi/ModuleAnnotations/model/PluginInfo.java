package com.esgi.ModuleAnnotations.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Date;

@Target( ElementType.PACKAGE )
@Retention( RetentionPolicy.RUNTIME )
public @interface PluginInfo {
	String title() default "A plugin";
	String titleShort() default "plug";
	String author() default "John Doe";
	String creationDate() default "2001-01-01";
	String version() default "alpha.5";
}
