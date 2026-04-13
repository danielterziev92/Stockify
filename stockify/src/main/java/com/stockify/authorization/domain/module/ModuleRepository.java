package com.stockify.authorization.domain.module;

import org.jmolecules.ddd.types.Repository;

import java.util.Optional;

/**
 * Repository port for the {@link Module} aggregate root.
 *
 * <p>Defines the persistence contract for {@code Module} aggregates. Implementations
 * are provided by the infrastructure layer; the domain layer depends only on this
 * interface, keeping the domain free of persistence concerns.
 */
public interface ModuleRepository extends Repository<Module, ModuleId> {

    /**
     * Looks up a module by its unique identifier.
     *
     * @param id the module identifier to search for; must not be {@code null}
     * @return an {@link Optional} containing the matching module, or empty if not found
     */
    Optional<Module> findById(ModuleId id);

    /**
     * Looks up a module by its normalized name.
     *
     * @param name the normalized (trimmed, lowercase) name to search for; must not be {@code null}
     * @return an {@link Optional} containing the matching module, or empty if not found
     */
    Optional<Module> findByName(String name);

    /**
     * Checks whether a {@link ModuleResource} with the given identifier exists.
     *
     * @param resourceId the resource identifier to check; must not be {@code null}
     * @return {@code true} if a resource with that identifier exists, {@code false} otherwise
     */
    boolean resourceExists(ModuleResourceId resourceId);

    /**
     * Persists a new or updated {@link Module} aggregate.
     *
     * @param module the module to save; must not be {@code null}
     */
    void save(Module module);
}
