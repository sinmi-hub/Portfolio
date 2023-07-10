#include <stdio.h>
#include <assert.h>
#include "omniflux_core.h"



#define PROGRAM_SIZE 15
#define START_ADDR 0
#define DATA_SEGMENT_SIZE 0

int main(void) {
  unsigned int memory[NUM_WORDS]= {0xffffffff, 0x239c0000, 0x32d80000,
                                   0x43d40000, 0x5b580000, 0x7b1c0000,
                                   0x82de0000, 0x4bc00000, 0x53400000,
                                   0xa80027e8, 0x08000000, 0xa3803810,
                                   0x93c02f10, 0x9ac03910, 0x8b560000};

  /* note we did not use init_program() to load the program in memory
   * because it would have choked due to the invalid first instruction; we
   * just used the initializer values to put the program words directly in
   * memory */

  /* decode_program, but nothing should be printed */
  assert(decode_program(memory, START_ADDR, PROGRAM_SIZE,
                     DATA_SEGMENT_SIZE) == 0);

  printf("The assertions were all extremely successful!\n");

  return 0;
}
