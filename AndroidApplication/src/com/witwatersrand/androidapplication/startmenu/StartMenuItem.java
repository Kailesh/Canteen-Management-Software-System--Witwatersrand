/**
 * 
 */
package com.witwatersrand.androidapplication.startmenu;

/**
 * @author Kailesh
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
	 * @return the _optionName
	 */
	public String getOptionName() {
		return _optionName;
	}

	/**
	 * @param _optionName the _optionName to set
	 */
	public void setOptionName(String optionName) {
		_optionName = optionName;
	}

	/**
	 * @return the _imageName
	 */
	public String getImageName() {
		return _imageName;
	}

	/**
	 * @param _imageName the _imageName to set
	 */
	public void setImageName(String _imageName) {
		this._imageName = _imageName;
	}

}
