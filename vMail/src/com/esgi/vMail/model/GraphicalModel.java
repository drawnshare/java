package com.esgi.vMail.model;

public interface GraphicalModel<T> {
	public GraphicalModel<T> detach();
	public T getObjectModel();
}
