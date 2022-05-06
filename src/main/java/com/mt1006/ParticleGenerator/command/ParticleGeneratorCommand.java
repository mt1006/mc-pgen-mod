package com.mt1006.ParticleGenerator.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.mt1006.ParticleGenerator.ParticleGenerator;
import com.mt1006.ParticleGenerator.network.PacketHandler;
import com.mt1006.ParticleGenerator.network.ParticleGeneratorPacket;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;

import java.util.Arrays;
import java.util.List;

public class ParticleGeneratorCommand
{
	private static final String COMMAND_SMOKE_SETBLOCK = "/setblock ~ ~ ~ pgen:particle_generator{Particles:[{id:\"smoke\"}],UseAnimateTick:true}";
	private static final String COMMAND_SMOKE_GIVE = "/give @p pgen:particle_generator{BlockEntityTag:{Particles:[{id:\"smoke\"}],UseAnimateTick:true}}";
	private static final String COMMAND_LIKE_CAMPFIRE_SETBLOCK = "/setblock ~ ~ ~ pgen:particle_generator[position=bottom]{Particles:" +
			"[{id:\"campfire_cosy_smoke\",Motion:[0.0,0.07,0.0],PositionRand:[0.67,0.0,0.67],ParticleCount:2,ParticleMaxCount:3,Probability:0.11}]}";
	private static final String COMMAND_LIKE_CAMPFIRE_GIVE = "/give @p pgen:particle_generator{BlockStateTag:{position:\"bottom\"}," +
			"BlockEntityTag:{Particles:[{id:\"campfire_cosy_smoke\",Motion:[0.0,0.07,0.0],PositionRand:[0.67,0.0,0.67]," +
			"ParticleCount:2,ParticleMaxCount:3,Probability:0.11}]}}";

	public static void register(CommandDispatcher<CommandSource> dispatcher)
	{
		LiteralCommandNode<CommandSource> literalCommandNode = dispatcher.
				register(Commands.literal("particlegenerator").requires((source) -> {return source.hasPermission(2);}).
				then(Commands.literal("show").executes(ParticleGeneratorCommand::show)).
				then(Commands.literal("hide").executes(ParticleGeneratorCommand::hide)).
				then(Commands.literal("locate").executes(ParticleGeneratorCommand::locate)).
				then(Commands.literal("help").executes(ParticleGeneratorCommand::help)).
				then(Commands.literal("info").executes(ParticleGeneratorCommand::info)));
		dispatcher.register(Commands.literal("pgen").requires((source) -> {return source.hasPermission(2);}).
				redirect(literalCommandNode));
	}

	private static int show(CommandContext<CommandSource> ctx)
	{
		Entity entity = ctx.getSource().getEntity();
		if (entity instanceof ServerPlayerEntity)
		{
			PacketHandler.sendToClient(new ParticleGeneratorPacket(ParticleGeneratorPacket.OP_SHOW), (ServerPlayerEntity)entity);
			return 1;
		}
		return 0;
	}

	private static int hide(CommandContext<CommandSource> ctx)
	{
		Entity entity = ctx.getSource().getEntity();
		if (entity instanceof ServerPlayerEntity)
		{
			PacketHandler.sendToClient(new ParticleGeneratorPacket(ParticleGeneratorPacket.OP_HIDE), (ServerPlayerEntity) entity);
			return 1;
		}
		return 0;
	}

	private static int locate(CommandContext<CommandSource> ctx)
	{
		Entity entity = ctx.getSource().getEntity();
		if (entity instanceof ServerPlayerEntity)
		{
			PacketHandler.sendToClient(new ParticleGeneratorPacket(ParticleGeneratorPacket.OP_LOCATE), (ServerPlayerEntity)entity);
			return 1;
		}
		return 0;
	}

	private static int help(CommandContext<CommandSource> ctx)
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
			ctx.getSource().sendSuccess(new StringTextComponent(str), false);
		}
		ctx.getSource().sendSuccess(new StringTextComponent("  >Simple smoke Particle Generator:"), false);
		ctx.getSource().sendSuccess(new StringTextComponent("    ").append(new StringTextComponent("[using a /setblock]").withStyle((style) ->
				style.applyFormat(TextFormatting.UNDERLINE).withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, COMMAND_SMOKE_SETBLOCK)))), false);
		ctx.getSource().sendSuccess(new StringTextComponent("    ").append(new StringTextComponent("[using a /give]").withStyle((style) ->
				style.applyFormat(TextFormatting.UNDERLINE).withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, COMMAND_SMOKE_GIVE)))), false);
		ctx.getSource().sendSuccess(new StringTextComponent("  >Particle Generator like campfire:"), false);
		ctx.getSource().sendSuccess(new StringTextComponent("    ").append(new StringTextComponent("[using a /setblock]").withStyle((style) ->
				style.applyFormat(TextFormatting.UNDERLINE).withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, COMMAND_LIKE_CAMPFIRE_SETBLOCK)))), false);
		ctx.getSource().sendSuccess(new StringTextComponent("    ").append(new StringTextComponent("[using a /give]").withStyle((style) ->
				style.applyFormat(TextFormatting.UNDERLINE).withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, COMMAND_LIKE_CAMPFIRE_GIVE)))), false);
		return 1;
	}

	private static int info(CommandContext<CommandSource> ctx)
	{
		ctx.getSource().sendSuccess(new StringTextComponent(ParticleGenerator.getFullName()), false);
		ctx.getSource().sendSuccess(new StringTextComponent("Author: mt1006"), false);
		return 1;
	}
}
