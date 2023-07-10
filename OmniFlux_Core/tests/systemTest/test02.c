#include <stdio.h>
#include "omniflux_core.h"

#define NUM_INSTRUCTIONS 5

int main(void) {
  unsigned int instructions[NUM_INSTRUCTIONS]= {0x4bc00000, 0x53400000,
                                                0xa80027e8, 0x08000000,
                                                0x00000000};
  int i;

  for (i= 0; i < NUM_INSTRUCTIONS; i++)
    print_instructions(instructions[i]);

  return 0;
}
