package com.stockify.authorization.domain.module;

import com.stockify.shared.exception.InvalidValueException;
import lombok.Getter;
import org.jmolecules.ddd.types.Entity;
import org.jspecify.annotations.NonNull;

/**
 * A resource that belongs to a {@link Module} aggregate.
 *
 * <p>{@code ModuleResource} is a DDD {@link Entity} whose identity is scoped to
 * its parent {@code Module}. It represents a single permission-protected resource
 * (e.g., an API endpoint or UI action) that the module exposes.
 *
 * <p>Instances must be created via {@link #create} (new resources) or
 * {@link #reconstitute} (rehydration from persistence). The private constructor
 * ensures all invariants are enforced in one place.
 */
@Getter
public class ModuleResource implements Entity<Module, ModuleResourceId> {

    /**
     * Unique identifier for this resource.
     */
    private final ModuleResourceId id;

    /**
     * Identifier of the {@link Module} aggregate root this resource belongs to.
     */
    private final ModuleId moduleId;

    /**
     * Normalized (trimmed, lowercase) name of the resource.
     */
    private final String name;

    private ModuleResource(@NonNull ModuleResourceId id, @NonNull ModuleId moduleId, @NonNull String name) {
        this.id = id;
        this.moduleId = moduleId;
        this.name = name;
    }

    /**
     * Creates a new {@code ModuleResource}, enforcing domain invariants.
     *
     * <p>The {@code name} is {@link #normalize normalized} (trimmed and lowercased)
     * before validation. A fresh {@link ModuleResourceId} is generated automatically.
     *
     * @param moduleId the identifier of the owning module; must not be {@code null}
     * @param name     the resource name; must not be blank and must not exceed
     *                 {@link ModuleRule.Resources#MAX_SIZE} characters after normalization
     * @return a new, valid {@code ModuleResource}
     * @throws com.stockify.shared.exception.InvalidValueException if {@code name} is blank
     *                                                             or exceeds the maximum allowed length
     */
    public static @NonNull ModuleResource create(@NonNull ModuleId moduleId, @NonNull String name) {
        String normalized = normalize(name);

        if (normalized.isBlank()) throw new InvalidValueException(ModuleRule.Resources.BLANK_MSG);
        if (normalized.length() > ModuleRule.Resources.MAX_SIZE)
            throw new InvalidValueException(ModuleRule.Resources.MAX_SIZE_MSG, normalized.length());

        return new ModuleResource(ModuleResourceId.generate(), moduleId, normalized);
    }

    /**
     * Reconstitutes a {@code ModuleResource} from its persisted state, bypassing
     * domain validation.
     *
     * <p>Intended exclusively for use by repository/persistence layer code that
     * rehydrates previously validated data. Do <em>not</em> use this method to
     * create new resources — use {@link #create} instead.
     *
     * @param id       the persisted resource identifier; must not be {@code null}
     * @param moduleId the identifier of the owning module; must not be {@code null}
     * @param name     the persisted (already normalized) resource name; must not be {@code null}
     * @return the reconstituted {@code ModuleResource}
     */
    public static @NonNull ModuleResource reconstitute(
            @NonNull ModuleResourceId id,
            @NonNull ModuleId moduleId,
            @NonNull String name
    ) {
        return new ModuleResource(id, moduleId, name);
    }

    /**
     * Normalizes a resource name by trimming surrounding whitespace and converting
     * it to lowercase.
     *
     * @param name the raw name; must not be {@code null}
     * @return the normalized name
     */
    static @NonNull String normalize(@NonNull String name) {
        return name.trim().toLowerCase();
    }
}
