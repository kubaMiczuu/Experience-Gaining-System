package org.system.experiencegainingsystem.commons;

import java.util.Objects;
import java.util.UUID;

public class Id {
    private final UUID id;

    public Id() {
        this.id = UUID.randomUUID();
    }

    public Id(UUID id) {
        this.id = (id != null) ? id : UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Id other)) return false;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return id.toString();
    }
}