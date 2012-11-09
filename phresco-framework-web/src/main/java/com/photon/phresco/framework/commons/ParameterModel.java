package com.photon.phresco.framework.commons;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ParameterModel {
	private String lableText = "";
	private String lableClass = "";
	private String id = "";
	private String cssClass = "";
	private String name = "";
	private String placeHolder = "";
	private String value = "";
	private String inputType = "";
	private String onClickFunction = "";
	private String onChangeFunction = "";
	private String controlGroupId = "";
	private String controlId = "";
	private List<? extends Object> objectValue = null;
	private List<String> selectedValues = null;
	private boolean mandatory;
	private boolean multiple;
	private boolean show;
	private String dependency = "";
	
    public String getLableText() {
		return lableText;
	}
	
	public void setLableText(String lableText) {
		this.lableText = lableText;
	}
	
	public String getLableClass() {
		return lableClass;
	}
	
	public void setLableClass(String lableClass) {
		this.lableClass = lableClass;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getCssClass() {
		return cssClass;
	}
	
	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPlaceHolder() {
		return placeHolder;
	}
	
	public void setPlaceHolder(String placeHolder) {
		this.placeHolder = placeHolder;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getInputType() {
		return inputType;
	}
	
	public void setInputType(String inputType) {
		this.inputType = inputType;
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
	
	public void setControlId(String controlId) {
		this.controlId = controlId;
	}
	
	public String getControlId() {
		return controlId;
	}
	
	public void setControlGroupId(String controlGroupId) {
		this.controlGroupId = controlGroupId;
	}
	
	public String getControlGroupId() {
		return controlGroupId;
	}
	
	public void setObjectValue(List<? extends Object> objectValue) {
		this.objectValue = objectValue;
	}
	
	public List<? extends Object> getObjectValue() {
		return objectValue;
	}
	
	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}
	
	public boolean isMandatory() {
		return mandatory;
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
    
    public String toString() {
        return new ToStringBuilder(this,
                ToStringStyle.DEFAULT_STYLE)
                .append("lableText", getLableText())
                .append("lableClass", getLableClass())
                .append("id", getId())
                .append("cssClass", getCssClass())
                .append("name", getName())
                .append("placeHolder", getPlaceHolder())
                .append("value", getValue())
                .append("inputType", getInputType())
                .append("onClickFunction", getOnClickFunction())
                .append("onChangeFunction", getOnChangeFunction())
                .append("controlGroupId", getControlGroupId())
                .append("controlId", getControlId())
                .append("objectValue", getObjectValue())
                .append("selectedValues", getSelectedValues())
                .append("mandatory", isMandatory())
                .append("multiple", isMultiple())
                .append("show", isShow())
                .append("dependency", getDependency())
                .toString();
    }
}