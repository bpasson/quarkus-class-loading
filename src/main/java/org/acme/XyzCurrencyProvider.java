package org.acme;

import org.javamoney.moneta.CurrencyUnitBuilder;

import javax.money.CurrencyQuery;
import javax.money.CurrencyUnit;
import javax.money.spi.CurrencyProviderSpi;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class XyzCurrencyProvider implements CurrencyProviderSpi {

    private Set<CurrencyUnit> upxSet = new HashSet<>();

    public XyzCurrencyProvider() {
        upxSet.add(CurrencyUnitBuilder.of("XYZ", "XyzCurrencyBuilder").build());
        upxSet = Collections.unmodifiableSet(upxSet);
    }

    /**
     * Return a {@link CurrencyUnit} instances matching the given
     * {@link javax.money.CurrencyQuery}.
     *
     * @param query the {@link javax.money.CurrencyQuery} containing the parameters determining the query. not null.
     * @return the corresponding {@link CurrencyUnit}s matching, never null.
     */
    @Override
    public Set<CurrencyUnit> getCurrencies(CurrencyQuery query) {
        // only ensure BTC is the code, or it is a default query.
        if (query.isEmpty()
                || query.getCurrencyCodes().contains("XYZ")
                || query.getCurrencyCodes().isEmpty()) {
            return upxSet;
        }
        return Collections.emptySet();
    }

}
