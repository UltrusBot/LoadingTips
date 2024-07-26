package me.ultrusmods.loadingscreentips.mixin;

import me.ultrusmods.loadingscreentips.LoadingScreenTips;
import me.ultrusmods.loadingscreentips.TipShowingScreen;
import me.ultrusmods.loadingscreentips.config.LoadingScreenTipsConfig;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.ConnectScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ConnectScreen.class)
public abstract class ConnectScreenMixin extends Screen implements TipShowingScreen {
    protected ConnectScreenMixin(Text text) {
        super(text);
    }

    private String randomTip;
    float tipTimer = 0f;

    @Inject(method = "<init>", at = @At("TAIL"))
    void pickRandomTip(CallbackInfo ci) {
        randomTip = LoadingScreenTips.getRandomTip();
    }

    @Inject(method = "render", at = @At("TAIL"))
    void drawLoadingTip(DrawContext graphics, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (LoadingScreenTipsConfig.serverLoadingTips) {
            drawLoadingTips(textRenderer, graphics, width, height, delta);
        }
    }
    @Override
    public float getTipTimer() {
        return tipTimer;
    }

    @Override
    public void setRandomTip(String randomTip) {
        this.randomTip = randomTip;
    }

    @Override
    public void setTipTimer(float tipTimer) {
        this.tipTimer = tipTimer;
    }

    @Override
    public String getRandomTip() {
        return randomTip;
    }
}
