package fr.sne.marketplace.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(fr.sne.marketplace.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(fr.sne.marketplace.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(fr.sne.marketplace.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(fr.sne.marketplace.domain.PersistentToken.class.getName(), jcacheConfiguration);
            cm.createCache(fr.sne.marketplace.domain.User.class.getName() + ".persistentTokens", jcacheConfiguration);
            cm.createCache(fr.sne.marketplace.domain.SocialUserConnection.class.getName(), jcacheConfiguration);
            cm.createCache(fr.sne.marketplace.domain.Operator.class.getName(), jcacheConfiguration);
            cm.createCache(fr.sne.marketplace.domain.Customer.class.getName(), jcacheConfiguration);
            cm.createCache(fr.sne.marketplace.domain.Customer.class.getName() + ".addresses", jcacheConfiguration);
            cm.createCache(fr.sne.marketplace.domain.Customer.class.getName() + ".orders", jcacheConfiguration);
            cm.createCache(fr.sne.marketplace.domain.CustomerAddress.class.getName(), jcacheConfiguration);
            cm.createCache(fr.sne.marketplace.domain.Region.class.getName(), jcacheConfiguration);
            cm.createCache(fr.sne.marketplace.domain.Country.class.getName(), jcacheConfiguration);
            cm.createCache(fr.sne.marketplace.domain.Market.class.getName(), jcacheConfiguration);
            cm.createCache(fr.sne.marketplace.domain.Market.class.getName() + ".infos", jcacheConfiguration);
            cm.createCache(fr.sne.marketplace.domain.Market.class.getName() + ".categories", jcacheConfiguration);
            cm.createCache(fr.sne.marketplace.domain.ProductCategory.class.getName(), jcacheConfiguration);
            cm.createCache(fr.sne.marketplace.domain.MarketInfo.class.getName(), jcacheConfiguration);
            cm.createCache(fr.sne.marketplace.domain.MarketOrder.class.getName(), jcacheConfiguration);
            cm.createCache(fr.sne.marketplace.domain.MarketOrder.class.getName() + ".items", jcacheConfiguration);
            cm.createCache(fr.sne.marketplace.domain.MarketOrder.class.getName() + ".quantities", jcacheConfiguration);
            cm.createCache(fr.sne.marketplace.domain.Payment.class.getName(), jcacheConfiguration);
            cm.createCache(fr.sne.marketplace.domain.MarketOrderItems.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
