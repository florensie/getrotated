package be.florens.getrotated.mixin;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.PanoramaRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin {

    @Shadow @Final private PanoramaRenderer panorama;

    /**
     * @author Florens Pauwels
     * @reason get rotated
     */
    @Overwrite
    public void render(GuiGraphics guiGraphics, int i, int j, float f) {
        this.panorama.render(f, 1.0F);
    }
}
