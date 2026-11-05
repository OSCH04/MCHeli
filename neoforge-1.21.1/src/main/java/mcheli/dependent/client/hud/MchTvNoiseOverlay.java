package mcheli.dependent.client.hud;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import mcheli.MCHeli;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;

/**
 * The TV-missile camera's image static — the port of {@code MCH_AircraftCommonGui.drawTvMissileNoise}: the
 * {@code textures/gui/noise.png} noise drawn ADDITIVELY over the whole screen in grey at 40 %, at a random texture
 * offset every frame, while the local player looks through his TV missile's nose camera.
 */
final class MchTvNoiseOverlay {
    private MchTvNoiseOverlay() {}

    private static final ResourceLocation TV_NOISE = ResourceLocation.fromNamespaceAndPath(MCHeli.MODID, "textures/gui/noise.png");
    private static final RandomSource RAND = RandomSource.create();

    static void render(GuiGraphics g, DeltaTracker deltaTracker) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.options.hideGui || mcheli.dependent.client.MchTvView.activeMissile() == null) {
            return;
        }
        int w = g.guiWidth();
        int h = g.guiHeight();
        // Sample a 128-wide window of the 256x256 noise (aspect-matched) at a random offset and stretch it to the screen.
        int uw = 128;
        int vh = Math.max(1, Math.min(256, uw * h / Math.max(1, w)));
        float u = RAND.nextInt(256 - uw + 1);
        float v = RAND.nextInt(256 - vh + 1);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE); // reference glBlendFunc(1, 1)
        g.setColor(0.5F, 0.5F, 0.5F, 0.4F);
        g.blit(TV_NOISE, 0, 0, w, h, u, v, uw, vh, 256, 256);
        g.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
    }
}
