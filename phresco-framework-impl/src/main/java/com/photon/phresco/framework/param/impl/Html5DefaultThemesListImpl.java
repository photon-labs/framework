/**
 * Phresco Framework Implementation
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