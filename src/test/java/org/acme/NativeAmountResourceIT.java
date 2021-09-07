package org.acme;

import io.quarkus.test.junit.NativeImageTest;

@NativeImageTest
public class NativeAmountResourceIT extends AmountResourceTest {

    // Execute the same tests but in native mode.
}