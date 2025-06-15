package org.spongepowered.asm.lib;

class Handler {
   Label start;
   Label end;
   Label handler;
   String desc;
   int type;
   Handler next;

   static Handler remove(Handler a, Label a, Label a) {
      if (a == null) {
         return null;
      } else {
         a.next = remove(a.next, a, a);
         int a = a.start.position;
         int a = a.end.position;
         int a = a.position;
         int a = a == null ? Integer.MAX_VALUE : a.position;
         if (a < a && a > a) {
            if (a <= a) {
               if (a >= a) {
                  a = a.next;
               } else {
                  a.start = a;
               }
            } else if (a >= a) {
               a.end = a;
            } else {
               Handler a = new Handler();
               a.start = a;
               a.end = a.end;
               a.handler = a.handler;
               a.desc = a.desc;
               a.type = a.type;
               a.next = a.next;
               a.end = a;
               a.next = a;
            }
         }

         return a;
      }
   }
}
