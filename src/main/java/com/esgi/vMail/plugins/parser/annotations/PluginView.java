package com.esgi.vMail.plugins.parser.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target( ElementType.TYPE )
@Retention( RetentionPolicy.RUNTIME )
public @interface PluginView {
	enum ViewType {
		   FULL, PANE, ICON
	}
	ViewType viewType() default ViewType.FULL;
	boolean isStyleInherit() default true;
}
