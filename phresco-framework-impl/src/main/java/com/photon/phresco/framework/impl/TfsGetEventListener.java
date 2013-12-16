/*
 * Copyright (c) Microsoft Corporation. All rights reserved. This code released
 * under the terms of the Microsoft Public License (MS-PL,
 * http://opensource.org/licenses/ms-pl.html.)
 */

package com.photon.phresco.framework.impl;

import com.microsoft.tfs.core.clients.versioncontrol.events.GetEvent;
import com.microsoft.tfs.core.clients.versioncontrol.events.GetListener;

public class TfsGetEventListener
    implements GetListener
{
    public void onGet(final GetEvent e)
    {
        String item = e.getTargetLocalItem() != null ? e.getTargetLocalItem() : e.getServerItem();

        //System.out.println("getting: " + item);
    }
}
