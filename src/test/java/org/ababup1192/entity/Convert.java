package org.ababup1192.entity;

import java.util.Objects;

public class Convert {
    public Integer x;
    public String y;

    public Convert(){}

    public Convert(Integer x, String y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Object target) {
        if (this == target) return true;
        else if (!(target instanceof Convert)) return false;
        else {
            Convert convert = (Convert) target;
            return Objects.equals(x, convert.x) && Objects.equals(y, convert.y);
        }
    }

    @Override
    public String toString() {
        return "Convert(" + x + "," + y + ")";
    }

}
