package org.devathon.contest2016;

import com.google.common.base.Preconditions;

/**
 * @author Jaxon A Brown
 */
public class Voxel {
    private final int x, y, z;

    private final boolean[][][] voxels;
    public Voxel(boolean[][][] voxels) {
        this.voxels = voxels;
        this.x = voxels.length;
        Preconditions.checkArgument(this.x > 0);
        this.y = voxels[0].length;
        Preconditions.checkArgument(this.y > 0);
        this.z = voxels[0][0].length;
        Preconditions.checkArgument(this.z > 0);
    }

    public boolean getState(int x, int y, int z) {
        Preconditions.checkArgument(x >= 0 && x < this.x);
        Preconditions.checkArgument(y >= 0 && y < this.y);
        Preconditions.checkArgument(z >= 0 && x < this.z);

        return voxels[x][y][z];
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }
}
