package com.cloudevents.preference.repository;

import com.cloudevents.preference.domain.UserPreference;

import java.util.Map;

public interface PreferenceRepository {
    void save(UserPreference preference);

    void update(UserPreference preference);

    void delete(String id);

    UserPreference find(final String id);

    Map<Object, UserPreference> findAll();
}
