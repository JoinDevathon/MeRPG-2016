package org.devathon.contest2016;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;

/**
 * @author Jaxon A Brown
 */
public class VoxelUtil {

    /**
     * Based on http://drububu.com/miscellaneous/voxelizer/index.html
     */
    public static Voxel loadFromJSON(File jsonFile) {
        JSONObject voxelsJSON;
        try {
            JSONParser parser = new JSONParser();
            voxelsJSON = (JSONObject) parser.parse(new FileReader(jsonFile));
        } catch(Exception ex) {
            throw new RuntimeException(ex);
        }

        JSONObject dimensions = (JSONObject) ((JSONArray) voxelsJSON.get("dimension")).get(0);
        int xSize = Integer.parseInt((String) dimensions.get("width")) + 1;
        int ySize = Integer.parseInt((String) dimensions.get("height")) + 1;
        int zSize = Integer.parseInt((String) dimensions.get("depth")) + 1;

        boolean[][][] voxels = new boolean[xSize][ySize][zSize];
        for(int x = 0; x < xSize; x++) {
            for(int y = 0; y < ySize; y++) {
                for(int z = 0; z < zSize; z++) {
                    voxels[x][y][z] = false;
                }
            }
        }

        for(Object jo : (JSONArray) voxelsJSON.get("voxels")) {
            JSONObject voxel = (JSONObject) jo;
            voxels[Integer.parseInt((String) voxel.get("x"))][Integer.parseInt((String) voxel.get("y"))][Integer.parseInt((String) voxel.get("z"))] = true;
        }

        return new Voxel(voxels);
    }

    public static void place(Voxel voxel, Location loc) {
        World world = loc.getWorld();
        int xO = loc.getBlockX();
        int yO = loc.getBlockY();
        int zO = loc.getBlockZ();

        for(int x = 0; x < voxel.getX(); x++) {
            for(int y = 0; y < voxel.getY(); y++) {
                for(int z = 0; z < voxel.getZ(); z++) {
                    if(voxel.getState(x, y, z)) {
                        world.getBlockAt(x + xO, y + yO, z + zO).setType(Material.WOOL);
                    }
                }
            }
        }
    }
}
