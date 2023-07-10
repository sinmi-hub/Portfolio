#include <stdio.h>
#include <assert.h>
#include "omniflux_core.h"



#define PROGRAM_SIZE 12
#define START_ADDR 0
#define DATA_SEGMENT_SIZE 0

int main(void) {
  unsigned int memory[NUM_WORDS]= {0};
  unsigned int program[PROGRAM_SIZE]= {0x12960000, 0x239c0000, 0x32d80000,
                                       0x43d40000, 0x5b580000, 0x7b1c0000,
                                       0x82de0000, 0x4bc00000, 0x53400000,
                                       0xa80027e8, 0x08000000, 0x00000000};
  int i;

  /* Load the program represented by the array "program" into array "memory"
   * starting at address 0.  The program occupies 12 total words and of
   * that, its data segment is 0 words large. */
  assert(init_program(memory, program, START_ADDR, PROGRAM_SIZE,
                      DATA_SEGMENT_SIZE) == 1);

  /* first verify that the program was loaded into the right part of memory */
  for (i= 0 ; i < PROGRAM_SIZE; i++)
    assert(memory[i] == program[i]);
  
  /* now also verify that the rest of memory wasn't changed */
  for (i= PROGRAM_SIZE; i < NUM_WORDS; i++)
    assert(memory[i] == 0);

  printf("The assertions were all extremely successful!\n");

  return 0;
}
