(*
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
 *)

%include {caml/list.tom}

%typeterm any {
  implement          { dontcare (* not used *)     }
  get_fun_sym(t)     { ()       (* not used ? *)   }
  cmp_fun_sym(t1,t2) { true     (* is it used ? *) }
  get_subterm (t,n)  { ()       (* not used *)     }
}

%oplist list conc( any* ){
  fsym{ "conc" }
  make_empty() { [] }
  make_insert(e,l) { e::l }
}


let rec genere = function
    2 -> [2]
  | n -> n :: genere (n-1) ;;


exception Result of int list
let rec elim l=
try(
%match (list l) {
  conc(x*,e1,y*,e2,z*) -> {
    if `e1 mod `e2 = 0
	then raise (Result (elim(`conc (x*,y*,e2,z*)))) } 
  }; l
)with Result r -> r;;
  

let erat n = elim (genere n) ;;

let rec string_of_l l =
  List.fold_right (fun e -> fun r -> string_of_int e ^ " " ^ r) l "" ;;

let r = erat 300 in
  print_string (string_of_l r ^ "\n") ;;




