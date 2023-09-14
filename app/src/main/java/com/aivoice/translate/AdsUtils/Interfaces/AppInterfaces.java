package com.aivoice.translate.AdsUtils.Interfaces;

import com.aivoice.translate.AdsUtils.FirebaseADHandlers.AdsJsonPOJO;

public class AppInterfaces {
    public interface AdDataInterface {
        void getAdData(AdsJsonPOJO adsJsonPOJO);
    }

    public interface InterStitialADInterface {
        void adLoadState(boolean isLoaded);
    }
    public interface AppOpenADInterface {
        void appOpenAdState(boolean state_load);
    }
}
