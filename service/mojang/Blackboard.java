package org.spongepowered.asm.service.mojang;

import net.minecraft.launchwrapper.Launch;
import org.spongepowered.asm.service.IGlobalPropertyService;

public class Blackboard implements IGlobalPropertyService {
   public final <T> T getProperty(String a) {
      return Launch.blackboard.get(a);
   }

   public final void setProperty(String a, Object a) {
      Launch.blackboard.put(a, a);
   }

   public final <T> T getProperty(String a, T a) {
      Object a = Launch.blackboard.get(a);
      return a != null ? a : a;
   }

   public final String getPropertyString(String a, String a) {
      Object a = Launch.blackboard.get(a);
      return a != null ? a.toString() : a;
   }
}
