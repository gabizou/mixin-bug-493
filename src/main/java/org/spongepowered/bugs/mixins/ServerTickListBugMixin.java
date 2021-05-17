package org.spongepowered.bugs.mixins;

import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.world.level.ServerTickList;
import net.minecraft.world.level.TickNextTickData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;
import java.util.Queue;

@Mixin(ServerTickList.class)
public abstract class ServerTickListBugMixin<T> {

    @Inject(
        method = "tick",
        at = @At(
            value = "INVOKE",
            target = "Ljava/util/function/Consumer;accept(Ljava/lang/Object;)V",
            remap = false,
            shift = At.Shift.AFTER
        ),
        locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void impl$markDataAsCompleted(CallbackInfo ci, int var0, ServerChunkCache var1, Iterator var2, TickNextTickData var4) {
    }

    @Redirect(
        method = "tick",
        at = @At(
            value = "INVOKE",
            target = "Ljava/util/Queue;add(Ljava/lang/Object;)Z",
            remap = false
        )
    )
    private boolean impl$validateHasNextUncancelled(Queue<TickNextTickData<T>> queue, Object data) {
        return queue.add((TickNextTickData<T>) data);
    }
}
