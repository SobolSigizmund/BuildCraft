/** 
 * BuildCraft is open-source. It is distributed under the terms of the 
 * BuildCraft Open Source License. It grants rights to read, modify, compile
 * or run the code. It does *NOT* grant the right to redistribute this software
 * or its modifications in any form, binary or source, except if expressively
 * granted by the copyright holder.
 * 
 * As a special exception, this file is part of the BuildCraft API and is 
 * allowed to be redistributed, either in source or binaries form.
 */

package net.minecraft.src.buildcraft.api;

import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public abstract class FillerPattern {
	
	/**
	 * stackToPlace contains the next item that can be place in the world. Null
	 * if there is none. IteratePattern is responsible to decrementing the 
	 * stack size if needed.
	 * Return true when the iteration process is finished.
	 */
	public abstract boolean iteratePattern(TileEntity tile, IBox box, ItemStack stackToPlace);

	public abstract String getTextureFile ();
	
	public abstract int getTextureIndex ();
	
	public int id;
	
	public boolean fill(int xMin, int yMin, int zMin, int xMax, int yMax,
			int zMax, ItemStack stackToPlace, World world) {
		boolean found = false;
		int xSlot = 0, ySlot = 0, zSlot = 0;

		for (int y = yMin; y <= yMax && !found; ++y) {
			for (int x = xMin; x <= xMax && !found; ++x) {
				for (int z = zMin; z <= zMax && !found; ++z) {					
					if (API.softBlock(world.getBlockId(x, y, z))) {
						xSlot = x;
						ySlot = y;
						zSlot = z;

						found = true;
					}
				}
			}
		}

		if (found && stackToPlace != null) {
			stackToPlace.getItem().onItemUse(stackToPlace, null, world,
					xSlot, ySlot + 1, zSlot, 0);
		}
		
		return !found;
	}
	
	public boolean empty(int xMin, int yMin, int zMin, int xMax, int yMax,
			int zMax, World world) {
		boolean found = false;
		int lastX = Integer.MAX_VALUE, lastY = Integer.MAX_VALUE, lastZ = Integer.MAX_VALUE;
		
		for (int y = yMin; y <= yMax; ++y) {
			found = false;
			for (int x = xMin; x <= xMax; ++x) {
				for (int z = zMin; z <= zMax; ++z) {
					if (!API.softBlock(world.getBlockId(x, y, z))
						&& !API.unbreakableBlock(world.getBlockId(x, y, z))) {
						found = true;
						lastX = x;
						lastY = y;
						lastZ = z;
					}
				}
			}

			if (found) {
				break;
			}
		}
		
		if (lastX != Integer.MAX_VALUE) {
			API.breakBlock(world, lastX, lastY, lastZ);
		}
				
		
		return lastX == Integer.MAX_VALUE;
	}

}
