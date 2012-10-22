package com.witwatersrand.androidapplication.startmenu;

/**
 * A class that defines a option item via a name and icon name
 * @author Kailesh Ramjee - University of Witwatersrand - School of Electrical & Information Engineering
 *
 */
public class StartMenuItem {
	private String _optionName;
	private String _imageName;
	
	StartMenuItem(String option, String imageName) {
		_optionName = option;
		_imageName = imageName;
	}

	/**
	 * Get the option name
	 * @return the option name
	 */
	public String getOptionName() {
		return _optionName;
	}

	/**
	 * Set the option name
	 * @param optionName the option name to set
	 */
	public void setOptionName(String optionName) {
		_optionName = optionName;
	}

	/**
	 * Get the image name
	 * @return the image name
	 */
	public String getImageName() {
		return _imageName;
	}

	/**
	 * Set the image name
	 * @param imageName the image name to set
	 */
	public void setImageName(String _imageName) {
		this._imageName = _imageName;
	}
}