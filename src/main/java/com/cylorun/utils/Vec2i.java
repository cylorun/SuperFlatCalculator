package com.cylorun.utils;


public class Vec2i {

    public static final Vec2i ZERO = new Vec2i(0, 0);
    public Integer x;
    public Integer z;


    public Vec2i() {
    }

    public Vec2i(Integer x, Integer z) {
        this.x = x;
        this.z = z;
    }

    public Vec2i div(int d) {
        return new Vec2i(this.x / d, this.z / d);
    }
    public Vec2i multiply(int m) {
        return new Vec2i(this.x * m, this.z * m);
    }
    public boolean isEmpty() {
        return this.x == null && this.z == null || this.x == Integer.MAX_VALUE && this.z == Integer.MAX_VALUE;
    }

    public static Vec2i add(Vec2i a, Vec2i b) {
        return new Vec2i((a.x + a.z), (b.x + b.z));
    }

    public Integer distanceTo(Vec2i b) {
        return distanceTo(this, b);
    }

    public static Integer distanceTo(Vec2i a, Vec2i b) {
        return (int) Math.abs(Math.sqrt(Math.pow(b.x - a.x, 2) + Math.pow(b.z - a.z, 2)));
    }

    public Double angle(Vec2i b) {
        return angle(this, b);
    }
    public static Double angle(Vec2i a, Vec2i b) {
        double angleRadians = Math.atan2(a.z - b.z, a.x - b.x); // returns the angle between 2 points, in an MC angle format
        double angleDegrees = Math.toDegrees(angleRadians);
        angleDegrees -= 90;
        if (angleDegrees > 180) {
            angleDegrees -= 360;
        } else if (angleDegrees <= -180) {
            angleDegrees += 360;
        }

        return Math.round(angleDegrees * 10) / 10.0;
    }

    @Override
    public String toString() {
        return "Vec2i{" +
                "x=" + x +
                ", z=" + z +
                '}';
    }

    public enum Dimension {
        OVERWORLD("minecraft:overworld"),
        NETHER("minecraft:the_nether"),
        END("minecraft:the_end"),
        UNKNOWN("not minecraft");

        private final String label;

        private Dimension(String label) {
            this.label = label;
        }


        @Override
        public String toString() {
            return this.label;
        }
    }
}
