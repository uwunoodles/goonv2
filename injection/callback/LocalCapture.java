package org.spongepowered.asm.mixin.injection.callback;

public enum LocalCapture {
   NO_CAPTURE(false, false),
   PRINT(false, true),
   CAPTURE_FAILSOFT,
   CAPTURE_FAILHARD,
   CAPTURE_FAILEXCEPTION;

   private final boolean captureLocals;
   private final boolean printLocals;

   private LocalCapture() {
      this(true, false);
   }

   private LocalCapture(boolean a, boolean a) {
      a.captureLocals = a;
      a.printLocals = a;
   }

   boolean isCaptureLocals() {
      return a.captureLocals;
   }

   boolean isPrintLocals() {
      return a.printLocals;
   }
}
