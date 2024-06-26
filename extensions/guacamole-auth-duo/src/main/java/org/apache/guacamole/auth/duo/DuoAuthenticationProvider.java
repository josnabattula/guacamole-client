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

package org.apache.guacamole.auth.duo;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.guacamole.GuacamoleException;
import org.apache.guacamole.net.auth.AbstractAuthenticationProvider;
import org.apache.guacamole.net.auth.AuthenticatedUser;
import org.apache.guacamole.net.auth.UserContext;

/**
 * AuthenticationProvider implementation which uses Duo as an additional
 * authentication factor for users which have already been authenticated by
 * some other AuthenticationProvider.
 */
public class DuoAuthenticationProvider extends AbstractAuthenticationProvider {

    /**
     * The unique identifier for this authentication provider. This is used in
     * various parts of the Guacamole client to distinguish this provider from
     * others, particularly when multiple authentication providers are used.
     */
    public static String PROVIDER_IDENTIFER = "duo";

    /**
     * Injector which will manage the object graph of this authentication
     * provider.
     */
    private final Injector injector;

    /**
     * Creates a new DuoAuthenticationProvider that verifies users
     * using the Duo authentication service
     *
     * @throws GuacamoleException
     *     If a required property is missing, or an error occurs while parsing
     *     a property.
     */
    public DuoAuthenticationProvider() throws GuacamoleException {

        // Set up Guice injector.
        injector = Guice.createInjector(
            new DuoAuthenticationProviderModule(this)
        );

    }

    @Override
    public String getIdentifier() {
        return PROVIDER_IDENTIFER;
    }

    @Override
    public UserContext getUserContext(AuthenticatedUser authenticatedUser)
            throws GuacamoleException {

        UserVerificationService verificationService =
                injector.getInstance(UserVerificationService.class);

        // Verify user against Duo service
        verificationService.verifyAuthenticatedUser(authenticatedUser);

        // User has been verified, and authentication should be allowed to
        // continue
        return null;

    }

}
