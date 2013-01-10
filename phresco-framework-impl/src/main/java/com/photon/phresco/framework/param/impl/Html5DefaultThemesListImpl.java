package com.photon.phresco.framework.param.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.photon.phresco.api.DynamicParameter;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues.Value;
import com.photon.phresco.util.Constants;

public class Html5DefaultThemesListImpl implements DynamicParameter, Constants {

    private static final long serialVersionUID = 1L;

    @Override
    public PossibleValues getValues(Map<String, Object> paramMap) throws PhrescoException {
        try {
            PossibleValues possibleValues = new PossibleValues();
            String csvParam = (String) paramMap.get("themes");
            List<String> selectedThemes = new ArrayList<String>();
            if (StringUtils.isNotEmpty(csvParam)) {
                selectedThemes = Arrays.asList(csvParam.split(","));
            }
            if (CollectionUtils.isNotEmpty(selectedThemes)) {
                for (String selectedTheme : selectedThemes) {
                    Value value = new Value();
                    value.setValue(selectedTheme);
                    possibleValues.getValue().add(value);
                }
            }
            return possibleValues;
        } catch (Exception e) {
            throw new PhrescoException(e);
        }
    }	
}