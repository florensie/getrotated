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
        this.panorama.render(f, 1.0F);
        RenderSystem.enableBlend();
        guiGraphics.blit(PANORAMA_OVERLAY, 0, 0, this.width, this.height, 0.0F, 0.0F, 16, 128, 16, 128);
    }
}
