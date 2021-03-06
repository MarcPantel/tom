grammar IptablesListParser;
options {
  output=AST;
  ASTLabelType=Tree;
  tokenVocab=AstTokens;
}

@header {
	package iptables;
}
@lexer::header {
	package iptables;
}

file :
	(block)* EOF
		-> ^(FirewallRulesIptablesList ^(IptablesListBlocks (block)*));

block:	'Chain' target '(policy' action ')'
	'target' 'prot' 'opt' 'source' 'destination'
	(rule)* { String str = $rule.text; } -> ^(
		IptablesListBlock target action ^(IptablesListRules (rule)*) STRING[str]
	);

rule:	action proto oopt a1=address a2=address opts { String str = $rule.text; } -> ^(
		IptablesListRule action proto $a1 $a2 opts STRING[str]
	);

action :
	'ACCEPT'	-> ^(Accept)
	| 'DROP'	-> ^(Drop)
	| 'REJECT'	-> ^(Reject)
	| 'LOG'		-> ^(Log)
	;

proto :
	'all' 	-> ^(ProtoAny)
	| 'tcp' -> ^(TCP)
	| 'udp' -> ^(UDP)
	| 'ip4' -> ^(IPv4)
	| 'ip6' -> ^(IPv6)
	| 'icmp'-> ^(ICMP)
	| 'eth' -> ^(Ethernet)
	;

target :
	'INPUT'		-> ^(In)
	| 'OUTPUT'	-> ^(Out)
	| 'FORWARD'	-> ^(Forward)
	;

address	: 'anywhere' 	-> ^(AddrAnyRaw)
	| 'localhost'	-> ^(AddrStringDotDecimal4 'localhost')
	| IPV4DOTDEC 	-> ^(AddrStringDotDecimal4 IPV4DOTDEC)
	| IPV4CIDR 	-> ^(AddrStringCIDR4 IPV4CIDR)
	| IPV6HEX	-> ^(AddrStringHexadecimal6 IPV6HEX)
	| IPV6CIDR	-> ^(AddrStringCIDR6 IPV6CIDR)
	;

oopt: 	'--';

port:	'spt:' INT	-> ^(IptablesListPortSrc INT)
	| 'dpt:' INT	-> ^(IptablesListPortDest INT)
	;

state : 'NEW'		-> ^(New)
	| 'RELATED'	-> ^(Related)
	| 'ESTABLISHED'	-> ^(Established)
	| 'INVALID'	-> ^(Invalid)
	;

states:	state stateIter* -> ^(States state stateIter*);

stateIter : ',' state -> ^(state);

opts:	opt* -> ^(IptablesListOptions opt*);

opt:	proto port		-> ^(port)
	| 'state' states	-> ^(IptablesListStates states)
	| STRING		-> ^(UnknownOption STRING)
	;

INT : ('0'..'9')+ ;
INTDOT		: (INT '.');
INTSTARDOT	: ((INT|'*') '.');
IPV4DOTDEC	: (b+=INTSTARDOT)+ { $b.size() <= 3 }? (INT|'*');
IPV4CIDR	: (b+=INTDOT)+ { $b.size() <= 3 }? INT '/' INT;
HEX2COLON	: (('0'..'9') ('0'..'9') ('0'..'9') ('0'..'9') ':');
IPV6HEX		: (b+=HEX2COLON)+ { $b.size() <= 7 }? (('0'..'9') ('0'..'9') ('0'..'9') ('0'..'9'));
IPV6CIDR 	: IPV6HEX '/' INT;

ESC : '\\' ( 'n'| 'r'| 't'| 'b'| 'f'| '"'| '\''| '\\') ;
STRING : '"' (ESC|~('"'|'\\'|'\n'|'\r'))* '"' ;
ID : ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'0'..'9')* ('*')?;
WS : (' '|'\t'|'\n')+ { $channel=HIDDEN; } ;

SLCOMMENT : '//' (~('\n'|'\r'))* ('\n'|'\r'('\n')?)? { $channel=HIDDEN; } ;
