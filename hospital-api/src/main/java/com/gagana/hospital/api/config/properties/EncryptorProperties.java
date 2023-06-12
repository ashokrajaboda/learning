package com.gagana.hospital.api.config.properties;

public record EncryptorProperties(String passphrase,
                                  String salt,
                                  String key,
                                  String iv) {
}
