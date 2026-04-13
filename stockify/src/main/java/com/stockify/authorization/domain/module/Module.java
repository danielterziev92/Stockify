package com.stockify.authorization.domain.module;

import com.stockify.shared.exception.InvalidValueException;
import lombok.Getter;
import org.jmolecules.ddd.types.AggregateRoot;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Aggregate root representing a logical grouping of permission-protected resources.
 *
 * <p>A {@code Module} owns a collection of {@link ModuleResource} entities, each
 * representing a distinct resource (e.g., an API endpoint or UI action) that the
 * module exposes. Resource names are deduplicated and stored in normalized form
 * (trimmed, lowercase).
 *
 * <p>Instances must be created via {@link #create} (new modules) or
 * {@link #reconstitute} (rehydration from persistence). The private constructor
 * ensures all invariants are enforced in one place.
 */
@Getter
public class Module implements AggregateRoot<Module, ModuleId> {

    /**
     * Unique identifier for this module.
     */
    private final ModuleId id;

    /**
     * Normalized (trimmed, lowercase) name of the module.
     */
    private final String name;

    /**
     * Ordered list of resources belonging to this module.
     */
    private final List<ModuleResource> resources;

    /**
     * Shadow set of normalized resource names used for O(1) duplicate detection
     * in {@link #registerResource}. Not exposed to callers.
     */
    private final Set<String> resourceNames;

    private Module(
            @NonNull ModuleId id,
            @NonNull String name,
            @NonNull List<ModuleResource> resources
    ) {
        this.id = id;
        this.name = name;
        this.resources = new ArrayList<>(resources);
        this.resourceNames = resources.stream()
                .map(ModuleResource::getName)
                .collect(Collectors.toCollection(HashSet::new));
    }

    /**
     * Creates a new {@code Module}, enforcing domain invariants.
     *
     * <p>The {@code name} is normalized (trimmed and lowercased) before validation.
     * A fresh {@link ModuleId} is generated automatically and the resource list
     * starts empty.
     *
     * @param name the module name; must not be blank and must not exceed
     *             {@link ModuleRule.Name#MAX_LENGTH} characters after normalization
     * @return a new, valid {@code Module}
     * @throws com.stockify.shared.exception.InvalidValueException if {@code name} is blank
     *                                                             or exceeds the maximum allowed length
     */
    public static @NonNull Module create(@NonNull String name) {
        String normalized = name.trim().toLowerCase();

        if (normalized.isBlank()) throw new InvalidValueException(ModuleRule.Name.BLANK_MSG);
        if (normalized.length() > ModuleRule.Name.MAX_LENGTH)
            throw new InvalidValueException(ModuleRule.Name.MAX_LENGTH_MSG, name.length());

        return new Module(ModuleId.generate(), normalized, List.of());
    }

    /**
     * Reconstitutes a {@code Module} from its persisted state, bypassing domain
     * validation.
     *
     * <p>Intended exclusively for use by repository/persistence layer code that
     * rehydrates previously validated data. Do <em>not</em> use this method to
     * create new modules — use {@link #create} instead.
     *
     * @param id        the persisted module identifier; must not be {@code null}
     * @param name      the persisted (already normalized) module name; must not be {@code null}
     * @param resources the persisted resource list; must not be {@code null}
     * @return the reconstituted {@code Module}
     */
    public static @NonNull Module reconstitute(
            @NonNull ModuleId id,
            @NonNull String name,
            @NonNull List<ModuleResource> resources
    ) {
        return new Module(id, name, resources);
    }

    /**
     * Registers a new resource on this module if one with the same normalized name
     * does not already exist.
     *
     * <p>The {@code resourceName} is normalised via {@link ModuleResource#normalize}
     * before the duplicate check. If a resource with that name is already registered,
     * this method is a no-op.
     *
     * @param resourceName the name of the resource to register; must not be {@code null}
     * @throws com.stockify.shared.exception.InvalidValueException if the normalized name
     *                                                             is blank or exceeds {@link ModuleRule.Resources#MAX_SIZE} characters
     */
    public void registerResource(@NonNull String resourceName) {
        String normalized = ModuleResource.normalize(resourceName);
        if (resourceNames.contains(normalized)) return;

        ModuleResource resource = ModuleResource.create(this.id, normalized);
        resources.add(resource);
        resourceNames.add(normalized);
    }

    /**
     * Returns an unmodifiable snapshot of the module's resource list.
     *
     * @return an immutable copy of the resources; never {@code null}
     */
    public @NonNull List<ModuleResource> getResources() {
        return List.copyOf(resources);
    }
}
