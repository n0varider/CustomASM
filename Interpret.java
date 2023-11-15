import java.util.Scanner;

public class Interpret {

    static int[] registers = new int[8]; // R0 -> R8
    static int[] memory = new int[128]; // M0 -> M8
    static Scanner sc = new Scanner(System.in);

    /*

    GENERAL PURPOSE             LOGIC               BRANCHING                   MEMORY                  PRINT
    0x1A: MOVE                  0x1B: AND           0x1C: IF EQUAL              0x1D: LOAD VALUE        0x7F: PRINT
    0x2A: ADD                   0x2B: OR            0x2C: IF NOT EQUAL          0x2D: STORE VALUE
    0x3A: SUBTRACT              0x3B: XOR           0x3C: LESS THAN
    0x4A: NO-OP                 0x4B: SRA           0x4C: GREATER THAN
    0x5A: MULTIPLY              0x5B: SLA           0x5C: GREATER THAN EQUAL
    0x6A: FLOOR DIVIDE                              0x6C: LESS THAN EQUAL

    USER INPUT
    0x1E: INTEGER

     */

    public static void main(String[] args) {
        byte[] code = {
                0x1A, 0x05, 0x00,       // MOV      32    R0
                0x1A, 0x10, 0x01,       // MOV      16    R1
                0x1A, 0x7F, 0x03,       // MOV      127   R3
                0x1A, 0x01, 0x04,       // MOV      1     R
                0x2D, 0x00, 0x04,       // STORE    M0 -> R4
                0x4C, 0x00, 0x01, 0x16, // BGT      R0    R1     GOTO PC=22
                0x2D, 0x00, 0x03,       // STORE    M0 -> R3
                0x1D, 0x00, 0x00,       // LOAD     M0 -> R0
                0x7F, 0x00              // PRINT    R0
        };


        int pc = 0;
        int val1;
        int val2;
        int register;
        int address;

        while (pc < code.length) {
            //System.out.println(pc);
            byte opcode = code[pc];
            pc++;
            switch(opcode) {

                // MOVE
                case 0x1A: // MOV
                    val1 = code[pc++];
                    register = code[pc++];
                    registers[register] = val1;
                    break;

                // ARITHMETIC
                case 0x2A:
                case 0x3A:
                case 0x5A:
                case 0x6A:
                    register = code[pc];
                    val1 = registers[code[pc++]];
                    val2 = registers[code[pc]];
                    switch(opcode) {
                        case 0x2A: // ADD
                            registers[register] = val1 + val2;
                            break;
                        case 0x3A: // SUB
                            registers[register] = val1 - val2;
                            break;
                        case 0x5A: // MUL
                            registers[register] = val1 * val2;
                            break;
                        case 0x6A: // DIV
                            registers[register] = val1 / val2;
                            break;
                    }
                    break;
                case 0x4A: // NO-OP
                    break;

                // LOGIC
                case 0x1B:
                case 0x2B:
                case 0x3B:
                    register = code[pc];
                    val1 = registers[code[pc++]];
                    val2 = registers[code[pc++]];
                    switch(opcode) {
                        case 0x1B: // AND
                            registers[register] = val1 & val2;
                            break;
                        case 0x2B: // OR
                            registers[register] = val1 | val2;
                            break;
                        case 0x3B: // XOR
                            registers[register] = val1 ^ val2;
                            break;
                    }
                    break;

                // BIT SHIFT
                case 0x4B: // SRA
                    register = code[pc++];
                    val1 = registers[register];
                    val1 = val1 >> code[pc];
                    registers[register] = val1;
                    break;
                case 0x5B: // SLA
                    register = code[pc++];
                    val1 = registers[register];
                    val1 = val1 << code[pc];
                    registers[register] = val1;
                    break;

                // BRANCH CONDITIONS
                case 0x1C:
                case 0x2C:
                case 0x3C:
                case 0x4C:
                case 0x5C:
                case 0x6C:
                    val1 = registers[code[pc++]];
                    val2 = registers[code[pc++]];
                    switch(opcode) {
                        case 0x1C: // EQUAL
                            pc = val1 == val2 ? code[pc] : pc;
                            break;
                        case 0x2C: // NOT EQUAL
                            pc = val1 != val2 ? code[pc] : pc;
                            break;
                        case 0x3C: // LESS THAN
                            pc = val1 < val2 ? code[pc] : pc;
                            break;
                        case 0x4C: // GREATER THAN
                            pc = val1 > val2 ? code[pc] : pc;
                            break;
                        case 0x5C: // GREATER THAN EQUAL TO
                            pc = val1 >= val2 ? code[pc] : pc;
                            break;
                        case 0x6C: // LESS THAN EQUAL TO
                            pc = val1 <= val2 ? code[pc] : pc;
                            break;
                    }
                    break;

                // MEMORY
                case 0x1D: // STORE
                    address = code[pc++];
                    register = code[pc];
                    registers[register] = memory[address];
                    break;
                case 0x2D: // LOAD
                    address = code[pc++];
                    register = code[pc];
                    memory[address] = registers[register];
                    break;

                case 0x1E:
                    register = code[pc++];
                    System.out.print("Enter integer: ");
                    registers[register] = sc.nextInt();
                    break;
                // PRINT
                case 0x7F: // PRINT
                     System.out.println(registers[code[pc++]]);
                     break;
            }
        }
    }

}
