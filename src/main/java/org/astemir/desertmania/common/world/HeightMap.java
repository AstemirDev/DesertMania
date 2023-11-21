package org.astemir.desertmania.common.world;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.astemir.api.math.components.Rect3;

import java.util.Arrays;

public class HeightMap {

    private final int[] data;
    private final int width;
    private final int height;
    private final Rect3 region;


    public HeightMap(Level level,Rect3 rect3) {
        this.region = rect3;
        this.width = (int) region.getWidth();
        this.height = (int) region.getLength();;
        data = new int[width * height];
        for (int z = 0; z < height; ++z) {
            for (int x = 0; x < width; ++x) {
                int y = getHighestTerrainBlock(level,x +(int) region.getPosition().x, z + (int)region.getPosition().z, (int)region.getPosition().y, (int) (region.getPosition().y+region.getHeight()));;
                data[z * width + x] = y;
            }
        }
    }

    public static int getHighestTerrainBlock(Level level,int x,int z,int minY,int maxY){
        for (int y = maxY; y >= minY; --y) {
            BlockPos pos = new BlockPos(x, y, z);
            boolean hardBlock = !level.getBlockState(pos).isAir();
            if (hardBlock) {
                return y;
            }
        }
        return minY;
    }

    public void applyFilter(Level level,HeightMap.Filter filter, int iterations) {
        int[] newData = new int[data.length];
        System.arraycopy(data, 0, newData, 0, data.length);
        for (int i = 0; i < iterations; ++i) {
            newData = filter.filter(newData, width, height, 0.5F);
        }
        apply(level,newData);
    }

    public void apply(Level level,int[] data) {
        BlockPos position = new BlockPos(region.getPosition().toVec3());
        int originX = position.getX();
        int originY = position.getY();
        int originZ = position.getZ();
        int maxY = (int) region.getPosition().add(region.getSize()).y;
        BlockState fillerAir = Blocks.AIR.defaultBlockState();
        for (int z = 0; z < height; ++z) {
            for (int x = 0; x < width; ++x) {
                int index = z * width + x;
                int curHeight = this.data[index];
                int newHeight = Math.min(maxY, data[index]);
                int xr = x + originX;
                int zr = z + originZ;
                double scale = (double) (curHeight - originY) / (double) (newHeight - originY);
                if (newHeight > curHeight) {
                    BlockState existing = level.getBlockState(new BlockPos(xr, curHeight, zr));
                    if (existing.getFluidState().isEmpty()) {
                        level.setBlock(new BlockPos(xr, newHeight, zr), existing,19);
                        for (int y = newHeight - 1 - originY; y >= 0; --y) {
                            int copyFrom = (int) Math.floor(y * scale);
                            level.setBlock(new BlockPos(xr, originY + y, zr), level.getBlockState(new BlockPos(xr, originY + copyFrom, zr)),19);
                        }
                    }
                } else if (curHeight > newHeight) {
                    for (int y = 0; y < newHeight - originY; ++y) {
                        int copyFrom = (int) Math.floor(y * scale);
                        level.setBlock(new BlockPos(xr, originY + y, zr), level.getBlockState(new BlockPos(xr, originY + copyFrom, zr)),19);
                    }
                    level.setBlock(new BlockPos(xr, newHeight, zr), level.getBlockState(new BlockPos(xr, curHeight, zr)),19);
                    for (int y = newHeight + 1; y <= curHeight; ++y) {
                        level.setBlock(new BlockPos(xr, y, zr), fillerAir,19);
                    }
                }
            }
        }
    }


    public static class Filter {

        private Kernel kernel;

        public Filter(Kernel kernel) {
            this.kernel = kernel;
        }

        public Filter(int kernelWidth, int kernelHeight, float[] kernelData) {
            this.kernel = new Kernel(kernelWidth, kernelHeight, kernelData);
        }

        public Kernel getKernel() {
            return kernel;
        }


        public void setKernel(Kernel kernel) {
            this.kernel = kernel;
        }

        public int[] filter(int[] inData, int width, int height) {
            return filter(inData, width, height, 0.5F);
        }


        public int[] filter(int[] inData, int width, int height, float offset) {
            float[] inDataFloat = new float[inData.length];
            for (int i = 0; i < inData.length; i++) {
                inDataFloat[i] = inData[i];
            }

            int index = 0;
            float[] matrix = kernel.getKernelData(null);
            int[] outData = new int[inData.length];

            for (int y = 0; y < height; ++y) {
                for (int x = 0; x < width; ++x) {
                    outData[index++] = (int) Math.floor(calculateHeight(inDataFloat, width, height, offset, matrix, x, y));
                }
            }
            return outData;
        }

        public float[] filter(float[] inData, int width, int height, float offset) {
            int index = 0;
            float[] matrix = kernel.getKernelData(null);
            float[] outData = new float[inData.length];

            for (int y = 0; y < height; ++y) {
                for (int x = 0; x < width; ++x) {
                    outData[index++] = calculateHeight(inData, width, height, offset, matrix, x, y);
                }
            }
            return outData;
        }

        private float calculateHeight(float[] inData, int width, int height, float offset, float[] matrix, int x, int y) {
            int kh = kernel.getHeight();
            int kw = kernel.getWidth();
            int kox = kernel.getXOrigin();
            int koy = kernel.getYOrigin();
            float z = 0;
            for (int ky = 0; ky < kh; ++ky) {
                int offsetY = y + ky - koy;
                if (offsetY < 0 || offsetY >= height) {
                    offsetY = y;
                }

                offsetY *= width;

                int matrixOffset = ky * kw;
                for (int kx = 0; kx < kw; ++kx) {
                    float f = matrix[matrixOffset + kx];
                    if (f == 0) {
                        continue;
                    }

                    int offsetX = x + kx - kox;
                    if (offsetX < 0 || offsetX >= width) {
                        offsetX = x;
                    }
                    z += f * inData[offsetY + offsetX];
                }
            }
            return z + offset;
        }
    }

    public static class Kernel {

        private final int width;
        private final int height;
        private final int xOrigin;
        private final int yOrigin;
        private final float[] data;

        public Kernel(int width, int height, float[] data) {
            this.width = width;
            this.height = height;
            this.xOrigin = (width - 1) >> 1;
            this.yOrigin = (height - 1) >> 1;
            int len = width * height;
            if (data.length < len) {
                throw new IllegalArgumentException("Data array too small (is " + data.length + " and should be " + len);
            }
            this.data = new float[len];
            System.arraycopy(data, 0, this.data, 0, len);
        }

        public final int getXOrigin() {
            return xOrigin;
        }

        public final int getYOrigin() {
            return yOrigin;
        }

        public final int getWidth() {
            return width;
        }

        public final int getHeight() {
            return height;
        }

        public final float[] getKernelData(float[] data) {
            if (data == null) {
                data = new float[this.data.length];
            } else if (data.length < this.data.length) {
                throw new IllegalArgumentException("Data array too small (should be " + this.data.length + " but is " + data.length + " )");
            }
            System.arraycopy(this.data, 0, data, 0, this.data.length);
            return data;
        }


        public static class Linear extends Kernel {

            public Linear(int radius) {
                super(radius * 2 + 1, radius * 2 + 1, createKernel(radius));
            }

            private static float[] createKernel(int radius) {
                int diameter = radius * 2 + 1;
                float[] data = new float[diameter * diameter];

                Arrays.fill(data, 1.0f / data.length);

                return data;
            }

        }

        public static class Gaussian extends Kernel {


            public Gaussian(int radius, double sigma) {
                super(radius * 2 + 1, radius * 2 + 1, createKernel(radius, sigma));
            }

            private static float[] createKernel(int radius, double sigma) {
                int diameter = radius * 2 + 1;
                float[] data = new float[diameter * diameter];

                double sigma22 = 2 * sigma * sigma;
                double constant = Math.PI * sigma22;
                for (int y = -radius; y <= radius; ++y) {
                    for (int x = -radius; x <= radius; ++x) {
                        data[(y + radius) * diameter + x + radius] = (float) (Math.exp(-(x * x + y * y) / sigma22) / constant);
                    }
                }
                return data;
            }

        }

    }


}
