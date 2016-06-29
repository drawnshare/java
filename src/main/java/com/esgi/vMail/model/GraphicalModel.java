package com.esgi.vMail.model;

public interface GraphicalModel<T> {
	GraphicalModel<T> detach();
	T getObjectModel();
}
