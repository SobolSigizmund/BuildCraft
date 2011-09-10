package net.minecraft.src.buildcraft.transport.pipes;

import net.minecraft.src.buildcraft.transport.Pipe;
import net.minecraft.src.buildcraft.transport.PipeLogicGold;
import net.minecraft.src.buildcraft.transport.PipeTransportLiquids;

public class PipeLiquidsGold extends Pipe {

	public PipeLiquidsGold(int itemID) {
		super(new PipeTransportLiquids(), new PipeLogicGold(), itemID);
		
		((PipeTransportLiquids) transport).flowRate = 80;
	}

	@Override
	public int getBlockTexture() {
		return 7 * 16 + 4;
	}
}
