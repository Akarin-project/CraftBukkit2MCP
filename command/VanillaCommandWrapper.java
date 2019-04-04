package org.bukkit.craftbukkit.command;

import com.google.common.base.Joiner;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.tree.CommandNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.command.CommandSenderWrapper;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.entity.item.EntityMinecartCommandBlock;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import org.apache.commons.lang3.Validate;
import org.bukkit.Location;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.ProxiedCommandSender;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftMinecartCommand;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.CommandMinecart;

public final class VanillaCommandWrapper extends BukkitCommand {

    private final ServerCommandManager dispatcher;
    public final CommandNode<CommandSenderWrapper> vanillaCommand;

    public VanillaCommandWrapper(ServerCommandManager dispatcher, CommandNode<CommandSenderWrapper> vanillaCommand) {
        super(vanillaCommand.getName(), "A Mojang provided command.", vanillaCommand.getUsageText(), Collections.EMPTY_LIST);
        this.dispatcher = dispatcher;
        this.vanillaCommand = vanillaCommand;
        this.setPermission(getPermission(vanillaCommand));
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!testPermission(sender)) return true;

        CommandSenderWrapper icommandlistener = getListener(sender);
        dispatcher.a(icommandlistener, toDispatcher(args, getName()), toDispatcher(args, commandLabel));
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args, Location location) throws IllegalArgumentException {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        Validate.notNull(alias, "Alias cannot be null");

        CommandSenderWrapper icommandlistener = getListener(sender);
        ParseResults<CommandSenderWrapper> parsed = dispatcher.a().parse(toDispatcher(args, getName()), icommandlistener);

        List<String> results = new ArrayList<>();
        dispatcher.a().getCompletionSuggestions(parsed).thenAccept((suggestions) -> {
            suggestions.getList().forEach((s) -> results.add(s.getText()));
        });

        return results;
    }

    public static class WorldRescueContext {

        private WorldServer[] prev;

        public WorldRescueContext start(WorldServer def) {
            // Some commands use the worldserver variable but we leave it full of null values,
            // so we must temporarily populate it with the world of the commandsender
            prev = MinecraftServer.getServer().worlds;
            MinecraftServer server = MinecraftServer.getServer();
            server.worlds = new WorldServer[server.worlds.size()];
            server.worlds[0] = def;
            int bpos = 0;
            for (int pos = 1; pos < server.worlds.length; pos++) {
                WorldServer world = server.worlds.get(bpos++);
                if (server.worlds[0] == world) {
                    pos--;
                    continue;
                }
                server.worlds[pos] = world;
            }

            return this;
        }

        public void end() {
            MinecraftServer.getServer().worlds = prev;
        }
    }

    private CommandSenderWrapper getListener(CommandSender sender) {
        if (sender instanceof Player) {
            return ((CraftPlayer) sender).getHandle().getCommandListener();
        }
        if (sender instanceof BlockCommandSender) {
            return ((CraftBlockCommandSender) sender).getWrapper();
        }
        if (sender instanceof CommandMinecart) {
            return ((EntityMinecartCommandBlock) ((CraftMinecartCommand) sender).getHandle()).getCommandBlockLogic().getWrapper();
        }
        if (sender instanceof RemoteConsoleCommandSender) {
            return ((DedicatedServer) MinecraftServer.getServer()).rconConsoleSource.f();
        }
        if (sender instanceof ConsoleCommandSender) {
            return ((CraftServer) sender.getServer()).getServer().getServerCommandListener();
        }
        if (sender instanceof ProxiedCommandSender) {
            return ((ProxiedNativeCommandSender) sender).getHandle();
        }

        throw new IllegalArgumentException("Cannot make " + sender + " a vanilla command listener");
    }

    public static String getPermission(CommandNode<CommandSenderWrapper> vanillaCommand) {
        return "minecraft.command." + ((vanillaCommand.getRedirect() == null) ? vanillaCommand.getName() : vanillaCommand.getRedirect().getName());
    }

    private String toDispatcher(String[] args, String name) {
        return "/" + name + ((args.length > 0) ? " " + Joiner.on(' ').join(args) : "");
    }
}
