/*
 * This is an example on computing n-th fib number on peano arithmetics
 */
#include<stdio.h>
#include<stdlib.h>

#define ZERO 0
#define SUC 1
#define PLUS 2
#define FIB 3

struct term {
  int fs;
  int arity;
  struct term **subt;
};

struct term s_zero = {ZERO, 0, NULL};
struct term *zero = &s_zero;

struct term *suc(struct term *x) {
  struct term *res;
  res = malloc(sizeof(struct term));
  res->fs = SUC;
  res->arity = 1;
  res->subt = malloc(sizeof(struct term *));
  res->subt[0] = x;
  return(res);
}

%typeterm term {
  implement { struct term* }
  get_fun_sym(t)      { (void*)t->fs }
  cmp_fun_sym(t1,t2)  { t1 == t2 }
  get_subterm(t, n)   { t->subt[n] }
}

%op term zero {
  fsym { (void*)ZERO }
}
  
%op term suc(term) {
  fsym { (void*)SUC }
}

%op term plus(term,term) {
  fsym { PLUS }
}

%op term fib(term) {
  fsym { FIB }
}

struct term *plus(struct term *t1, struct term *t2) {
  %match(term t1, term t2) {
    x,zero()   -> { return(x); }
    x,suc(y) -> { return(suc(plus(x,y))); }
  }
}

struct term *fib(struct term *t) {
  %match(term t) {
    zero()        -> { return(suc(zero)); }
    suc(zero())   -> { return(suc(zero)); }
    suc(suc(x)) -> { return(plus(fib(x),fib(suc(x)))); }
  }
}

int count(struct term *t) {
  %match(term t) {
    zero()        -> { return 0; }
    suc(x)   -> { return 1 + count(x); }
  }
}

void printTerm(struct term *tt) {
  int i;
  static char * fsnames[] = {"zero", "suc", "plus", "fib"};
  printf("%s", fsnames[tt->fs]);
  if (tt->arity!=0) {
    printf("(");
    for(i=0; i<tt->arity; i++) {
      printTerm(tt->subt[i]);
      if (i+1 != tt->arity) printf(",");
    }
    printf(")");
  }
}

struct term *buildPeano(int n) {
  int i;
  struct term  *t;
  t = zero;
  for(i=0; i<n; i++) {
    t = suc(t);
  }
  return t;
}

static void symbolicFib( int n ) {
  printf("fib %d == ", n);
  printTerm(fib(buildPeano(n)));
  printf("\n");
  fflush(stdout);
}

void test_failed(char *category) {
  fprintf(stderr, "test %s failed!\n", category);
  exit(1);
}
#define test_assert(cat,cond) if(!(cond)) test_failed(cat)


int main() {
    /*printTerm(fib(buildPeano(10)));*/
  test_assert("fib(10)", (count(fib(buildPeano(10))) == 89) );
  return(0);
}
