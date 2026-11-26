package org.system.experiencegainingsystem.commons;

import lombok.Getter;

import java.util.Objects;

@Getter
public class Experience{
    private final double experience;

    public Experience(double experience) {
        if (experience < 0) throw new IllegalArgumentException("Experience cannot be negative");
        this.experience = experience;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Experience that = (Experience) o;
        return Double.compare(experience, that.experience) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(experience);
    }

   public Experience multiply(double multiplier) {
        if(multiplier < 0) {
            throw new IllegalArgumentException("multiplier must be a positive number");
        }
        return new Experience(this.experience * multiplier);
   }

   public Experience add(Experience other) {
        if (other == null) {
            throw new IllegalArgumentException("Experience cannot be null");
        }
        return new Experience(this.experience + other.experience);
   }

    @Override
    public String toString() {
        return experience + "";
    }
}
