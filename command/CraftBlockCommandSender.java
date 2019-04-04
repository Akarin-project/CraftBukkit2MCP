package org.bukkit.craftbukkit.command;

import net.minecraft.command.CommandSenderWrapper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.math.Vec3d;

import org.bukkit.block.Block;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.craftbukkit.util.CraftChatMessage;

/**
 * Represents input from a command block
 */
public class CraftBlockCommandSender extends ServerCommandSender implements BlockCommandSender {
    private final CommandSenderWrapper block;

    public CraftBlockCommandSender(CommandSenderWrapper commandBlockListenerAbstract) {
        super();
        this.block = commandBlockListenerAbstract;
    }

    public Block getBlock() {
        Vec3d pos = block.getPosition();
        return block.getWorld().getWorld().getBlockAt((int) pos.x, (int) pos.y, (int) pos.z);
    }

    public void sendMessage(String message) {
        for (ITextComponent component : CraftChatMessage.fromString(message)) {
            block.delegate.sendMessage(component);
        }
    }

    public void sendMessage(String[] messages) {
        for (String message : messages) {
            sendMessage(message);
        }
    }

    public String getName() {
        return block.getName();
    }

    public boolean isOp() {
        return true;
    }

    public void setOp(boolean value) {
        throw new UnsupportedOperationException("Cannot change operator status of a block");
    }

    public CommandSenderWrapper getWrapper() {
        return block;
    }
}
