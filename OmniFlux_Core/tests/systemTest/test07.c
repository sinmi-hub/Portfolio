#include <stdio.h>
#include <assert.h>
#include "omniflux_core.h"


#define PROGRAM_SIZE 12
#define START_ADDR 0x100000
#define DATA_SEGMENT_SIZE 0

int main(void) {
  unsigned int memory[NUM_WORDS]= {0};
  unsigned int program[PROGRAM_SIZE]= {0x12960000, 0x239c0000, 0x32d80000,
                                       0x43d40000, 0x5b580000, 0x7b1c0000,
                                       0x82de0000, 0x4bc00000, 0x53400000,
                                       0xa80027e8, 0x08000000, 0x00000000};
  int i;

  /* load the program represented by the array program into the array memory
   * starting at address 0x100000, which sadly is an invalid MAD Raisin
   * address
   */
  assert(init_program(memory, program, START_ADDR, PROGRAM_SIZE,
                      DATA_SEGMENT_SIZE) == 0);

  /* verify that none of the memory was changed */
  for (i= 0; i < NUM_WORDS / 4; i++)
    assert(memory[i] == 0);

  printf("The assertions were all extremely successful!\n");

  return 0;
}
