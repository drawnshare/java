package com.esgi.vMail.plugins.parser.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.jar.JarFile;

@Target( ElementType.TYPE )
@Retention( RetentionPolicy.RUNTIME )
public @interface PluginView {
	public enum ViewType {
		   FULL, PANE, ICON
	}
	ViewType viewType() default ViewType.FULL;
	boolean isStyleInherit() default true;
}
