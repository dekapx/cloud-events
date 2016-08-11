package com.cloudevents.event.util;

import com.cloudevents.preference.model.UserPreference;

public interface PreferenceMediator {
    UserPreference findPreferenceByUserId(String userId);
}
