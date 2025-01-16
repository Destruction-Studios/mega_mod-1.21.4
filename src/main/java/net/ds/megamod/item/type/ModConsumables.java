package net.ds.megamod.item.type;

import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.item.consume.UseAction;
import net.minecraft.sound.SoundEvents;

public class ModConsumables {

    public static ConsumableComponent.Builder food() {
        return ConsumableComponent.builder().consumeSeconds(1.6F).useAction(UseAction.EAT).sound(SoundEvents.ENTITY_GENERIC_EAT).consumeParticles(true);
    }

    public static ConsumableComponent.Builder drink() {
        return ConsumableComponent.builder().consumeSeconds(1.6F).useAction(UseAction.DRINK).sound(SoundEvents.ENTITY_GENERIC_DRINK).consumeParticles(false);
    }
}

