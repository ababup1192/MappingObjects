package org.ababup1192.entity;

import java.util.Objects;

public class Target {
    public final Integer x;
    public final String y;

    public Target(Integer x, String y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Object target) {
        if (this == target) return true;
        else if (!(target instanceof Target)) return false;
        else {
            Target t = (Target) target;
            return Objects.equals(x, t.x) && Objects.equals(y, t.y);
        }
    }

    @Override
    public String toString() {
        return "Target(" + x + "," + y + ")";
    }
}
