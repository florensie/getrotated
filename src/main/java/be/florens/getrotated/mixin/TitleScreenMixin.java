package be.florens.getrotated.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {

    @Shadow private long fadeInStart;
    @Shadow @Final private boolean fading;
    @Shadow @Final private PanoramaRenderer panorama;
    @Shadow @Final private static ResourceLocation PANORAMA_OVERLAY;

    public TitleScreenMixin(Component arg) {
        super(arg);
    }

    /**
     * @author Florens Pauwels
     * @reason get rotated
     */
    @Overwrite
    @Override
    public void render(GuiGraphics guiGraphics, int i, int j, float f) {
        if (this.fadeInStart == 0L && this.fading) {
            this.fadeInStart = Util.getMillis();
        }

        float g = this.fading ? (float) (Util.getMillis() - this.fadeInStart) / 1000.0F : 1.0F;
        this.panorama.render(f, Mth.clamp(g, 0.0F, 1.0F));
        RenderSystem.enableBlend();
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, this.fading ? (float) Mth.ceil(Mth.clamp(g, 0.0F, 1.0F)) : 1.0F);
        guiGraphics.blit(PANORAMA_OVERLAY, 0, 0, this.width, this.height, 0.0F, 0.0F, 16, 128, 16, 128);
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        float h = this.fading ? Mth.clamp(g - 1.0F, 0.0F, 1.0F) : 1.0F;
        int k = Mth.ceil(h * 255.0F) << 24;
        if ((k & -67108864) != 0) {

            for (GuiEventListener guiEventListener : this.children()) {
                if (guiEventListener instanceof AbstractWidget) {
                    ((AbstractWidget) guiEventListener).setAlpha(h);
                }
            }

            super.render(guiGraphics, i, j, f);
        }
    }

    /**
     * @author Florens Pauwels
     * @reason get rotated
     */
    @Overwrite
    public void init() {
        // NO-OP
    }
}
