/*
 * Copyright (C) 2013 The ChameleonOS Open Source Project
 * Copyright (C) 2014 The Android Ice Cold Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.settings.aicp;

import android.provider.SearchIndexableResource;
import com.android.settings.search.BaseSearchIndexProvider;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.search.Indexable;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.util.Helpers;

import java.util.ArrayList;
import java.util.List;

/**
 * LAB files borrowed from excellent ChameleonOS for AICP
 */
public class AicpSettings extends SettingsPreferenceFragment
        implements OnSharedPreferenceChangeListener, Indexable {

    private static final String TAG = "AicpLabs";

    private static final String KEY_AICPOTA_START = "aicp_ota_start";
    private static final String KEY_KERNEL_AUDIUTOR_START = "kernel_adiutor_start";

    // Package name of the AICP OTA app
    public static final String AICPOTA_PACKAGE_NAME = "com.aicp.aicpota";
    // Intent for launching the AICP OTA main actvity
    public static Intent INTENT_AICPOTA = new Intent(Intent.ACTION_MAIN)
            .setClassName(AICPOTA_PACKAGE_NAME, AICPOTA_PACKAGE_NAME + ".MainActivity");

    // Package name of the Kernel Adiutor app
    public static final String KERNEL_AUDIUTOR_PACKAGE_NAME = "com.grarak.kerneladiutor";
    // Intent for launching the Kernel Adiutor main actvity
    public static Intent INTENT_KERNEL_AUDIUTOR = new Intent(Intent.ACTION_MAIN)
            .setClassName(KERNEL_AUDIUTOR_PACKAGE_NAME, KERNEL_AUDIUTOR_PACKAGE_NAME + ".MainActivity");

    private Preference mAicpOta;
    private Preference mKernelAdiutor;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        addPreferencesFromResource(R.xml.aicp_lab_prefs);

        PreferenceScreen prefSet = getPreferenceScreen();
        PackageManager pm = getPackageManager();

        mAicpOta = (Preference)
                prefSet.findPreference(KEY_AICPOTA_START);
        if (!Helpers.isPackageInstalled(AICPOTA_PACKAGE_NAME, pm)) {
            prefSet.removePreference(mAicpOta);
        }

        mKernelAdiutor = (Preference)
                prefSet.findPreference(KEY_KERNEL_AUDIUTOR_START);
        if (!Helpers.isPackageInstalled(KERNEL_AUDIUTOR_PACKAGE_NAME, pm)) {
            prefSet.removePreference(mKernelAdiutor);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences preferences, String key) {
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        if (preference == mAicpOta) {
            startActivity(INTENT_AICPOTA);
            return true;
        } else if (preference == mKernelAdiutor) {
            startActivity(INTENT_KERNEL_AUDIUTOR);
            return true;
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

    public static final Indexable.SearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
        new BaseSearchIndexProvider() {
        @Override
        public List<SearchIndexableResource> getXmlResourcesToIndex(Context context,
                                                                    boolean enabled) {
            ArrayList<SearchIndexableResource> result =
                new ArrayList<SearchIndexableResource>();

            SearchIndexableResource sir = new SearchIndexableResource(context);
            sir.xmlResId = R.xml.aicp_lab_prefs;
            result.add(sir);

            return result;
        }

        @Override
        public List<String> getNonIndexableKeys(Context context) {
            ArrayList<String> result = new ArrayList<String>();
            return result;
        }
    };
}

