package mcheli.dependent.client;

import mcheli.dependent.entity.AbstractMchVehicle;
import mcheli.dependent.entity.MchBullet;
import net.minecraft.client.Minecraft;

/**
 * Client-side TV-missile view — the port of the reference {@code isMissileCameraMode}: while the local player is in
 * camera view (gunner mode / a gunner seat) and his vehicle's active TV missile is one he fired, the camera rides in the
 * missile's nose ({@code CameraMixin}), the {@code tv_missile} HUD + TV noise replace the cockpit HUD, and the missile's
 * own model and smoke trail are hidden so they don't block the picture.
 */
public final class MchTvView {
    private MchTvView() {}

    /** The TV missile the local player is looking through right now, or null. */
    public static MchBullet activeMissile() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null && mc.player.getVehicle() instanceof AbstractMchVehicle v) {
            return v.tvMissileViewedBy(mc.player);
        }
        return null;
    }

    /** Is {@code missile} the one the local player is flying through its camera? */
    public static boolean isViewing(MchBullet missile) {
        return missile != null && missile == activeMissile();
    }
}
