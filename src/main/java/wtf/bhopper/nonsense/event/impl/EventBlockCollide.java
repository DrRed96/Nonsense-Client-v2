package wtf.bhopper.nonsense.event.impl;

import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import wtf.bhopper.nonsense.event.Cancellable;

public class EventBlockCollide extends Cancellable {

    public final Block block;
    public final BlockPos pos;
    public AxisAlignedBB bounds;

    public EventBlockCollide(Block block, BlockPos pos, AxisAlignedBB bounds) {
        this.block = block;
        this.pos = pos;
        this.bounds = bounds;
    }

}
