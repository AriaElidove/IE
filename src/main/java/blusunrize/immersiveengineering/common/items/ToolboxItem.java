/*
 * BluSunrize
 * Copyright (c) 2017
 *
 * This code is licensed under "Blu's License of Common Sense"
 * Details can be found in the license file in the root folder of this project
 */

package blusunrize.immersiveengineering.common.items;

import blusunrize.immersiveengineering.common.register.IEBlocks.MetalDevices;
import blusunrize.immersiveengineering.common.register.IEContainerTypes;
import blusunrize.immersiveengineering.common.register.IEContainerTypes.ItemContainerTypeNew;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.BundleTooltip;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.AbstractCollection;
import java.util.AbstractList;
import java.util.Optional;

public class ToolboxItem extends InternalStorageItem
{
	public static final int SLOT_COUNT = 23;

	public ToolboxItem()
	{
		super(new Properties().stacksTo(1));
	}

	@Nonnull
	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, @Nonnull InteractionHand hand)
	{
		ItemStack stack = player.getItemInHand(hand);
		if(!world.isClientSide)
			openGui(player, hand);
		return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
	}

	@Nullable
	@Override
	protected ItemContainerTypeNew<?> getContainerTypeNew()
	{
		return IEContainerTypes.TOOLBOX;
	}

	@Override
	public InteractionResult useOn(UseOnContext ctx)
	{
		ItemStack stack = ctx.getItemInHand();
		Player player = ctx.getPlayer();
		if(player!=null&&player.isShiftKeyDown())
		{
			Level world = ctx.getLevel();
			BlockPos pos = ctx.getClickedPos();
			Direction side = ctx.getClickedFace();
			BlockState state = world.getBlockState(pos);
			if(!state.canBeReplaced(new BlockPlaceContext(ctx)))
				pos = pos.relative(side);

			if(stack.getCount()!=0&&player.mayUseItemAt(pos, side, stack))//TODO &&world.mayPlace(IEContent.blockToolbox, pos, false, side, null))
			{
				BlockState toolbox = MetalDevices.TOOLBOX.defaultBlockState();
				if(world.setBlock(pos, toolbox, 3))
				{
					MetalDevices.TOOLBOX.get().onIEBlockPlacedBy(new BlockPlaceContext(ctx), toolbox);

					SoundType soundtype = world.getBlockState(pos).getBlock().getSoundType(world.getBlockState(pos), world, pos, player);
					world.playSound(player, pos, soundtype.getPlaceSound(), SoundSource.BLOCKS, (soundtype.getVolume()+1.0F)/2.0F, soundtype.getPitch()*0.8F);
					stack.shrink(1);
				}
				return InteractionResult.SUCCESS;
			}
			else
				return InteractionResult.FAIL;
		}
		return super.useOn(ctx);
	}

	@Override
	public int getSlotCount()
	{
		return SLOT_COUNT;
	}

	@Override
	public boolean canFitInsideContainerItems()
	{
		return false;
	}

	@Nonnull
	@Override
	public Optional<TooltipComponent> getTooltipImage(@Nonnull ItemStack stack)
	{
		if(stack.hasTag())
		{
			// cut all empty slots from list
			NonNullList<ItemStack> items = getContainedItems(stack)
					.stream()
					.filter(s -> !s.isEmpty())
					.collect(NonNullList::create, AbstractList::add, AbstractCollection::addAll);
			return Optional.of(new BundleTooltip(items, 0));
		}
		return super.getTooltipImage(stack);
	}
}
