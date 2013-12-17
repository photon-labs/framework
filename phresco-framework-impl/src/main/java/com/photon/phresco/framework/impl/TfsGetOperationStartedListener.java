/*
 * Copyright (c) Microsoft Corporation. All rights reserved. This code released
 * under the terms of the Microsoft Public License (MS-PL,
 * http://opensource.org/licenses/ms-pl.html.)
 */

package com.photon.phresco.framework.impl;

import com.microsoft.tfs.core.clients.versioncontrol.events.GetOperationStartedEvent;
import com.microsoft.tfs.core.clients.versioncontrol.events.OperationStartedEvent;
import com.microsoft.tfs.core.clients.versioncontrol.events.OperationStartedListener;
import com.microsoft.tfs.core.clients.versioncontrol.soapextensions.GetRequest;

public class TfsGetOperationStartedListener
    implements OperationStartedListener
{
    public void onGetOperationStarted(final GetOperationStartedEvent e)
    {
        for (GetRequest request : e.getRequests())
        {
            if (request.getItemSpec() != null)
            {
                System.out.println("Started getting: " + request.getItemSpec().toString());
            }
        }
    }

    public void onOperationStarted(final OperationStartedEvent e)
    {
        if (e instanceof GetOperationStartedEvent)
        {
            onGetOperationStarted((GetOperationStartedEvent) e);
        }
    }
}
