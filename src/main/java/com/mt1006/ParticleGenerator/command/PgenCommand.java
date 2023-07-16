package com.mt1006.ParticleGenerator.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.mt1006.ParticleGenerator.ParticleGenerator;
import com.mt1006.ParticleGenerator.network.PacketHandler;
import com.mt1006.ParticleGenerator.network.ParticleGeneratorPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

import java.util.Arrays;
import java.util.List;

public class PgenCommand
{
	private static final String COMMAND_SMOKE_SETBLOCK = "/setblock ~ ~ ~ pgen:particle_generator{Particles:[{id:\"smoke\"}],UseAnimateTick:true}";
	private static final String COMMAND_SMOKE_GIVE = "/give @p pgen:particle_generator{BlockEntityTag:{Particles:[{id:\"smoke\"}],UseAnimateTick:true}}";
	private static final String COMMAND_LIKE_CAMPFIRE_SETBLOCK = "/setblock ~ ~ ~ pgen:particle_generator[position=bottom]{Particles:" +
			"[{id:\"campfire_cosy_smoke\",Motion:[0.0,0.07,0.0],PositionRand:[0.67,0.0,0.67],ParticleCount:2,ParticleMaxCount:3,Probability:0.11}]}";
	private static final String COMMAND_LIKE_CAMPFIRE_GIVE = "/give @p pgen:particle_generator{BlockStateTag:{position:\"bottom\"}," +
			"BlockEntityTag:{Particles:[{id:\"campfire_cosy_smoke\",Motion:[0.0,0.07,0.0],PositionRand:[0.67,0.0,0.67]," +
			"ParticleCount:2,ParticleMaxCount:3,Probability:0.11}]}}";

	public static void register(CommandDispatcher<CommandSourceStack> dispatcher)
	{
		LiteralCommandNode<CommandSourceStack> literalCommandNode = dispatcher.
				register(Commands.literal("particlegenerator").requires((source) -> {return source.hasPermission(2);}).
				then(Commands.literal("show").executes(PgenCommand::show)).
				then(Commands.literal("hide").executes(PgenCommand::hide)).
				then(Commands.literal("locate").executes(PgenCommand::locate)).
				then(Commands.literal("help").executes(PgenCommand::help)).
				then(Commands.literal("info").executes(PgenCommand::info)));
		dispatcher.register(Commands.literal("pgen").requires((source) -> {return source.hasPermission(2);}).
				redirect(literalCommandNode));
	}

	private static int show(CommandContext<CommandSourceStack> ctx)
	{
		Entity entity = ctx.getSource().getEntity();
		if (entity instanceof ServerPlayer)
		{
			PacketHandler.sendToClient(new ParticleGeneratorPacket(ParticleGeneratorPacket.OP_SHOW), (ServerPlayer)entity);
			return 1;
		}
		return 0;
	}

	private static int hide(CommandContext<CommandSourceStack> ctx)
	{
		Entity entity = ctx.getSource().getEntity();
		if (entity instanceof ServerPlayer)
		{
			PacketHandler.sendToClient(new ParticleGeneratorPacket(ParticleGeneratorPacket.OP_HIDE), (ServerPlayer)entity);
			return 1;
		}
		return 0;
	}

	private static int locate(CommandContext<CommandSourceStack> ctx)
	{
		Entity entity = ctx.getSource().getEntity();
		if (entity instanceof ServerPlayer)
		{
			PacketHandler.sendToClient(new ParticleGeneratorPacket(ParticleGeneratorPacket.OP_LOCATE), (ServerPlayer)entity);
			return 1;
		}
		return 0;
	}

	private static int help(CommandContext<CommandSourceStack> ctx)
	{
		//TODO: Internationalization
		List<String> text = Arrays.asList(
				ParticleGenerator.getName() + " - Help",
				"Commands:",
				"  /pgen show - Shows edges of particle generators",
				"  /pgen hide - Hides edges of particle generators",
				"  /pgen locate - Adds markers inside particle generators",
				"  /pgen info - Displays information about mod",
				"  /pgen help - Displays this message",
				"Particle Generator block states:",
				"  >position=[center(default)/top/bottom] -",
				"    determines position of particles generation",
				"Particle Generator NBT tags:",
				"  >Particles:[{...}] - list of compounds:",
				"    >id:\"...\" - particle id",
				"    >Motion:[x,y,z] - particle velocities [m/tick] (in most cases)",
				"    >MotionRand:[x,y,z] - randomization of \"Motion\" values",
				"    >PositionOffset:[x,y,z] - offset of particle positions",
				"    >PositionRand:[x,y,z] - randomization of particle positions",
				"    >Interval:int - interval between particles [ticks]",
				"    >Probability:double - probability of particle spawning",
				"    >ParticleCount:int - number of particles when spawned",
				"    >ParticleMaxCount:int - maximum number of particles",
				"    >AdditionalTags:{} - additional tags (like block id)",
				"  >UseAnimateTick:bool - spawn particles on animateTick",
				"Useful Minecraft BlockItem NBT tags:",
				"  >BlockStateTag:{} - specifies block states",
				"  >BlockEntityTag:{} - specifies block NBT tags",
				"Example usages:");
		for (String str : text)
		{
			ctx.getSource().sendSuccess(() -> Component.literal(str), false);
		}
		ctx.getSource().sendSuccess(() -> Component.literal("  >Simple smoke Particle Generator:"), false);
		ctx.getSource().sendSuccess(() -> Component.literal("    ").append(Component.literal("[using a /setblock]").withStyle((style) ->
				style.applyFormat(ChatFormatting.UNDERLINE).withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, COMMAND_SMOKE_SETBLOCK)))), false);
		ctx.getSource().sendSuccess(() -> Component.literal("    ").append(Component.literal("[using a /give]").withStyle((style) ->
				style.applyFormat(ChatFormatting.UNDERLINE).withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, COMMAND_SMOKE_GIVE)))), false);
		ctx.getSource().sendSuccess(() -> Component.literal("  >Particle Generator like campfire:"), false);
		ctx.getSource().sendSuccess(() -> Component.literal("    ").append(Component.literal("[using a /setblock]").withStyle((style) ->
				style.applyFormat(ChatFormatting.UNDERLINE).withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, COMMAND_LIKE_CAMPFIRE_SETBLOCK)))), false);
		ctx.getSource().sendSuccess(() -> Component.literal("    ").append(Component.literal("[using a /give]").withStyle((style) ->
				style.applyFormat(ChatFormatting.UNDERLINE).withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, COMMAND_LIKE_CAMPFIRE_GIVE)))), false);
		return 1;
	}

	private static int info(CommandContext<CommandSourceStack> ctx)
	{
		ctx.getSource().sendSuccess(() -> Component.literal(ParticleGenerator.getFullName()), false);
		ctx.getSource().sendSuccess(() -> Component.literal("Author: mt1006 (mt1006x)"), false);
		return 1;
	}
}
