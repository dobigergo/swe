package util.guice;

import com.google.inject.AbstractModule;
import com.google.inject.persist.jpa.JpaPersistModule;
import util.guice.JpaInitializer;

public class PersistanceModule extends AbstractModule {

    private String jpaUnit;

    public PersistanceModule(String jpaUnit) {
        this.jpaUnit = jpaUnit;
    }

    @Override
    protected void configure() {
        install(new JpaPersistModule(jpaUnit));
        bind(JpaInitializer.class).asEagerSingleton();
    }


}
