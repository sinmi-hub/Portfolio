/*Author: Sinmi.*/

/*This program represents a hypothetical processor that 32 bit word length
 * meaning that instructions, registers and memory words are all 32 bits The
 * processor has 4 fields. This includes opcode, 2 registers and a memory
 * address. 22 different instructions as defined in the header file, and has
 * 18 registers too. Each word is defined as having an opcode, 2
 * registers and a memory address. The first 5 bits are the opcodes (which
 * are predefined instructions as stated earlier), the next 5 bits are the
 * registers, after that, the next 5 bits symbolize the second register,
 * while the rest of the 17 BITS defines the memory address
 * The function of this processor is essentially to convert machine
 * instructions or the words into assembly language. The program also mimics
 * an operating system by loading machine language or instruction into the
 * processor's memory to be executed.*/
#include "omniflux_core.h"
#include <stdio.h>

unsigned int extract_bit(int num_bits);
void print_opcode(unsigned int opcode);
int check_instruction(unsigned int instruction);

/*This function interprets its parameter as an instruction. It uses the
 * operators in C to extract the fields from an instruction. These fields are
 * defined in the class comments. It then prints the fields on the
 * instruction on at output line. If the parameter does not represent a valid
 * instruction, 0 is simply returned, with nothing printed. However, if it is
 * valid, the output is printed and 1 is returned */
unsigned short print_instructions(unsigned int instruction) {
    /*state checks whether the instruction is valid before extracting its
     * field*/
    unsigned short state = (unsigned short) check_instruction(instruction);

    unsigned int opcode = 0;
    unsigned int register_1 = 0;
    unsigned int register_2 = 0;
    unsigned int mem_addr = 0;

    /* Instruction in the parameter is printed if it is valid or not*/
    if (state){
        /* extracting opcode by shifting the entire instruction or word
         * right by 27 bits. Since there are 32 bits in all, the leftmost 5
         * bits are what is left and padded with 0s to the left*/
        opcode = (instruction >> 27);
        /* extract the value of the first register by shifting 22 bits to the
         * right first. This removes leaves value of the opcode and the first
         * register itself. We then use the bitwise operator '&' to remove
         * the opcode from this value*/
        register_1 = (instruction >> 22) & extract_bit(5);
        register_2 = (instruction >> 17) & extract_bit(5);
        mem_addr = (instruction << 15);/*shifts to the right 15 bits*/
        mem_addr >>= 15;/* shifts to the left 15 bits to make the bits of
                        * memory address accessible*/

        print_opcode(opcode);

        /* first register is only printed if an instruction or the opcode
         * operation uses at least one register. The range of such opcode
         * value is [2, 20]*/
        if (opcode >= 2 && opcode <= 20) {
            printf("\t");
            printf("R%d ", register_1);

            /* second register is only printed if an instruction or the
             * opcode operation uses two register. Given that this is a
             * nested if statement, the lowest boundary is 2. Therefore,
             * such opcode value is in the range [2,8] and [11,17]*/
            if ((opcode <= 8) || (opcode >= 11 && opcode <= 17)) {
                printf("R%d ", register_2);
            }
        }

        /* memory address is only printed if an instruction uses the
         * memory address for its operation or if it is immediate.*/
        if (opcode >= 18){

            /* if opcode value is 21, prints a tab, before printing
             * memory address*/
            if (opcode == 21){
                printf("\t");
            }
            printf("%05d", mem_addr);
        }
        printf("\n");
    }

    return state;
}

/*This function simulates an operating system loading a machine language
 * program into the processor's memory, so it can be executed. It copies the
 * content of the array, "program", into the array "memory" stating at start
 * address in the memory. The program array contains the set of instructions
 * to be copied into memory. It could also contain data segment or text
 * segment, which stores variables needed for the instructions to work on.
 * Start address is a word address, where each word is 4bytes. pgm_size
 * refers to the size of the array "program". Data segment_size part of the
 * program array that are actually not instructions, but simply text segment
 * that contain data */
int init_program(unsigned int mem[], const unsigned int prog[],
                 unsigned int start_addr, unsigned short pgm_size,
                 unsigned short data_segment_size){
    int state = 0;
    unsigned int start_index = (start_addr / 4);
    unsigned int *mem = mem;
    const unsigned int *pgm = prog;


    /*------------checking for valid parameters--------------------*/
    /*Each of the condition checked below is as follows:
     * Start address cannot be greater that NUM_WORDS, where NUM_WORDS is the
     * size of the memory array as defined in header file.
     * Start address must also be a valid word address that is a byte.
     * Size of array to be copied "program" cannot be greater than size of
     * array "memory" to copy into.
     * Check to see that instructions exist in the program array and not just
     * the data segment.
     * Also making sure that everything can be copied from program array into
     * memory array, no matter what the start index is*/
    if (start_addr > NUM_WORDS || start_addr % 4 != 0 ||pgm_size > NUM_WORDS
            || pgm_size == data_segment_size || mem == NULL || pgm == NULL
            || pgm_size + start_index >NUM_WORDS)
    {
        state = 0;
    }

    else{
        unsigned int pgm_index = 0;

        /* iterate through the pgm array*/
        for (pgm_index = 0; pgm_index < pgm_size; pgm_index++){

            /* checks if every single iteration of instruction is valid and
             * copies word from program array to memory array*/
            if (check_instruction(prog[pgm_index]) == 1){
                mem[start_index] = prog[pgm_index];
                start_index++;
                state = 1;
            }
        }
    }

    return state;
}

/*This function simulates a disassembler that converts machine language
 * into assembly language. This function assumes that the array,
 * "memory" is valid with at least NUM_WORDS element which is defined in
 * the header. The parameters are pretty much similar to that of the
 *init_program function too. This function prints all the instruction
 * in the memory and if there is a data segment, it prints that too*/
unsigned short decode_program(const unsigned int memory[],
                           unsigned int start_addr, unsigned int pgm_size,
                           unsigned int data_segment_size){
    unsigned short valid = 1;
    unsigned int start_index = (start_addr / 4);
    /*defines the range where instruction is present in the array,
     * "memory". The size of the program is subtracted from the
     * data_segment_size to give the rest of the array which should
     * contain instructions. start index is added to it since start index
     * might not always start at 0*/
    unsigned int instruct_size = (pgm_size - data_segment_size) +
                                    start_index;
    const unsigned int *mem = memory;


    /*--------checking for more invalid parameters-----------*/
    /* Size of array to be copied "program" cannot be greater than
     * size of array "memory" to copy into.
     * Checking that start_address is valid before doing anything.
     * There must be at least one instruction present in memory.
     */
    if(start_addr % 4 != 0 || start_addr > NUM_WORDS
        || pgm_size > NUM_WORDS || mem == NULL
        || pgm_size == data_segment_size )
    {
        valid = 0;
    }


    /*----------------TESTING FOR PARAMETER VALIDITY-------------*/
    if (valid){
        /*----------checking for invalid parameter array---------------*/
        for(start_index = (start_addr / 4); start_index < instruct_size
                                && valid; start_index++)
        {
           /* Every iteration of instruction is checked for validity. If one
            * appears to be invalid at any iteration, then the whole array,
            * "memory" is considered to be invalid.*/
            if (check_instruction(memory[start_index]) == 0){
                valid = 0;
            }

        }
    }

    if(valid){
        for(start_index = (start_addr / 4); start_index < instruct_size;
                                                            start_index++)
        {
            /* if instruction is VALID, it is printed, and the flag,
             * "result" is changed*/
            print_instructions(memory[start_index]);
            valid = 1;
        }
    }

    /* here, we check to see if memory was valid. If the words in memory
     * were indeed valid, we check again to see if there was any data
     * present in the same memory array */
    if (valid != 0 && data_segment_size > 0){
        unsigned int data_index = 0;

        /* here we continue at one index greater than where se stopped.
         * Since all instructions have been read, and subsequent word
         * left is the text segment that contains data. This is also
         * printed*/
        for(data_index = start_index; data_index < pgm_size;
                data_index++)
        {
            printf("%08x\n", memory[data_index]);
        }
    }

    return valid;
}

/* -----------------------Helper Methods-----------------------------*/
/*This function is a function that performs bit masking. It takes a certain
 * number of bits called num_bits, shifts 1 to the left by num_bits,
 * and then subtract 1 to create a buffer for the position to
 * the left of it, which would always be 0, because we originally shifted.
 * This is very useful for bit manipulation. Any number that the bitwise
 * operator '&' is used on this result will provide the rightmost
 * "num_bits" bits in the original number */
unsigned int extract_bit(int num_bits){
    unsigned int mask = 0;

    mask = ((1 << num_bits) - 1);

    return mask;
}

/*this function simply prints the opcode depending on the value that is
 *passed. It uses a switch statement to define what should be printed. All
 *of these opcodes have been predefined in the header file as an enum and 
 *are simply accessed using the values*/
void print_opcode(unsigned int opcode_num){
    enum opcode_name opcode = opcode_num;

    switch (opcode) {
        case HALT:
            printf("halt");
            break;
        case SYSCALL:
            printf("syscall");
            break;
        case ADD:
            printf("add");
            break;
        case SUB:
            printf("sub");
            break;
        case MUL:
            printf("mul");
            break;
        case QUOT:
            printf("quot");
            break;
        case MOD:
            printf("mod");
            break;
        case AND:
            printf("and");
            break;
        case OR:
            printf("or");
            break;
        case NEG:
            printf("neg");
            break;
        case NOT:
            printf("not");
            break;
        case EQL:
            printf("eql");
            break;
        case NEQ:
            printf("neq");
            break;
        case LT:
            printf("lt");
            break;
        case LE:
            printf("le");
            break;
        case GT:
            printf("gt");
            break;
        case GE:
            printf("ge");
            break;
        case MOVE:
            printf("move");
            break;
        case LW:
            printf("lw");
            break;
        case SW:
            printf("sw");
            break;
        case LI:
            printf("li");
            break;
        case BRANCH:
            printf("branch");
            break;
    }

}

/*This function checks to make sure that the instruction that is passed in is
 * a valid one. It does this by breaking down the instruction into 4
 * different fields. The opcode value, 2 registers and its memory address. It
 * then checks the validity for each one. An opcode value is defined as valid
 * if it is less than 22 (There are 22 values [0-21]). Anything greater than
 * 21 is invalid. There are also 18 registers([0-17]). Any register value
 * greater than 17 is deemed invalid. The test for memory address is to check
 * if it is a byte. i.e. if its value is evenly dividable into 4 groups*/
int check_instruction(unsigned int instruction){
    int validity = 0;

    unsigned int opcode = 0;
    unsigned int register_1 = 0;
    unsigned int register_2 = 0;
    unsigned int mem_addr = 0;

    /* extracting opcode by shifting the entire instruction or word
     * right by 27 bits. Since there are 32 bits in all, the leftmost
     * 5 bits are what is left and padded with 0s*/
    opcode = (instruction >> 27);
    /*extracting the bits using the positions where they are located*/
    register_1 = (instruction >> 22) & extract_bit(5);
    register_2 = (instruction >> 17) & extract_bit(5);
    mem_addr = (instruction << 15);
    mem_addr >>= 15;

    /* if opcode is 0 or 1, it is considered valid because it does not
    * need any other register or mem address*/
    if (opcode == 0 || opcode == 1) {
        validity = 1;
    }

    /* check for the instruction that only uses memory address.i.e. the
    * branch opcode. if the memory address is divisible evenly by 4,
    * this is the only time that a branch instruction is considered 
    valid*/
    else if (opcode == 21 && mem_addr % 4 == 0 ) {
        validity = 1;
    }

    else{
        /* Checks to see if opcode is within a valid range and can
         * be used*/
        if (opcode < 21) {

            /*--------TESTING VALIDITY OF INSTRUCTIONS----------*/
            /* check  to see if register value is in the range
             * of [0,17], as this are the only valid registers for the
             * omniflux_core processor*/
            if (register_1 <= 17)
            {

                /* if the first register is valid, and the second
                 * register is also valid. i.e. both registers are within
                 *the range [0,17]. These instruction is considered valid.
                 *They are in the range[2 ,8] or [11,17]*/
                if (((opcode <= 8) || (opcode >= 11 && opcode <= 17))
                    && register_2 <= 17)
                {
                    validity = 1;
                }

                /* if at least the first register is valid,
                 * this means instructions such as neg or not, that need
                 * at least one register to function is also valid. This
                 * opcode value is in the range [9,10]*/
                else if (opcode == 9 || opcode == 10) {
                    validity = 1;
                }

                /* if the first register is valid, instructions
                 * that need at least the first register and the memory
                 * address is also valid. The memory address must also be
                 * evenly divisible by 4 for this instruction to stay valid.
                 * This instruction's opcode value is in the range (17, 21).
                 * */
                else if ((opcode > 17 && opcode < 20) && (mem_addr % 4 == 0))
                {
                    validity = 1;
                }

                /*since the opcode value 20, has the operation li,
                 * the minimum requirement for li to be valid is that the
                 * first register must be valid, because it does not
                 * actually access the memory address, but uses it memory
                 * address to represent a constant value.*/
                else if(opcode == 20){
                    validity = 1;
                }

                else
                    validity = 0;
            }

            /* this checks for instructions that modify their first
             * register. Such instructions become invalid if there have
             * registers that cannot be modified such as R16 and R17.
             * The opcode value for such instructions are in the range
             * of [2,10], [17,18]*/
            if (( opcode <= 10 || opcode == 17 || opcode == 18) && 
		 (register_1 == 16 || register_1 == 17)) {
                validity = 0; /*changes current state to invalid*/
            }
        }
    }
    return validity;
}
