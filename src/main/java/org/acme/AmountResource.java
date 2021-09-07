package org.acme;

import org.javamoney.moneta.Money;
import org.javamoney.moneta.spi.PriorityAwareServiceProvider;
import org.javamoney.moneta.spi.loader.DefaultLoaderService;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.spi.Bootstrap;
import javax.money.spi.CurrencyProviderSpi;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

@Path("/")
public class AmountResource {

    @Path("/parse-value")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String,Object> parseValue(@QueryParam("v") String v) {
        try {
            MonetaryAmount amount = Money.parse(v);
            return Map.of("value", amount.getNumber().numberValue(BigDecimal.class), "currency",amount.getCurrency().getCurrencyCode());
        } catch( Exception e ) {
            return Map.of("error", e.getMessage());
        }
    }

    @GET
    @Path("/providers-default")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> providersDefault() {
        return Bootstrap.getServices(CurrencyProviderSpi.class).stream().map( s -> s.getClass().getName()).collect(Collectors.toList());
    }

    @GET
    @Path("/providers-context-class-loader")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> providersWithContextClassLoader() {
        return ServiceLoader.load(CurrencyProviderSpi.class).stream()
                .map( p -> p.get().getClass().getName() + " from " + p.get().getClass().getClassLoader().getClass().getName()).collect(Collectors.toList());
    }


    @GET
    @Path("/classloaders")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> classloaders() {
//        return Bootstrap.getServices(CurrencyProviderSpi.class).stream().map( s -> s.getClass().getName()).collect(Collectors.toList());
//        return ServiceLoader.load(CurrencyProviderSpi.class).stream()
//                .map( p -> p.get().getClass().getName()).collect(Collectors.toList());
        return resolveClassloaders(Thread.currentThread().getContextClassLoader(),new ArrayList<>());

    }

    private List<String> resolveClassloaders(ClassLoader cl, List<String> classloaders) {
        classloaders.add(cl.getName() + " -> " + cl.getClass().getName());
        if( cl.getParent() != null ) {
            return resolveClassloaders(cl.getParent(),classloaders);
        }
        return classloaders;
    }
}