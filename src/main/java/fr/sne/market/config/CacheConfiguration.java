package fr.sne.market.config;

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
            cm.createCache(fr.sne.market.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(fr.sne.market.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(fr.sne.market.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(fr.sne.market.domain.SocialUserConnection.class.getName(), jcacheConfiguration);
            cm.createCache(fr.sne.market.domain.Operator.class.getName(), jcacheConfiguration);
            cm.createCache(fr.sne.market.domain.Customer.class.getName(), jcacheConfiguration);
            cm.createCache(fr.sne.market.domain.Customer.class.getName() + ".addresses", jcacheConfiguration);
            cm.createCache(fr.sne.market.domain.Customer.class.getName() + ".orders", jcacheConfiguration);
            cm.createCache(fr.sne.market.domain.CustomerAddress.class.getName(), jcacheConfiguration);
            cm.createCache(fr.sne.market.domain.Region.class.getName(), jcacheConfiguration);
            cm.createCache(fr.sne.market.domain.Country.class.getName(), jcacheConfiguration);
            cm.createCache(fr.sne.market.domain.Market.class.getName(), jcacheConfiguration);
            cm.createCache(fr.sne.market.domain.Market.class.getName() + ".infos", jcacheConfiguration);
            cm.createCache(fr.sne.market.domain.Market.class.getName() + ".categories", jcacheConfiguration);
            cm.createCache(fr.sne.market.domain.ProductCategory.class.getName(), jcacheConfiguration);
            cm.createCache(fr.sne.market.domain.MarketInfo.class.getName(), jcacheConfiguration);
            cm.createCache(fr.sne.market.domain.MarketOrders.class.getName(), jcacheConfiguration);
            cm.createCache(fr.sne.market.domain.MarketOrders.class.getName() + ".quantities", jcacheConfiguration);
            cm.createCache(fr.sne.market.domain.Shipments.class.getName(), jcacheConfiguration);
            cm.createCache(fr.sne.market.domain.Invoices.class.getName(), jcacheConfiguration);
            cm.createCache(fr.sne.market.domain.Payment.class.getName(), jcacheConfiguration);
            cm.createCache(fr.sne.market.domain.MarketOrderline.class.getName(), jcacheConfiguration);
            cm.createCache(fr.sne.market.domain.MarketOrderline.class.getName() + ".details", jcacheConfiguration);
            cm.createCache(fr.sne.market.domain.MarketOrderItemsDetails.class.getName(), jcacheConfiguration);
            cm.createCache(fr.sne.market.domain.MarketProduct.class.getName(), jcacheConfiguration);
            cm.createCache(fr.sne.market.domain.MarketProduct.class.getName() + ".attributes", jcacheConfiguration);
            cm.createCache(fr.sne.market.domain.MarketProductCategory.class.getName(), jcacheConfiguration);
            cm.createCache(fr.sne.market.domain.MarketProductAttributes.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
