/*
 * BluSunrize
 * Copyright (c) 2020
 *
 * This code is licensed under "Blu's License of Common Sense"
 * Details can be found in the license file in the root folder of this project
 */

package blusunrize.immersiveengineering.common.gui;

import blusunrize.immersiveengineering.common.blocks.wooden.ItemBatcherBlockEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

import static blusunrize.immersiveengineering.common.blocks.wooden.ItemBatcherBlockEntity.NUM_SLOTS;

public class ItemBatcherContainer extends IEBaseContainerOld<ItemBatcherBlockEntity>
{
	public ItemBatcherContainer(MenuType<?> type, int id, Inventory inventoryPlayer, ItemBatcherBlockEntity tile)
	{
		super(type, tile, id);
		IItemHandler filterItemHandler = new ItemStackHandler(tile.getFilters());
		for(int i = 0; i < NUM_SLOTS; i++)
			this.addSlot(new IESlot.ItemHandlerGhost(filterItemHandler, i, 8+i*18, 30));
		for(int i = 0; i < NUM_SLOTS; i++)
			this.addSlot(new Slot(this.inv, i, 8+i*18, 59));

		this.ownSlotCount = 2*NUM_SLOTS;
		this.tile = tile;

		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 9; j++)
				addSlot(new Slot(inventoryPlayer, j+i*9+9, 8+j*18, 118+i*18));
		for(int i = 0; i < 9; i++)
			addSlot(new Slot(inventoryPlayer, i, 8+i*18, 176));
	}

	@Nonnull
	@Override
	public ItemStack quickMoveStack(Player player, int slot)
	{
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slotObject = this.slots.get(slot);
		if(slotObject!=null&&slotObject.hasItem())
		{
			ItemStack itemstack1 = slotObject.getItem();
			itemstack = itemstack1.copy();
			if(slot < ownSlotCount)
			{
				if(!this.moveItemStackTo(itemstack1, ownSlotCount, this.slots.size(), true))
					return ItemStack.EMPTY;
			}
			// exclude ghost slots from shiftclick
			else if(!this.moveItemStackTo(itemstack1, 9, ownSlotCount, false))
			{
				return ItemStack.EMPTY;
			}

			if(itemstack1.isEmpty())
				slotObject.set(ItemStack.EMPTY);
			else
				slotObject.setChanged();
		}
		return itemstack;
	}
}