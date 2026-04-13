package com.stockify.authorization.domain.module;

import org.jmolecules.ddd.types.Repository;

import java.util.List;
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
     * Returns all persisted {@link Module} aggregates.
     *
     * @return a list of all modules; never {@code null}, may be empty
     */
    List<Module> findAll();

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
     * Returns all {@link ModuleResource} entities belonging to the given module.
     *
     * @param moduleId the identifier of the owning module; must not be {@code null}
     * @return a list of resources for that module; never {@code null}, may be empty
     */
    List<ModuleResource> findResourcesByModuleId(ModuleId moduleId);

    /**
     * Looks up a {@link ModuleResource} by its unique identifier.
     *
     * @param id the resource identifier to search for; must not be {@code null}
     * @return an {@link Optional} containing the matching resource, or empty if not found
     */
    Optional<ModuleResource> findResourceById(ModuleResourceId id);

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
