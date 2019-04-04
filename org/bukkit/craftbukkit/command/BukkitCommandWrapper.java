package org.bukkit.craftbukkit.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import net.minecraft.command.CommandSenderWrapper;
import org.bukkit.command.Command;
import org.bukkit.craftbukkit.CraftServer;

public class BukkitCommandWrapper implements com.mojang.brigadier.Command<CommandSenderWrapper>, Predicate<CommandSenderWrapper>, SuggestionProvider<CommandSenderWrapper> {

    private final CraftServer server;
    private final Command command;

    public BukkitCommandWrapper(CraftServer server, Command command) {
        this.server = server;
        this.command = command;
    }

    public LiteralCommandNode<CommandSenderWrapper> register(CommandDispatcher<CommandSenderWrapper> dispatcher, String label) {
        return dispatcher.register(
                LiteralArgumentBuilder.<CommandSenderWrapper>literal(label).requires(this).executes(this)
                .then(RequiredArgumentBuilder.<CommandSenderWrapper, String>argument("args", StringArgumentType.greedyString()).suggests(this).executes(this))
        );
    }

    @Override
    public boolean test(CommandSenderWrapper wrapper) {
        return command.testPermissionSilent(wrapper.getBukkitSender());
    }

    @Override
    public int run(CommandContext<CommandSenderWrapper> context) throws CommandSyntaxException {
        return server.dispatchCommand(context.getSource().getBukkitSender(), context.getInput()) ? 1 : 0;
    }

    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<CommandSenderWrapper> context, SuggestionsBuilder builder) throws CommandSyntaxException {
        List<String> results = server.tabComplete(context.getSource().getBukkitSender(), builder.getInput(), context.getSource().getWorld(), context.getSource().getPosition(), true);

        // These are normally only set based on sub nodes, but we have just one giant args node
        builder.start = builder.getInput().lastIndexOf(' ') + 1;
        builder.remaining = builder.getInput().substring(builder.start);

        for (String s : results) {
            builder.suggest(s);
        }

        return builder.buildFuture();
    }
}
