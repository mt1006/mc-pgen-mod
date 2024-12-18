package com.mt1006.pgen.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.mt1006.pgen.PgenMod;
import com.mt1006.pgen.network.PgenPacketS2C;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

public class PgenCommand
{
	private static final String COMMAND_SMOKE_SETBLOCK = "/setblock ~ ~ ~ pgen:particle_generator{Particles:[{id:\"smoke\"}],UseAnimateTick:true}";
	private static final String COMMAND_SMOKE_GIVE = "/give @p pgen:particle_generator[block_entity_data={id:\"pgen:particle_generator\"," +
			"Particles:[{id:\"smoke\"}],UseAnimateTick:true}]";
	private static final String COMMAND_LIKE_CAMPFIRE_SETBLOCK = "/setblock ~ ~ ~ pgen:particle_generator[position=bottom]{Particles:" +
			"[{id:\"campfire_cosy_smoke\",Motion:[0.0,0.07,0.0],PositionRand:[0.67,0.0,0.67],ParticleCount:2,ParticleMaxCount:3,Probability:0.11}]}";
	private static final String COMMAND_LIKE_CAMPFIRE_GIVE = "/give @p pgen:particle_generator[block_state={position:bottom}," +
			"block_entity_data={id:\"pgen:particle_generator\",Particles:[{id:\"campfire_cosy_smoke\",Motion:[0.0,0.07,0.0]," +
			"PositionRand:[0.67,0.0,0.67],ParticleCount:2,ParticleMaxCount:3,Probability:0.11}]}]";

	public static void register(CommandDispatcher<CommandSourceStack> dispatcher)
	{
		LiteralCommandNode<CommandSourceStack> literalCommandNode = dispatcher.
				register(Commands.literal("particlegenerator").requires((source) -> source.hasPermission(2)).
				then(Commands.literal("show").executes(PgenCommand::show)).
				then(Commands.literal("hide").executes(PgenCommand::hide)).
				then(Commands.literal("locate").executes(PgenCommand::locate)).
				then(Commands.literal("help").executes(PgenCommand::help)).
				then(Commands.literal("info").executes(PgenCommand::info)));
		dispatcher.register(Commands.literal("pgen").requires((source) -> source.hasPermission(2)).
				redirect(literalCommandNode));
	}

	private static int show(CommandContext<CommandSourceStack> ctx)
	{
		Entity entity = ctx.getSource().getEntity();
		if (entity instanceof ServerPlayer)
		{
			PgenPacketS2C.send((ServerPlayer)entity, PgenPacketS2C.OP_SHOW);
			return 1;
		}
		return 0;
	}

	private static int hide(CommandContext<CommandSourceStack> ctx)
	{
		Entity entity = ctx.getSource().getEntity();
		if (entity instanceof ServerPlayer)
		{
			PgenPacketS2C.send((ServerPlayer)entity, PgenPacketS2C.OP_HIDE);
			return 1;
		}
		return 0;
	}

	private static int locate(CommandContext<CommandSourceStack> ctx)
	{
		Entity entity = ctx.getSource().getEntity();
		if (entity instanceof ServerPlayer)
		{
			PgenPacketS2C.send((ServerPlayer)entity, PgenPacketS2C.OP_LOCATE);
			return 1;
		}
		return 0;
	}

	private static int help(CommandContext<CommandSourceStack> ctx)
	{
		CommandSourceStack source = ctx.getSource();

		source.sendSuccess(() -> Component.translatable("pgen.help.message", PgenMod.getName()), false);
		source.sendSuccess(() -> Component.translatable("pgen.help.examples", PgenMod.getName()), false);

		source.sendSuccess(() -> Component.translatable("pgen.help.examples.simple_smoke"), false);
		source.sendSuccess(() -> Component.literal("    ").append(Component.translatable("pgen.help.examples.using", "/setblock").withStyle((style) ->
				style.applyFormat(ChatFormatting.UNDERLINE).withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, COMMAND_SMOKE_SETBLOCK)))), false);
		source.sendSuccess(() -> Component.literal("    ").append(Component.translatable("pgen.help.examples.using", "/give").withStyle((style) ->
				style.applyFormat(ChatFormatting.UNDERLINE).withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, COMMAND_SMOKE_GIVE)))), false);

		source.sendSuccess(() -> Component.translatable("pgen.help.examples.like_campfire"), false);
		source.sendSuccess(() -> Component.literal("    ").append(Component.translatable("pgen.help.examples.using", "/setblock").withStyle((style) ->
				style.applyFormat(ChatFormatting.UNDERLINE).withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, COMMAND_LIKE_CAMPFIRE_SETBLOCK)))), false);
		source.sendSuccess(() -> Component.literal("    ").append(Component.translatable("pgen.help.examples.using", "/give").withStyle((style) ->
				style.applyFormat(ChatFormatting.UNDERLINE).withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, COMMAND_LIKE_CAMPFIRE_GIVE)))), false);
		return 1;
	}

	private static int info(CommandContext<CommandSourceStack> ctx)
	{
		ctx.getSource().sendSuccess(() -> Component.literal(PgenMod.getFullName()), false);
		ctx.getSource().sendSuccess(() -> Component.literal("Author: mt1006"), false);
		return 1;
	}
}
