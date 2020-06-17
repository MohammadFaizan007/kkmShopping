package com.digitalcashbag.common_activities;


import com.digitalcashbag.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class AppControllerApp extends www.paymonk.com.aepsredirection.AppController {


    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
    }
}
