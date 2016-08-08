package com.cloudevents.preference.repository;

import com.cloudevents.preference.domain.UserPreference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Optional;

@Repository
public class PreferenceRepositoryImpl implements PreferenceRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(PreferenceRepositoryImpl.class);

    private static final String KEY = "UserPreference";
    private HashOperations hashOperations;
    private RedisTemplate<String, UserPreference> redisTemplate;

    @PostConstruct
    private void init() {
        hashOperations = redisTemplate.opsForHash();
    }

    @Autowired
    public PreferenceRepositoryImpl(RedisTemplate<String, UserPreference> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void save(final UserPreference preference) {
        hashOperations.put(KEY, String.valueOf(preference.getId()), preference);
        LOGGER.info("Preference persisted for ID {}", preference.getId());
    }

    @Override
    public void update(final UserPreference preference) {
        hashOperations.put(KEY, String.valueOf(preference.getId()), preference);
        LOGGER.info("Preference updated for ID {}", preference.getId());
    }

    @Override
    public void delete(final String id) {
        hashOperations.delete(KEY, id);
        LOGGER.info("Preference deleted for ID {}", id);
    }

    @Override
    public UserPreference find(final String id) {
        final Optional<UserPreference> optional = Optional.ofNullable((UserPreference) hashOperations.get(KEY, id));
        return optional.orElseThrow(PreferenceNotFoundException::new);
    }

    @Override
    public Map<Object, UserPreference> findAll() {
        LOGGER.info("--- Loading all the preferences ---");
        return hashOperations.entries(KEY);
    }
}
