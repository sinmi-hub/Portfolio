#include <stdio.h>
#include "omniflux_core.h"


#define NUM_INSTRUCTIONS 7

int main(void) {
  unsigned int instructions[NUM_INSTRUCTIONS]= {0x12960000, 0x239c0000,
                                                0x32d80000, 0x43d40000,
                                                0x5b580000, 0x7b1c0000,
                                                0x82de0000};
  int i;

  for (i= 0; i < NUM_INSTRUCTIONS; i++)
    print_instructions(instructions[i]);

  return 0;
}
