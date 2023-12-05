package assemblerV1;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Scanner;
import java.util.function.BinaryOperator;

public class Execution {

    private int[] registers;
    private int[] memory; // Pointers
    private int pointerValue = 0;
    private byte[] memobjects; // Values
    private int pc;
    private String[] inst;

    private Map<String, BinaryOperator<Integer>> opcodes;
    public Execution(String[] inst, int[] reg, int[] mem, byte[] obj) {
        this.registers = reg;
        this.memory = mem;
        this.inst = inst;
        this.memobjects = obj;
        // LAMBDA MAPPINGS
        // opcodes.put("add", (a, b) -> a + b);
        // opcodes.put("div", (a, b) -> (b != 0) ? a / b : 0);
    }

    private String translate(String inst) {
        String[] tokens = inst.split(" ");
        switch(tokens[0]) {
            case "eqz":
                return "eq " + tokens[1] + " R0 " + tokens[2];
        }
        return null;
    }
    public void run() {
        pc = 0;
        while(pc < inst.length) {
            execute(parse(inst[pc]));
            pc++;
        }
    }

    private byte[] toBytes(int i) {
        return ByteBuffer.allocate(4).putInt(i).array();
    }

    private byte[] toBytes(String s) {
        return s.getBytes();
    }

    private String parse(String inst) {
        String[] tokens = inst.split(" ");
        for(int i = 1; i < tokens.length; i++) {
            if(tokens[i].contains("R") || tokens[i].contains("M")) {
                tokens[i] = tokens[i].substring(1);
            }
        }
        return String.join(" ", tokens);
    }

    private void storeValue(byte[] bytes) {
        for(byte b : bytes) {
            memobjects[pointerValue] = b;

            pointerValue++;
        }
        pointerValue++; // Allocate null byte
    }

    private int execute(String inst) {
        String[] tokens = inst.split(" ");

        int reg1 = Integer.valueOf(tokens[1]);
        int reg2;
        int destination;
            if (tokens.length == 2) {
                //reg1 = Integer.valueOf(tokens[1]);
                switch (tokens[0]) {
                    case "out":
                        System.out.println(registers[reg1]);
                        break;
                    default:
                        System.out.println("Invalid Instruction: " + inst);
                        pc = Integer.MAX_VALUE - 2;
                        break;
                }
            } else if (tokens.length == 3) {
                Scanner sc = new Scanner(System.in);
               // reg1 = Integer.valueOf(tokens[1]);
                reg2 = Integer.valueOf(tokens[2]);
                switch (tokens[0]) {// OPCODE
                    case "mv":
                        registers[reg2] = registers[reg1];
                        // delete reg1
                        break;
                    case "addi":
                        registers[reg1] += reg2;
                        break;
                    case "subi":
                        registers[reg1] -= reg2;
                        break;
                    case "eqz":
                        execute(parse(translate(inst)));
                        break;
                    case "in":
                        if (reg1 == 0) {
                            System.out.print("Enter integer: ");
                            registers[reg2] = sc.nextInt();
                            break;
                        } else {
                            System.out.println("Invalid input. Terminating program...");
                            pc = Integer.MAX_VALUE;
                            break;
                        }
                    default:
                        System.out.println("Invalid Instruction: " + inst);
                        pc = Integer.MAX_VALUE - 2;
                        break;
                }
            } else if (tokens.length == 4) {
               // reg1 = Integer.parseInt(tokens[1]);
                reg2 = Integer.parseInt(tokens[2]);
                destination = Integer.parseInt(tokens[3]);
                switch (tokens[0]) {// OPCODE
                    case "add":
                        registers[destination] = registers[reg1] + registers[reg2];
                        break;
                    case "sub":
                        registers[destination] = registers[reg1] - registers[reg2];
                        break;
                    case "mul":
                        registers[destination] = registers[reg1] * registers[reg2];
                        break;
                    case "div":
                        registers[destination] = registers[reg1] / registers[reg2];
                        break;
                    case "and":
                        registers[destination] = registers[reg1] & registers[reg2];
                        break;
                    case "or":
                        registers[destination] = registers[reg1] | registers[reg2];
                        break;
                    case "xor":
                        registers[destination] = registers[reg1] ^ registers[reg2];
                        break;
                    case "sra":
                        registers[destination] = registers[reg1] >> registers[reg2];
                        break;
                    case "sla":
                        registers[destination] = registers[reg1] << registers[reg2];
                        break;
                    case "srai":
                        registers[destination] = registers[reg1] >> reg2;
                        break;
                    case "sli":
                        registers[destination] = registers[reg1] << reg2;
                        break;
                    case "eq":
                        pc = registers[reg1] == registers[reg2] ? destination - 2 : pc;
                        break;
                    case "neq":
                        pc = registers[reg1] != registers[reg2] ? destination - 2 : pc;
                        break;
                    case "lt":
                        pc = registers[reg1] < registers[reg2] ? destination - 2 : pc;
                        break;
                    case "gte":
                        pc = registers[reg1] >= registers[reg2] ? destination - 2 : pc;
                        break;
                    default:
                        System.out.println("Invalid Instruction: " + inst);
                        pc = Integer.MAX_VALUE - 2;
                        break;

                }
            }
            return pc;
        }
}
