package earth.terrarium.adastra.event;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.api.systems.OxygenApi;
import earth.terrarium.adastra.common.handlers.PlanetHandler;
import earth.terrarium.adastra.common.items.armor.SpaceSuitItem;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.entity.living.LivingBreatheEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = AdAstra.MOD_ID)
public class LivingBreatheEventSubscriber {

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void spaceSuitLivingBreatheEvent(LivingBreatheEvent event){
        if(event.getEntity() instanceof ServerPlayer player) {
            ItemStack stack = player.getItemBySlot(EquipmentSlot.CHEST);
            if(stack.getItem() instanceof SpaceSuitItem item
                && player.tickCount % 12 == 0
                && SpaceSuitItem.hasOxygen(player)
                && !event.canBreathe()
                && player.getEyeInFluidType() != ForgeMod.WATER_TYPE.get())
            {
                item.consumeOxygen(stack, 1);
                event.setCanBreathe(true);
                event.setCanRefillAir(true);
            }
        }
    }

}
