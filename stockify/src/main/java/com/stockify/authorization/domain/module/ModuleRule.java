package com.stockify.authorization.domain.module;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Validation constants for the {@code Module} aggregate.
 *
 * <p>Organized into nested classes, one per field, each exposing the
 * constraint limits and the message-key strings used by Bean Validation
 * annotations (e.g. {@code @Size}, {@code @NotBlank}).
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ModuleRule {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Generic {
        public static final String NOT_FOUND_MSG = "module.not-found";
        public static final String RESOURCE_NOT_FOUND_MSG = "module.resource.not-found";
    }

    /**
     * Constraints for the module's {@code name} field.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Name {

        /**
         * Maximum allowed length for a module name.
         */
        public static final int MAX_LENGTH = 50;

        /**
         * Message key returned when the module name is blank.
         */
        public static final String BLANK_MSG = "module.name.blank";

        /**
         * Message key returned when the module name exceeds {@link #MAX_LENGTH} characters.
         */
        public static final String MAX_LENGTH_MSG = "module.name.max-length";
    }

    /**
     * Constraints for the module's {@code resources} collection field.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Resources {

        /**
         * Maximum number of resources that may be assigned to a single module.
         */
        public static final int MAX_LENGTH = 100;

        /**
         * Message key returned when the resource collection is blank/empty.
         */
        public static final String BLANK_MSG = "module.resources.blank";

        /**
         * Message key returned when the resource collection exceeds {@link #MAX_LENGTH} entries.
         */
        public static final String MAX_SIZE_MSG = "module.resources.max-size";

        /**
         * Message key returned when the resource collection contains duplicate entries.
         */
        public static final String DUPLICATE_MSG = "module.resources.duplicate";
    }
}
