/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sfb805.application;

import javax.swing.ImageIcon;

/**
 *
 * @author adam
 */
public class GUISwingVariables {
	// getClass().getResource("/com/sfb805/view/icons/forwardOneSmall.png")

	private ImageIcon forwaredSmall;
	private ImageIcon forwardBig;
	private ImageIcon backwardSmall;
	private ImageIcon backwardBig;
	private Boolean isDisabledLabelOne;
	private Boolean isDisabledLabelTwo;
	private Boolean isDisabledLabelThree;
	private Boolean isDisabledLabelFour;
	private Boolean isDisabledLabelFive;

	public GUISwingVariables() {
		this.setForwardBig(new ImageIcon(getClass().getResource("/forwardOneBig.png")));
		this.setForwaredSmall(new ImageIcon(getClass().getResource("/forwardOneSmall.png")));
		this.setBackwardBig(new ImageIcon(getClass().getResource("/backwardOneBig.png")));
		this.setBackwardSmall(new ImageIcon(getClass().getResource("/backwardOneSmall.png")));
		this.setIsDisabledLabelOne(true);
		this.setIsDisabledLabelTwo(true);
		this.setIsDisabledLabelThree(true);
		this.setIsDisabledLabelFour(true);
		this.setIsDisabledLabelFive(true);

	}

	public void setBackwardBig(ImageIcon backwardBig) {
		this.backwardBig = backwardBig;
	}

	public void setBackwardSmall(ImageIcon backwardSmall) {
		this.backwardSmall = backwardSmall;
	}

	public void setForwardBig(ImageIcon forwardBig) {
		this.forwardBig = forwardBig;
	}

	public void setForwaredSmall(ImageIcon forwaredSmall) {
		this.forwaredSmall = forwaredSmall;
	}

	public void setIsDisabledLabelFive(Boolean isDisabledLabelFive) {
		this.isDisabledLabelFive = isDisabledLabelFive;
	}

	public void setIsDisabledLabelFour(Boolean isDisabledLabelFour) {
		this.isDisabledLabelFour = isDisabledLabelFour;
	}

	public void setIsDisabledLabelOne(Boolean isDisabledLabelOne) {
		this.isDisabledLabelOne = isDisabledLabelOne;
	}

	public void setIsDisabledLabelThree(Boolean isDisabledLabelThree) {
		this.isDisabledLabelThree = isDisabledLabelThree;
	}

	public void setIsDisabledLabelTwo(Boolean isDisabledLabelTwo) {
		this.isDisabledLabelTwo = isDisabledLabelTwo;
	}

	public ImageIcon getForwardBig() {
		return forwardBig;
	}

	public ImageIcon getForwaredSmall() {
		return forwaredSmall;
	}

	public ImageIcon getBackwardBig() {
		return backwardBig;
	}

	public ImageIcon getBackwardSmall() {
		return backwardSmall;
	}

	public Boolean getIsDisabledLabelFive() {
		return isDisabledLabelFive;
	}

	public Boolean getIsDisabledLabelFour() {
		return isDisabledLabelFour;
	}

	public Boolean getIsDisabledLabelOne() {
		return isDisabledLabelOne;
	}

	public Boolean getIsDisabledLabelThree() {
		return isDisabledLabelThree;
	}

	public Boolean getIsDisabledLabelTwo() {
		return isDisabledLabelTwo;
	}

}
