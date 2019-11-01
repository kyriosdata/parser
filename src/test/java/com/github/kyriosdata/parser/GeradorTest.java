package com.github.kyriosdata.parser;

import org.junit.jupiter.api.Test;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import static org.objectweb.asm.Opcodes.*;

class GeradorTest {

    @Test
    void a() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ClassWriter cw = new ClassWriter(0);
        cw.visit(V11, ACC_PUBLIC, "A", null, "java/lang/Object", null);

        final int PUBLIC_STATIC = ACC_PUBLIC + ACC_STATIC;
        MethodVisitor main = cw.visitMethod(PUBLIC_STATIC, "main",
                "([Ljava/lang/String;)V", null, null);
        main.visitCode();
        main.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io" +
                "/PrintStream;");

        // Coloca no topo da pilha o valor 1
        final int c1 = cw.newConst(10);
        main.visitInsn(LDC);
        main.visitInsn(c1);

        // Coloca no topo da pilha o valor 5
        final int c2 = cw.newConst(51);
        main.visitInsn(LDC);
        main.visitInsn(c2);

        // Adiciona elementos no topo da pilha
        main.visitInsn(IADD);

        main.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println",
                "(I)V", false);
        main.visitInsn(RETURN);
        main.visitMaxs(5, 2);
        main.visitEnd();
        cw.visitEnd();

        byte[] bytecodes = cw.toByteArray();

        Path dirCorrente = FileSystems.getDefault().getPath(".");
        final Path path = Path.of(dirCorrente.toString(), "A.class");

        try (FileOutputStream outputStream =
                     new FileOutputStream(path.toString())) {
            outputStream.write(bytecodes);
        }

        // Carregando e executando a classe criada dinamicamente
        DynamicClassLoader loader = new DynamicClassLoader();
        Class<?> clazz = loader.defineClass("A", bytecodes);
        Method method = clazz.getMethod("main", String[].class);
        method.invoke(null, (Object) new String[0]);
        System.out.println(clazz.getName());

    }

    @Test
    void b() throws IOException, NoSuchMethodException,
            InvocationTargetException, IllegalAccessException {
        ClassWriter cw = new ClassWriter(0);
        cw.visit(V11, ACC_PUBLIC, "B", null, "java/lang/Object", null);

        final int PUBLIC_STATIC = ACC_PUBLIC + ACC_STATIC;
        MethodVisitor main = cw.visitMethod(PUBLIC_STATIC, "calcule",
                "(II)I", null, null);
        main.visitCode();

        main.visitCode();
        main.visitVarInsn(ILOAD, 0);
        main.visitVarInsn(ILOAD, 1);
        main.visitInsn(IADD);
        main.visitInsn(IRETURN);
        main.visitMaxs(2, 2);
        main.visitEnd();
        cw.visitEnd();

        byte[] bytecodes = cw.toByteArray();

        Path dirCorrente = FileSystems.getDefault().getPath(".");
        final Path path = Path.of(dirCorrente.toString(), "B.class");

        try (FileOutputStream outputStream =
                     new FileOutputStream(path.toString())) {
            outputStream.write(bytecodes);
        }

        // Carregando e executando a classe criada dinamicamente
        DynamicClassLoader loader = new DynamicClassLoader();
        Class<?> clazz = loader.defineClass("B", bytecodes);
        Class<?>[] tipos = new Class[] { Integer.TYPE, Integer.TYPE };
        Method method = clazz.getDeclaredMethod("calcule", tipos);
        Object[] params = { 1, 7 };
        Object retorno = method.invoke(null, params);
        System.out.println(clazz.getName());
        System.out.println(retorno);
    }
}


class DynamicClassLoader extends ClassLoader {
    public Class<?> defineClass(String name, byte[] b) {
        return defineClass(name, b, 0, b.length);
    }

    public static int calcule(int x, int y) {
        return x + y;
    }
}
