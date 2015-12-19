package org.ababup1192;

import org.ababup1192.entity.Convert;
import org.ababup1192.entity.Target;
import org.junit.Test;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MappingTest {

    @Test
    public void testConvert() throws Exception {
        Target target = new Target(123, "abc");

        Optional<Convert> convertOpt = Mapping.convert(target, Convert.class);

        convertOpt.ifPresent(convert ->
                assertThat(convert, is(new Convert(123, "abc")))
        );

        convertOpt.orElseThrow(RuntimeException::new);
    }

    @Test
    public void testConvertClasses() throws Exception {
        Mapping mapping = new Mapping(Collections.singletonList("org.ababup1192.entity"));
        Optional<?> convertOpt =  mapping.convertFromPackageObjects(new Target(123, "abc"));
        convertOpt.ifPresent(convert ->
                assertThat(convert, is(new Convert(123, "abc")))
        );

        convertOpt.orElseThrow(RuntimeException::new);
    }
}
