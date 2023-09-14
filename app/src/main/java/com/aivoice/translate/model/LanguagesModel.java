package com.aivoice.translate.model;

public class LanguagesModel {
    String LanguageName;

    String LanguageCode;

    public LanguagesModel(String languageName, String languageCode) {
        LanguageName = languageName;
        LanguageCode = languageCode;
    }

    public String getLanguageName() {
        return LanguageName;
    }

    public String getLanguageCode() {
        return LanguageCode;
    }
}
