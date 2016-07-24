package com.esgi.vMail.plugins.parser.process;

import javax.annotation.processing.*;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.util.Set;

@SupportedAnnotationTypes("com.esgi.vMail.plugins.parser.annotations.PluginsInfo")
public class PluginAnnotationReader extends AbstractProcessor {
	Types types;
	Elements elements;
	Messager messager;
	Filer filer;
	Package pack;

	public PluginAnnotationReader(Package pack) {
		this.pack = pack;
	}

	public void init(ProcessingEnvironment processingEnv) {
		super.init(processingEnv);
		this.types = processingEnv.getTypeUtils();
		this.elements = processingEnv.getElementUtils();
		this.messager = processingEnv.getMessager();
		this.filer = processingEnv.getFiler();
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		for (TypeElement annotation : annotations) {
			for (Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
				messager.printMessage(Diagnostic.Kind.NOTE, element.getSimpleName());
			}
		}
		return false;
	}

}
