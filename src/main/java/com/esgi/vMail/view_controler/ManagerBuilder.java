package com.esgi.vMail.view_controler;

import com.esgi.vMail.view.WindowBuilder;

public abstract class ManagerBuilder {
	protected WindowBuilder windowBuilder;
	/**
	 * @return the windowBuilder
	 */
	public WindowBuilder getWindowBuilder() {
		return windowBuilder;
	}

	/**
	 * @param windowBuilder the windowBuilder to set
	 */
	public void setWindowBuilder(WindowBuilder windowBuilder) {
		this.windowBuilder = windowBuilder;
	}
}
