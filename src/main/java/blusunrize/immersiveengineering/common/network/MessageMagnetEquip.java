/*
 * BluSunrize
 * Copyright (c) 2017
 *
 * This code is licensed under "Blu's License of Common Sense"
 * Details can be found in the license file in the root folder of this project
 */

package blusunrize.immersiveengineering.common.network;

import blusunrize.immersiveengineering.common.items.IEShieldItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.NetworkEvent.Context;

public class MessageMagnetEquip implements IMessage
{
	private int fetchSlot;

	public MessageMagnetEquip(int fetch)
	{
		this.fetchSlot = fetch;
	}

	public MessageMagnetEquip(FriendlyByteBuf buf)
	{
		this.fetchSlot = buf.readInt();
	}

	@Override
	public void toBytes(FriendlyByteBuf buf)
	{
		buf.writeInt(this.fetchSlot);
	}

	@Override
	public void process(Context context)
	{
		ServerPlayer player = context.getSender();
		assert player!=null;
		context.enqueueWork(() -> {
			ItemStack held = player.getItemInHand(InteractionHand.OFF_HAND);
			if(fetchSlot >= 0)
			{
				ItemStack s = player.getInventory().items.get(fetchSlot);
				if(!s.isEmpty()&&s.getItem() instanceof IEShieldItem&&((IEShieldItem)s.getItem()).getUpgrades(s).getBoolean("magnet"))
				{
					((IEShieldItem)s.getItem()).getUpgrades(s).putInt("prevSlot", fetchSlot);
					player.getInventory().items.set(fetchSlot, held);
					player.setItemInHand(InteractionHand.OFF_HAND, s);
				}
			}
			else
			{
				if(held.getItem() instanceof IEShieldItem&&((IEShieldItem)held.getItem()).getUpgrades(held).getBoolean("magnet"))
				{
					int prevSlot = ((IEShieldItem)held.getItem()).getUpgrades(held).getInt("prevSlot");
					ItemStack s = player.getInventory().items.get(prevSlot);
					player.getInventory().items.set(prevSlot, held);
					player.setItemInHand(InteractionHand.OFF_HAND, s);
					((IEShieldItem)held.getItem()).getUpgrades(held).remove("prevSlot");
				}
			}
		});
	}
}