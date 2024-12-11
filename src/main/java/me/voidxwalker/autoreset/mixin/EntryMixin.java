package me.voidxwalker.autoreset.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.world.WorldListWidget.Entry;
import net.minecraft.world.level.storage.LevelSummary;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

import me.voidxwalker.autoreset.Atum;

@Mixin(Entry.class)
public class EntryMixin {
    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow
    @Final
    private LevelSummary level;

    @Inject(method = "recreate", at = @At("TAIL"))
    private void atum_cloneDesiredWorld(CallbackInfo info) throws IOException
    {
        Atum.backupAttempts++;
    }
}
