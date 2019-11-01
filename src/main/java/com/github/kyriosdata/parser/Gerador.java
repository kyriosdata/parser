package com.github.kyriosdata.parser;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Arrays;

import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ACC_STATIC;
import static org.objectweb.asm.Opcodes.DADD;
import static org.objectweb.asm.Opcodes.DLOAD;
import static org.objectweb.asm.Opcodes.DMUL;
import static org.objectweb.asm.Opcodes.DRETURN;
import static org.objectweb.asm.Opcodes.V11;

public class Gerador {

    public static void main(String[] args) throws Exception {
        realize("D", "calcule");
    }

    public static void realize(String classe, String metodo) throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException, IOException {
        ClassWriter cw = new ClassWriter(0);
        cw.visit(V11, ACC_PUBLIC, classe, null, "java/lang/Object", null);

        // Depende da quantidade de variáveis empregadas na expressão
        String descriptor = "(DD)D";

        MethodVisitor mtd = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, metodo,
                descriptor, null, null);
        mtd.visitCode();

        // -------------- START -----------------------
        // Executa operações conforme notação pós-fixada da expressão
        // Este trecho terá que ser construído dinamicamente a partir
        // da expressão fornecida.

        mtd.visitVarInsn(DLOAD, 0);
        mtd.visitVarInsn(DLOAD, 2);
        mtd.visitInsn(DADD);

        // Multiplica resultado da adição por 200 (2E2
        mtd.visitLdcInsn(2e2);
        mtd.visitInsn(DMUL);

        // ------------- END --------------------------

        mtd.visitInsn(DRETURN);
        mtd.visitMaxs(4, 4);
        mtd.visitEnd();
        cw.visitEnd();

        byte[] bytecodes = cw.toByteArray();

        Path dirCorrente = FileSystems.getDefault().getPath(".");
        final Path path = Path.of(dirCorrente.toString(), classe + ".class");

        try (FileOutputStream outputStream =
                     new FileOutputStream(path.toString())) {
            outputStream.write(bytecodes);
        }

        double[] variaveis = new double[]{1.9, 7.1};
        System.out.println(avaliaExpressao(classe, bytecodes, variaveis));
    }

    private static double avaliaExpressao(String classe, byte[] bytecodes,
                                          double[] params) throws InvocationTargetException, IllegalAccessException {
        Object[] parametros = Arrays.stream(params).boxed().toArray();
        return avaliaExpressao(classe, bytecodes, parametros);
    }

    private static double avaliaExpressao(String classe, byte[] bytecodes,
                                          Object[] params) throws IllegalAccessException, InvocationTargetException {
        // Carregando a classe e executando o método
        DynamicClassLoader loader = new DynamicClassLoader();
        Class<?> clazz = loader.defineClass(classe, bytecodes);

        // Classe terá um único método e portanto, não é necessário
        // recuperar método declarado por meio do nome e tipos (args).
        // Class<?>[] tipos = new Class[]{Double.TYPE, Double.TYPE};
        // Method method = clazz.getDeclaredMethod(metodo, tipos);
        Method method = clazz.getDeclaredMethods()[0];

        return (double) method.invoke(null, params);
    }

    private static class DynamicClassLoader extends ClassLoader {
        public Class<?> defineClass(String name, byte[] b) {
            return defineClass(name, b, 0, b.length);
        }
    }
}
