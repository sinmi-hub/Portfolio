#include <stdio.h>
#include "omniflux_core.h"



#define NUM_INSTRUCTIONS 3

int main(void) {
  unsigned int instructions[NUM_INSTRUCTIONS]= {0xa3803810, 0x93c02f10,
                                                0x9ac03910};
  int i;

  for (i= 0; i < NUM_INSTRUCTIONS; i++)
    print_instructions(instructions[i]);

  return 0;
}
