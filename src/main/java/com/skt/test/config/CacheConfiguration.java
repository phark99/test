package com.skt.test.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.skt.test.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.skt.test.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.skt.test.domain.User.class.getName());
            createCache(cm, com.skt.test.domain.Authority.class.getName());
            createCache(cm, com.skt.test.domain.User.class.getName() + ".authorities");
            createCache(cm, com.skt.test.domain.UserToken.class.getName());
            createCache(cm, com.skt.test.domain.LoginHistory.class.getName());
            createCache(cm, com.skt.test.domain.UserGroup.class.getName());
            createCache(cm, com.skt.test.domain.UserGroup.class.getName() + ".users");
            createCache(cm, com.skt.test.domain.UserGroup.class.getName() + ".roles");
            createCache(cm, com.skt.test.domain.Api.class.getName());
            createCache(cm, com.skt.test.domain.Api.class.getName() + ".apiMetas");
            createCache(cm, com.skt.test.domain.Api.class.getName() + ".apiRequests");
            createCache(cm, com.skt.test.domain.ApiMeta.class.getName());
            createCache(cm, com.skt.test.domain.ApiRequest.class.getName());
            createCache(cm, com.skt.test.domain.ApiRequest.class.getName() + ".apis");
            createCache(cm, com.skt.test.domain.Resource.class.getName());
            createCache(cm, com.skt.test.domain.Resource.class.getName() + ".resourceMetas");
            createCache(cm, com.skt.test.domain.Resource.class.getName() + ".roles");
            createCache(cm, com.skt.test.domain.Resource.class.getName() + ".projectResources");
            createCache(cm, com.skt.test.domain.ResourceMeta.class.getName());
            createCache(cm, com.skt.test.domain.Project.class.getName());
            createCache(cm, com.skt.test.domain.Project.class.getName() + ".resources");
            createCache(cm, com.skt.test.domain.Project.class.getName() + ".projectHistories");
            createCache(cm, com.skt.test.domain.ProjectHistory.class.getName());
            createCache(cm, com.skt.test.domain.Role.class.getName());
            createCache(cm, com.skt.test.domain.Role.class.getName() + ".users");
            createCache(cm, com.skt.test.domain.Role.class.getName() + ".resources");
            createCache(cm, com.skt.test.domain.Role.class.getName() + ".menus");
            createCache(cm, com.skt.test.domain.Role.class.getName() + ".userGroups");
            createCache(cm, com.skt.test.domain.Menu.class.getName());
            createCache(cm, com.skt.test.domain.Menu.class.getName() + ".roles");
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
