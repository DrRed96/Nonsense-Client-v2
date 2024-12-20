package wtf.bhopper.nonsense.util.minecraft.world;

import io.netty.util.internal.ThreadLocalRandom;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import wtf.bhopper.nonsense.util.minecraft.MinecraftInstance;

public class BlockUtil implements MinecraftInstance {

    public static IBlockState getState(BlockPos blockPos) {
        return mc.theWorld.getBlockState(blockPos);
    }

    public static Block getBlock(BlockPos blockPos) {
        return mc.theWorld.getBlockState(blockPos).getBlock();
    }

    public static boolean isAir(BlockPos blockPos) {
        return getBlock(blockPos).getMaterial() == Material.air;
    }

    public static Block getBlockRelativeToPlayer(final double offsetX, final double offsetY, final double offsetZ) {
        return mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX + offsetX, mc.thePlayer.posY + offsetY, mc.thePlayer.posZ + offsetZ)).getBlock();
    }

    public static Block getRelativeBlock(double x, double y, double z) {
        return getBlock(new BlockPos(mc.thePlayer).add(x, y, z));
    }

    public static boolean isSolid(BlockPos blockPos) {
        return getBlock(blockPos)
                .getMaterial()
                .blocksMovement();
    }

    public static BlockPos randomPos() {
        return new BlockPos(
                ThreadLocalRandom.current().nextInt(-30000000, 30000000),
                ThreadLocalRandom.current().nextInt(-30000000, 30000000),
                ThreadLocalRandom.current().nextInt(-30000000, 30000000)
        );
    }

    public static boolean compareProperties(IBlockState state1, IBlockState state2, IProperty<?>... properties) {
        for (IProperty<?> property : properties) {
            if (state1.getValue(property) != state2.getValue(property)) {
                return false;
            }
        }

        return true;
    }

    public static BlockLever.EnumOrientation getLeverFace(BlockPos pos, EnumFacing facing) {
        if (BlockLever.func_181090_a(mc.theWorld, pos, facing.getOpposite())) {
            return BlockLever.EnumOrientation.forFacings(facing, mc.thePlayer.getHorizontalFacing());
        }

        for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
            if (enumfacing != facing && BlockLever.func_181090_a(mc.theWorld, pos, enumfacing.getOpposite())) {
                return BlockLever.EnumOrientation.forFacings(enumfacing, mc.thePlayer.getHorizontalFacing());
            }
        }

        if (World.doesBlockHaveSolidTopSurface(mc.theWorld, pos.down())) {
            return BlockLever.EnumOrientation.forFacings(EnumFacing.UP, mc.thePlayer.getHorizontalFacing());
        }

        return null;
    }

    public static boolean blockPosEqual(BlockPos blockPosA, BlockPos blockPosB) {

        if (blockPosA == null || blockPosB == null) {
            return false;
        }

        return blockPosA.toLong() == blockPosB.toLong();
    }

    public static boolean compareStatesBasic(IBlockState state1, IBlockState state2) {

        Block block = state1.getBlock();

        if (block != state2.getBlock()) {
            return false;
        }

        if (block instanceof BlockColored) {
            return compareProperties(state1, state2, BlockColored.COLOR);
        }

        if (block instanceof BlockNewLog) {
            return compareProperties(state1, state2, BlockNewLog.VARIANT);
        }

        if (block instanceof BlockOldLog) {
            return compareProperties(state1, state2, BlockOldLog.VARIANT);
        }

        if (block instanceof BlockPlanks) {
            return compareProperties(state1, state2, BlockPlanks.VARIANT);
        }

        if (block instanceof BlockStainedGlass) {
            return compareProperties(state1, state2, BlockStainedGlass.COLOR);
        }

        if (block instanceof BlockStainedGlassPane) {
            return compareProperties(state1, state2, BlockStainedGlassPane.COLOR);
        }

        if (block instanceof BlockStone) {
            return compareProperties(state1, state2, BlockStone.VARIANT);
        }

        return true;
    }

}