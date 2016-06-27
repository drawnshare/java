package com.esgi.ModuleProcessor.controler;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

@SupportedAnnotationTypes("com.esgi.vMail.plugins.parser.annotations.PluginsInfo")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class PluginAnnotationReader extends AbstractProcessor {
	Types types;
	Elements elements;
	Messager messager;
	Filer filer;
//	Package pack;
	public PluginAnnotationReader(/*Package pack*/) {
//		this.pack = pack;
	}
	@Override
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
				System.out.println(annotation.getSimpleName());
				messager.printMessage(Diagnostic.Kind.NOTE, element.getSimpleName());
			}
		}
		return false;
	}
}
