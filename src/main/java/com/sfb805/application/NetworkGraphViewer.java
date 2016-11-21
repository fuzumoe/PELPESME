package com.sfb805.application;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class NetworkGraphViewer extends Region {

	final WebView browser = new WebView();
	final WebEngine webEngine = browser.getEngine();
	private String viewAddress;

	/*
	 * constructor
	 */
	public NetworkGraphViewer(String viewAddress) {
		this.viewAddress = viewAddress;
		this.mainView();
	}

	public void mainView() {
		webEngine.load(this.getViewAddress());

		// http://vowl.visualdataweb.org;

		getChildren().add(browser);
	}

	/**
	 * 
	 * @param viewAddress
	 */
	public void setViewAddress(String viewAddress) {
		this.viewAddress = viewAddress;
	}

	/**
	 * 
	 * @return
	 */
	public String getViewAddress() {
		return viewAddress;
	}

	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("unused")
	private Node createSpacer() {
		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);
		return spacer;
	}

	/**
	 * this class will style the internal nodes of the GUI
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.scene.Parent#layoutChildren()
	 */
	@Override
	protected void layoutChildren() {
		double w = getWidth();
		double h = getHeight();
		layoutInArea(browser, 0, 0, w, h, 0, HPos.CENTER, VPos.CENTER);
	}

	/**
	 * This class will compute the preferable size "Width" of the browser
	 * windows based on the screen size
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.scene.layout.Region#computePrefWidth(double)
	 */
	@Override
	protected double computePrefWidth(double height) {
		return 750;
	}

	/**
	 * This class will compute the preferable size "Height" of the browser
	 * windows based on the screen size
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.scene.layout.Region#computePrefHeight(double)
	 */
	@Override
	protected double computePrefHeight(double width) {
		return 500;
	}
}