#include <stdio.h>
#include <assert.h>
#include "omniflux_core.h"


#define PROGRAM_SIZE 12
#define START_ADDR 20
#define DATA_SEGMENT_SIZE 0

int main(void) {
  unsigned int memory[NUM_WORDS]= {0};
  unsigned int program[PROGRAM_SIZE]= {0x12960000, 0x239c0000, 0x32d80000,
                                       0x43d40000, 0x5b580000, 0x7b1c0000,
                                       0x82de0000, 0x4bc00000, 0x53400000,
                                       0xa80027e8, 0x08000000, 0x00000000};
  int i, j;

  /* Load the program into the memory starting at address 20 (which is a
   * byte address, not an array subscript).  The program occupies 12 total
   * words and of that, its data segment is 0 words large.
   */
  assert(init_program(memory, program, START_ADDR, PROGRAM_SIZE,
                      DATA_SEGMENT_SIZE) == 1);

  /* first verify that the memory before the program wasn't changed */
  for (i= 0; i < START_ADDR / 4; i++)
    assert(memory[i] == 0);

  /* next verify that the program was loaded into the right part of memory */
  for (i= START_ADDR / 4, j= 0 ; i < START_ADDR / 4 + PROGRAM_SIZE; i++, j++)
    assert(memory[i] == program[j]);

  /* lastly verify that the memory after the program also wasn't changed */
  for (i= START_ADDR / 4 + PROGRAM_SIZE; i < NUM_WORDS / 4; i++)
    assert(memory[i] == 0);

  printf("The assertions were all extremely successful!\n");

  return 0;
}
