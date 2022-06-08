/*
 * BluSunrize
 * Copyright (c) 2017
 *
 * This code is licensed under "Blu's License of Common Sense"
 * Details can be found in the license file in the root folder of this project
 */

package blusunrize.immersiveengineering.common.gui;

import blusunrize.immersiveengineering.api.IETags;
import blusunrize.immersiveengineering.api.tool.ToolboxHandler;
import blusunrize.immersiveengineering.common.gui.IESlot.ICallbackContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ToolboxContainer extends InternalStorageItemContainer implements ICallbackContainer
{
	public ToolboxContainer(MenuType<?> type, int id, Inventory inventoryPlayer, Level world, EquipmentSlot slot, ItemStack toolbox)
	{
		super(type, id, inventoryPlayer, world, slot, toolbox);
	}

	@Override
	int addSlots()
	{
		int i = 0;
		this.addSlot(new IESlot.ContainerCallback(this, this.inv, i++, 48, 24));
		this.addSlot(new IESlot.ContainerCallback(this, this.inv, i++, 30, 42));
		this.addSlot(new IESlot.ContainerCallback(this, this.inv, i++, 48, 42));

		this.addSlot(new IESlot.ContainerCallback(this, this.inv, i++, 75, 24));
		this.addSlot(new IESlot.ContainerCallback(this, this.inv, i++, 93, 24));
		this.addSlot(new IESlot.ContainerCallback(this, this.inv, i++, 111, 24));
		this.addSlot(new IESlot.ContainerCallback(this, this.inv, i++, 75, 42));
		this.addSlot(new IESlot.ContainerCallback(this, this.inv, i++, 93, 42));
		this.addSlot(new IESlot.ContainerCallback(this, this.inv, i++, 111, 42));
		this.addSlot(new IESlot.ContainerCallback(this, this.inv, i++, 129, 42));

		for(int j = 0; j < 6; j++)
			this.addSlot(new IESlot.ContainerCallback(this, this.inv, i++, 35+j*18, 77));
		for(int j = 0; j < 7; j++)
			this.addSlot(new IESlot.ContainerCallback(this, this.inv, i++, 26+j*18, 112));

		bindPlayerInventory(inventoryPlayer);
		return i;
	}

	@Override
	public boolean canInsert(ItemStack stack, int slotNumer, Slot slotObject)
	{
		if(stack.isEmpty())
			return false;
		if(stack.is(IETags.forbiddenInCrates))
			return false;
		if(slotNumer < 3)
			return ToolboxHandler.isFood(stack);
		else if(slotNumer < 10)
			return ToolboxHandler.isTool(stack);
		else if(slotNumer < 16)
			return ToolboxHandler.isWiring(stack);
		else
			return true;
	}

	@Override
	public boolean canTake(ItemStack stack, int slotNumer, Slot slotObject)
	{
		return true;
	}

	protected void bindPlayerInventory(Inventory inventoryPlayer)
	{
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 9; j++)
				this.addSlot(new Slot(inventoryPlayer, j+i*9+9, 8+j*18, 157+i*18));

		for(int i = 0; i < 9; i++)
			this.addSlot(new Slot(inventoryPlayer, i, 8+i*18, 215));
	}
}