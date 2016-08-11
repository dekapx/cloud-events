package com.cloudevents.preference.service;

import com.cloudevents.preference.model.UserPreference;

import java.util.Map;

public interface PreferenceService {
    void save(final UserPreference preference);

    void update(final UserPreference preference);

    void delete(String id);

    UserPreference find(final String id);

    Map<Object, UserPreference> findAll();

}
