/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.guacamole.properties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.guacamole.GuacamoleException;
import org.apache.guacamole.GuacamoleServerException;

/**
 * A GuacamoleProperty whose value is an long.
 */
public abstract class LongGuacamoleProperty implements GuacamoleProperty<Long> {

    @Override
    public Long parseValue(String value) throws GuacamoleException {

        // If no property provided, return null.
        if (value == null)
            return null;

        try {
            return Long.valueOf(value);
        }
        catch (NumberFormatException e) {
            throw new GuacamoleServerException("Property \"" + getName() + "\" must be an long.", e);
        }

    }
    
    @Override
    public List<Long> parseValueCollection(String value) throws GuacamoleException {
        
        if (value == null)
            return null;
        
        // Split string into a list of individual values
        List<String> stringValues = Arrays.asList(DELIMITER_PATTERN.split(value));
        if (stringValues.isEmpty())
            return null;
        
        // Translate values to Longs, validating along the way.
        List<Long> longValues = new ArrayList<>();
        for (String stringLong : stringValues) {
            longValues.add(parseValue(stringLong));
        }

        return longValues;
        
    }

}
