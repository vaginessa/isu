/*
 * Copyright (C) 2016-2017 Felipe de Leon <fglfgl27@gmail.com>
 *
 * this file is part of iSu.
 *
 * iSu is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * iSu is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with iSu.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.bhb27.isu.services;

import android.app.Service;
import android.content.Context;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.content.Intent;

import com.bhb27.isu.R;
import com.bhb27.isu.tools.Constants;
import com.bhb27.isu.tools.Tools;

public class PropsService extends Service {

    private final int NOTIFY_ID = Constants.NOTIFY_ID_PROPS;
    private Context context;
    private boolean isCMSU;
    private static final String id = "iSu_PropsService";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            startForeground(NOTIFY_ID, Tools.OreoNotify(getString(R.string.props_apply_boot), id, context).build());

        init();
    }

    private void init() {
        String executableFilePath = getFilesDir().getPath() + "/";

        Log.d(id, " Applying props");
        Tools.applyprop(context, executableFilePath);
        Tools.applyDbProp(context, executableFilePath);

        if (Tools.SuVersionBool(Tools.SuVersion(context)) &&
            Tools.readString("ro.debuggable", null, context).equals("0"))
            Tools.stripadb(executableFilePath, context);

        Tools.closeSU();
        Log.d(id, " Run");
        stopSelf();
    }

}
