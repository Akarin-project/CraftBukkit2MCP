/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.craftbukkit.block.impl;

public final class CraftNote extends org.bukkit.craftbukkit.block.data.CraftBlockData implements org.bukkit.block.data.type.NoteBlock, org.bukkit.block.data.Powerable {

    public CraftNote() {
        super();
    }

    public CraftNote(minecraft.block.state.IBlockState state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.type.CraftNoteBlock

    private static final minecraft.block.properties.PropertyEnum<?> INSTRUMENT = getEnum(net.minecraft.block.BlockNote.class, "instrument");
    private static final minecraft.block.properties.PropertyInteger NOTE = getInteger(net.minecraft.block.BlockNote.class, "note");

    @Override
    public org.bukkit.Instrument getInstrument() {
        return get(INSTRUMENT, org.bukkit.Instrument.class);
    }

    @Override
    public void setInstrument(org.bukkit.Instrument instrument) {
        set(INSTRUMENT, instrument);
    }

    @Override
    public org.bukkit.Note getNote() {
       return new org.bukkit.Note(get(NOTE));
    }

    @Override
    public void setNote(org.bukkit.Note note) {
        set(NOTE, (int) note.getId());
    }

    // org.bukkit.craftbukkit.block.data.CraftPowerable

    private static final minecraft.block.properties.PropertyBool POWERED = getBoolean(net.minecraft.block.BlockNote.class, "powered");

    @Override
    public boolean isPowered() {
        return get(POWERED);
    }

    @Override
    public void setPowered(boolean powered) {
        set(POWERED, powered);
    }
}
