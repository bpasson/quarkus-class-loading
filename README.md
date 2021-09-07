# Quarkus Class Loader Issues

This project demonstrates classloading differences when running in production or dev/test mode. It is supporting GitHub Issue #...

## Details
This application uses the Java Money API and the Moneta RI. A custom currency (XYZ) is installed using
the SPI mechanism Java Money exposes. See [META-INF/services/javax.money.spi.CurrencyProviderSpi](src/main/resources/META-INF/services/javax.money.spi.CurrencyProviderSpi)

It demonstrates a potentially unwanted difference between dev/test and production mode. When running the application in dev/test mode, the custom currency provider is never found by Java Money. When running in production mode, it works as expected. 

## Endpoints
The following endpoints can be uses to get classloader info. See [AmountResource](src/main/java/org/acme/AmountResource.java) the implementation.

### Test Amount
The following get will parse an amount string. In this case using the custom currency XYZ installed
using the CurrencyProviderSpi.
```
GET /parse-value?v=1.00%20XYZ
```

### List Currency Providers
There are two calls for listing the available CurrencyProvider instances. One which lists the currency providers discovered by Java Money using the Moneta Bootstrap class. 

#### List with Default Moneta discovery.
The following endpoint lists all discovered currency providers that are discoverable using
the Moneta Bootstrap class.
```
GET /providers-default
```

#### List with ServiceLoader and context class loader.
The following endpoint lists all discovered currency providers using the `ServiceLoader.load(serviceClass)` method. This method uses the `Thread.currentThread().getContextClassLoader()` under the hood.
```
GET /providers-context-class-loader
```

#### List all classloaders in hierarchy
The following endpoint lists all classloaders from the `Thread.currentThread().getContextClassLoader()` all the way up to the highest parent.
```
GET /classloaders
```