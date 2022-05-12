package io.quarkiverse.openapi.generator.deployment.codegen;

import static io.quarkiverse.openapi.generator.deployment.CodegenConfig.ADDITIONAL_PROPERTY_NAME;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.eclipse.microprofile.config.Config;

/**
 * Extracts the AdditionalProperties properties from a given {@link Config} reference.
 * These properties are then injected in the OpenAPI generator to tweak the code generation properties.
 */
public final class AdditionalPropertiesConfigParser {

    public static Map<String, String> parse(final Config config) {
        final List<String> additionalPropertiesNames = filterAdditionalProperties(config.getPropertyNames());
        final Map<String, String> additionalProperties = new HashMap<>();
        additionalPropertiesNames.forEach(
                name -> additionalProperties.put(getAdditionalPropertyName(name), config.getValue(name, String.class)));
        return additionalProperties;
    }

    private static String getAdditionalPropertyName(final String fullPropertyName) {
        return fullPropertyName.replaceFirst(ADDITIONAL_PROPERTY_NAME, "");
    }

    private static List<String> filterAdditionalProperties(final Iterable<String> propertyNames) {
        return StreamSupport.stream(propertyNames.spliterator(), false)
                .filter(propertyName -> propertyName.startsWith(ADDITIONAL_PROPERTY_NAME))
                .collect(Collectors.toList());
    }
}
