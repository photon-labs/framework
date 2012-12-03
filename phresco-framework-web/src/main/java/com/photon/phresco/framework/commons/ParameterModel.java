package com.photon.phresco.framework.commons;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ParameterModel extends BasicParameterModel {
	
	private String value = "";
	private String onClickFunction = "";
	private String onChangeFunction = "";
	private String optionOnclickFunction = "";
	private List<String> selectedValues = null;
	
	private boolean multiple;
	private boolean show;
	private String dependency = "";
	private String fileType = "";
	private List<BasicParameterModel> childs = null;
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getOnClickFunction() {
		return onClickFunction;
	}
	
	public void setOnClickFunction(String onClickFunction) {
		this.onClickFunction = onClickFunction;
	}
	
	public String getOnChangeFunction() {
		return onChangeFunction;
	}
	
	public void setOnChangeFunction(String onChangeFunction) {
		this.onChangeFunction = onChangeFunction;
	}
	
	public List<String> getSelectedValues() {
		return selectedValues;
	}
	
	public void setSelectedValues(List<String> selectedValues) {
		this.selectedValues = selectedValues;
	}
	
	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}
	
	public boolean isMultiple() {
		return multiple;
	}
	
	public void setShow(boolean show) {
		this.show = show;
	}
	
	public boolean isShow() {
		return show;
	}
	
	public String getDependency() {
        return dependency;
    }

    public void setDependency(String dependency) {
        this.dependency = dependency;
    }
    
    public List<BasicParameterModel> getChilds() {
        return childs;
    }

    public void setChilds(List<BasicParameterModel> childs) {
        this.childs = childs;
    }

    public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public void setOptionOnclickFunction(String optionOnclickFunction) {
		this.optionOnclickFunction = optionOnclickFunction;
	}

	public String getOptionOnclickFunction() {
		return optionOnclickFunction;
	}

	public String toString() {
        return new ToStringBuilder(this,
                ToStringStyle.DEFAULT_STYLE)
                .append(super.toString())
                .append("value", getValue())
                .append("onClickFunction", getOnClickFunction())
                .append("onChangeFunction", getOnChangeFunction())
                .append("selectedValues", getSelectedValues())
                .append("multiple", isMultiple())
                .append("show", isShow())
                .append("dependency", getDependency())
                .toString();
    }
}