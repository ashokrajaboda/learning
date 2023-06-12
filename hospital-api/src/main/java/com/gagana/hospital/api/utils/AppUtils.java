package com.gagana.hospital.api.utils;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

public class AppUtils {
    public static <T> Optional<T> resolve(Supplier<T> resolver, T defaultValue) {
        try {
            T result = resolver.get();
            if(Objects.isNull(result)) {
                return Optional.ofNullable(defaultValue);
            }
            return Optional.of(result);
        }
        catch (NullPointerException e) {
            return Optional.ofNullable(defaultValue);
        }
    }
}
