/*
 * BluSunrize
 * Copyright (c) 2020
 *
 * This code is licensed under "Blu's License of Common Sense"
 * Details can be found in the license file in the root folder of this project
 */

package blusunrize.immersiveengineering.common.items;

import blusunrize.immersiveengineering.ImmersiveEngineering;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;
import java.util.function.Predicate;

public class SawbladeItem extends IEBaseItem
{
	private final float sawbladeSpeed;
	private final float sawbladeDamage;
	private final ResourceLocation texture;

	public SawbladeItem(int maxDamage, float sawbladeSpeed, float sawbladeDamage, ResourceLocation texture)
	{
		super(new Properties().defaultDurability(maxDamage).setNoRepair());
		this.sawbladeSpeed = sawbladeSpeed;
		this.sawbladeDamage = sawbladeDamage;
		this.texture = texture;
		BuzzsawItem.SAWBLADES.add(this);
	}

	public SawbladeItem(int maxDamage, float sawbladeSpeed, float sawbladeDamage)
	{
		this(maxDamage, sawbladeSpeed, sawbladeDamage, ImmersiveEngineering.rl("item/sawblade_blade"));
	}

	@Override
	public boolean canBeDepleted()
	{
		return true;
	}

	@Override
	public boolean isEnchantable(ItemStack stack)
	{
		return false;
	}

	@Override
	public boolean isBookEnchantable(ItemStack stack, ItemStack book)
	{
		return false;
	}

	public final ResourceLocation getSawbladeTexture()
	{
		return texture;
	}

	public float getSawbladeSpeed()
	{
		return sawbladeSpeed;
	}

	public float getSawbladeDamage()
	{
		return sawbladeDamage;
	}

	public boolean canSawbladeFellTree()
	{
		return true;
	}

	public void modifyEnchants(Map<Enchantment, Integer> baseEnchants)
	{
	}

	public Predicate<BlockState> getSawbladeMaterials()
	{
		return s -> s.is(BlockTags.MINEABLE_WITH_AXE);
	}
}
