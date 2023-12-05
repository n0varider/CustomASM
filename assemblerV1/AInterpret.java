package assemblerV1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class AInterpret {

    private static int[] registers = new int[8];
    private static int[] memory = new int[16]; // Pointers to actual values
   // private static String[] memobj = new String[16];
    private static byte[] obj = new byte[128];
    // "TYPE VALUE"

    static int pc = 0;
    public static void main(String[] args) throws IOException {

        // Input file
        File f = new File("C:\\Users\\vp102\\Desktop\\cd.txt");
        BufferedReader read = new BufferedReader(new FileReader(f));
        List<String> lines = read.lines().toList();
        read.close();
        String[] code = lines.toArray(new String[lines.size()]);

        // Execution
        Execution e = new Execution(code, registers, memory, obj);
        // System.out.format("0x%x ", b);
         e.run();

        System.out.print("\nPOINTERS:  ");
        System.out.println(Arrays.toString(Arrays.stream(memory).toArray()));
        System.out.print("MEMORY:    ");
        System.out.print(Arrays.toString(obj));
        System.out.print("\nREGISTERS: ");
        System.out.print(Arrays.toString(Arrays.stream(registers).toArray()));
    }
}