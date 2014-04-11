/**
 * Framework Web Archive
 *
 * Copyright (C) 1999-2014 Photon Infotech Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.photon.phresco.framework.commons;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class BasicParameterModel {
    
    private String inputType = "";
    private String lableText = "";
    private String lableClass = "";
    private String id = "";
    private String cssClass = "";
    private String name = "";
    private String placeHolder = "";
    private String controlGroupId = "";
    private String controlGroupClass = "";
    private String controlId = "";
    private boolean mandatory;
    private boolean showMinusIcon;
    private List<? extends Object> objectValue = null;
    
    public BasicParameterModel() {
    }
 
    public String getInputType() {
        return inputType;
    }
    
    public void setInputType(String inputType) {
        this.inputType = inputType;
    }
    
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
    
    public void setControlGroupClass(String controlGroupClass) {
		this.controlGroupClass = controlGroupClass;
	}

	public String getControlGroupClass() {
		return controlGroupClass;
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
    
    public boolean isShowMinusIcon() {
		return showMinusIcon;
	}

	public void setShowMinusIcon(boolean showMinusIcon) {
		this.showMinusIcon = showMinusIcon;
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
                .append("inputType", getInputType())
                .append("controlGroupId", getControlGroupId())
                .append("controlGroupClass", getControlGroupClass())
                .append("controlId", getControlId())
                .append("objectValue", getObjectValue())
                .append("mandatory", isMandatory())
                .toString();
    }
}