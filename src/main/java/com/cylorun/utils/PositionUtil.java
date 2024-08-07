package com.cylorun.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class PositionUtil {
    private static List<Vec2i> strongholdPositions;

    public static Vec2i.Dimension getDimension(String f3c) {
        if (!isF3c(f3c)) {
            return Vec2i.Dimension.UNKNOWN;
        }

        if (f3c.contains("minecraft:overworld")) {
            return Vec2i.Dimension.OVERWORLD;
        }

        if (f3c.contains("minecraft:the_nether")) {
            return Vec2i.Dimension.NETHER;
        }

        if (f3c.contains("minecraft:the_end")) {
            return Vec2i.Dimension.END;
        }

        return Vec2i.Dimension.UNKNOWN;
    }

    public static Vec2i getLocation(String f3c) { // x, z, yaw
        Vec2i locations = new Vec2i();

        if (!isF3c(f3c)) {
            return Vec2i.ZERO;
        }

        String[] f3info = f3c.replaceAll("\\D+ ", "").split("\\s+");
        try {
            locations.x = ((int) Double.parseDouble(f3info[0]));
            locations.z = ((int) Double.parseDouble(f3info[2]));
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format in f3c");
            return Vec2i.ZERO;
        }

        return locations;
    }

    public static boolean isF3c(String f3c) {
        return f3c.contains("/execute in minecraft"); // shoulod prolly use a regex here
    }

    public static List<Vec2i> getStrongholdPositions() {
        JsonObject coordsData = ResourceUtil.loadJsonResource(PositionUtil.class.getResource("/coords.json"));
        JsonElement owElement = coordsData.get("ow_coords");

        if (owElement == null) {
            return List.of();
        }

        JsonArray owCoords = owElement.getAsJsonArray();

        List<Vec2i> res = new ArrayList<>();
        for (JsonElement e : owCoords) {
            String data = e.getAsString();
            Integer[] split = Arrays.stream(data.split(",")).map(Integer::parseInt).toArray(Integer[]::new);
            res.add(new Vec2i(split[0], split[1]));
        }

        return res;
    }

    public static List<Vec2i> getClosestStrongholds(Vec2i playerLocation, Vec2i.Dimension dim) {
        if (strongholdPositions == null) {
            strongholdPositions = getStrongholdPositions();
        }

        List<Vec2i> netherCoords = strongholdPositions.stream().map(e -> e.div(8)).toList();
        List<Pair<Integer, Vec2i>> distances = new ArrayList<>();

        for (Vec2i sh : dim == Vec2i.Dimension.NETHER ? netherCoords : strongholdPositions) {
            int dist = playerLocation.distanceTo(sh);
            distances.add(new Pair<>(dim == Vec2i.Dimension.NETHER ? dist * 8 : dist, dim == Vec2i.Dimension.NETHER ? sh.multiply(8) : sh));
        }

        distances.sort(Comparator.comparing(integerVec2iPair -> integerVec2iPair.a));

        return distances.stream().map(pair -> pair.b).toList().subList(0, 3);
    }

    public static String getDistanceHexColor(int distance, Vec2i.Dimension dim) {
        if (dim == Vec2i.Dimension.OVERWORLD) distance /= 8;

        if (distance <= 3) {
            return "#0BC531";
        }
        if (distance <= 10) {
            return "#0CD019";
        }
        if (distance <= 30) {
            return "#79D255";
        }
        if (distance <= 80) {
            return "#FFE455";
        }
        if (distance <= 150) {
            return "#DF6530";
        }
        return "#cf401f";
    }
}
