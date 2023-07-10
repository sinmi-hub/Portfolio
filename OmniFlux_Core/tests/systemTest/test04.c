#include <stdio.h>
#include <assert.h>
#include "omniflux_core.h"


int main(void) {
  /* this instruction is valid */
  assert(print_instructions(0x0a960000) == 1);

  /* but this one is invalid so it should not print anything */
  assert(print_instructions(0xffffffff) == 0);

  printf("The assertions were all extremely successful!\n");

  return 0;
}
