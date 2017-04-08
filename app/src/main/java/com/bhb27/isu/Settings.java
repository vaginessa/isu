/*
 * Copyright (C) Felipe de Leon <fglfgl27@gmail.com>
 *
 * This file is part of iSu.
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
package com.bhb27.isu;

import android.os.Bundle;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.preference.ListPreference;

import com.bhb27.isu.preferencefragment.PreferenceFragment;
import com.bhb27.isu.tools.Constants;
import com.bhb27.isu.tools.RootUtils;
import com.bhb27.isu.tools.Tools;

public class Settings extends PreferenceFragment {

    private String suVersion;
    private boolean isCMSU;
    private PreferenceScreen mPreferenceScreen;
    private PreferenceCategory mSettingsSU, mSettings, mSettingsNotifications, mSettingsSelinux, mSettingsDebug;
    private ListPreference mApplySuDelay;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceManager().setSharedPreferencesName(Constants.PREF_NAME);
        addPreferencesFromResource(R.xml.settings);
        getActivity().setTheme(R.style.Switch_theme);

        mSettingsSU = (PreferenceCategory) getPreferenceManager().findPreference("su_settings_pref");
        mPreferenceScreen = (PreferenceScreen) getPreferenceManager().findPreference("settings_preferencescreen");
        mSettings = (PreferenceCategory) getPreferenceManager().findPreference("settings_pref_view");
        mSettingsNotifications = (PreferenceCategory) getPreferenceManager().findPreference("notifications_settings_pref");
        mSettingsSelinux = (PreferenceCategory) getPreferenceManager().findPreference("selinux_settings_pref");
        mSettingsDebug = (PreferenceCategory) getPreferenceManager().findPreference("anddebug_settings_pref");

        mApplySuDelay = (ListPreference) getPreferenceManager().findPreference("apply_su_delay");

        CharSequence[] entries = new CharSequence[6];
        CharSequence[] entryValues = new CharSequence[6];
        for (int i = 0; i < 6; i++) {
            entries[i] = (String.format(getString(R.string.apply_su_delay_summary), ((i+1)*10)));
            entryValues[i] = String.valueOf((i+1)*10000);
        }
        mApplySuDelay.setEntries(entries);
        mApplySuDelay.setEntryValues(entryValues);

        suVersion = Tools.SuVersion(getActivity());
        isCMSU = Tools.SuVersionBool(suVersion);

        if (!isCMSU) {
            mSettingsSU.setEnabled(false);
            mSettingsNotifications.setEnabled(false);
            mSettingsSelinux.setEnabled(false);
            mSettingsDebug.setEnabled(false);
        } else
            mPreferenceScreen.removePreference(mSettings);
    }

    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}