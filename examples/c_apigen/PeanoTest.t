/*
 * Copyright (c) 2004, INRIA
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met: 
 * 	- Redistributions of source code must retain the above copyright
 * 	notice, this list of conditions and the following disclaimer.  
 * 	- Redistributions in binary form must reproduce the above copyright
 * 	notice, this list of conditions and the following disclaimer in the
 * 	documentation and/or other materials provided with the distribution.
 * 	- Neither the name of the INRIA nor the names of its
 * 	contributors may be used to endorse or promote products derived from
 * 	this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

#include<stdio.h>
#include<stdlib.h>

#include "Peano.h"

%include { Peano.tom }


Nat plus(Nat t1, Nat t2) {
  %match(Nat t1, Nat t2) {
    x,consZero         -> { return x; }
    x,consSuc[pred=y]  -> { return `consSuc(plus(x,y)); }
  }
}

Nat fib(Nat t) {
  %match(Nat t) {
    consZero                           -> { return `consSuc(consZero); }
    pred@consSuc[pred=consZero]        -> { return pred; }
    consSuc[pred=pred@consSuc[pred=x]] -> { return plus(fib(x),fib(pred)); }
  }
}

void run(int loop, int n) {
  int i;
  Nat res;
  Nat N = makeNatConsZero();
  for(i=0 ; i<n ; i++) {
    N = makeNatConsSuc(N);
  }
  
  for(i=0 ; i<loop; i++) {
    res = fib(N);
  }

  ATprintf("res = %t\n",NatToTerm(res));
}

int main(int argc, char **argv) {
  ATerm bottomOfStack;

  ATinit(argc, argv, &bottomOfStack);
  initPeanoApi();

  run(10,10);
  
  return 0;
}
