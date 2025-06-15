package org.spongepowered.tools.obfuscation.struct;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic.Kind;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;

public class InjectorRemap {
   private final boolean remap;
   private Message message;
   private int remappedCount;

   public InjectorRemap(boolean a) {
      a.remap = a;
   }

   public boolean shouldRemap() {
      return a.remap;
   }

   public void notifyRemapped() {
      ++a.remappedCount;
      a.clearMessage();
   }

   public void addMessage(Kind a, CharSequence a, Element a, AnnotationHandle a) {
      a.message = new Message(a, a, a, a);
   }

   public void clearMessage() {
      a.message = null;
   }

   public void dispatchPendingMessages(Messager a) {
      if (a.remappedCount == 0 && a.message != null) {
         a.message.sendTo(a);
      }

   }
}
