package org.spongepowered.asm.lib.tree;

import java.util.Iterator;
import java.util.List;
import org.spongepowered.asm.lib.MethodVisitor;

public class TryCatchBlockNode {
   public LabelNode start;
   public LabelNode end;
   public LabelNode handler;
   public String type;
   public List<TypeAnnotationNode> visibleTypeAnnotations;
   public List<TypeAnnotationNode> invisibleTypeAnnotations;

   public TryCatchBlockNode(LabelNode a, LabelNode a, LabelNode a, String a) {
      a.start = a;
      a.end = a;
      a.handler = a;
      a.type = a;
   }

   public void updateIndex(int a) {
      int a = 1107296256 | a << 8;
      Iterator var3;
      TypeAnnotationNode a;
      if (a.visibleTypeAnnotations != null) {
         for(var3 = a.visibleTypeAnnotations.iterator(); var3.hasNext(); a.typeRef = a) {
            a = (TypeAnnotationNode)var3.next();
         }
      }

      if (a.invisibleTypeAnnotations != null) {
         for(var3 = a.invisibleTypeAnnotations.iterator(); var3.hasNext(); a.typeRef = a) {
            a = (TypeAnnotationNode)var3.next();
         }
      }

   }

   public void accept(MethodVisitor a) {
      a.visitTryCatchBlock(a.start.getLabel(), a.end.getLabel(), a.handler == null ? null : a.handler.getLabel(), a.type);
      int a = a.visibleTypeAnnotations == null ? 0 : a.visibleTypeAnnotations.size();

      int a;
      TypeAnnotationNode a;
      for(a = 0; a < a; ++a) {
         a = (TypeAnnotationNode)a.visibleTypeAnnotations.get(a);
         a.accept(a.visitTryCatchAnnotation(a.typeRef, a.typePath, a.desc, true));
      }

      a = a.invisibleTypeAnnotations == null ? 0 : a.invisibleTypeAnnotations.size();

      for(a = 0; a < a; ++a) {
         a = (TypeAnnotationNode)a.invisibleTypeAnnotations.get(a);
         a.accept(a.visitTryCatchAnnotation(a.typeRef, a.typePath, a.desc, false));
      }

   }
}
