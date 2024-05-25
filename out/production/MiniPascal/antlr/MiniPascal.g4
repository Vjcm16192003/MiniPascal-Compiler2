grammar MiniPascal;

@header{
    package antlr;
    import java.util.HashMap;
    import java.util.Scanner;
    import java.util.Map;
}

@members {
    Map<String, Double> arithVars = new HashMap<String, Double>();
    Map<String, Boolean> boolVars = new HashMap<String, Boolean>();
    Scanner scanner = new Scanner(System.in);
    // Separate the variable name list into usable names
    public String [] parseString(String variable_list){
        String[] values = variable_list.split("\\s*,\\s*");
        return values;
    }
}

options { caseInsensitive = true; }

program
   : programHeading block DOT EOF
   ;

programHeading
   : PROGRAM identifier SEMI
   ;

identifier
   : IDENT
   ;

block
   : (constantDefinitionPart | variableDeclarationPart | functionDeclarationPart | procedureDeclarationPart)* compoundStatement
   ;

label
   : unsignedInteger
   ;

constantDefinitionPart
   : CONST (constantDefinition SEMI) +
   ;

constantDefinition
   : identifier EQUAL constant
   ;

constantChr
   : CHR LPAREN unsignedInteger RPAREN
   ;

constant
   : unsignedNumber
   | sign unsignedNumber
   | identifier
   | sign
   | character
   | string
   | constantChr
   ;

unsignedNumber
   : unsignedInteger
   ;

unsignedInteger
   : NUM_INT
   ;

sign
   : PLUS
   | MINUS
   ;

bool_
   : TRUE
   | FALSE
   ;

string
   : CONSTSTR
   ;

character
   : CONSTCHAR
   ;

type_
   : simpleType
   | structuredType
   ;

structuredType
   : unpackedStructuredType
   ;

unpackedStructuredType
   : arrayType
   ;

arrayType
   : ARRAY LBRACK typeList RBRACK OF componentType
   ;

typeList
   : indexType
   ;

indexType
   : simpleType
   ;

componentType
   : type_
   ;

simpleType
   : scalarType                                         #scalar
   | subrangeType                                       #subType
   | typeIdentifier                                     #identType
   | stringtype                                         #sType
   ;

scalarType
   : LPAREN identifier RPAREN
   ;

subrangeType
   : constant DOTDOT constant
   ;

typeIdentifier
   : (CHAR | BOOLEAN | INTEGER | STRING)
   ;

stringtype
   : STRING LBRACK (identifier | unsignedNumber) RBRACK
   ;

variableDeclarationPart
   : VAR variableDeclaration (SEMI variableDeclaration)* SEMI
   ;

variableDeclaration
   : identifierList COLON type_
   ;

functionDeclarationPart
   : mainFunctionDeclaration SEMI
   ;

procedureDeclarationPart
   : mainProcedureDeclaration SEMI
   ;


mainFunctionDeclaration
   : functionDeclaration
   ;

mainProcedureDeclaration
   : procedureDeclaration
   ;

formalParameterList
   : LPAREN formalParameterSection (SEMI formalParameterSection)* RPAREN
   ;

formalParameterSection
   : parameterGroup
   | VAR parameterGroup
   | FUNCTION parameterGroup
   ;

parameterGroup
   : identifierList COLON typeIdentifier
   ;

identifierList
   : identifier (COMMA identifier)*
   ;

functionDeclaration
   : FUNCTION identifier ( (formalParameterList)?| (LPAREN RPAREN)) COLON resultType SEMI block
   ;

procedureDeclaration
   : PROCEDURE identifier (formalParameterList)? SEMI block
   ;

resultType
   : typeIdentifier
   ;

statement
   : label COLON unlabelledStatement
   | unlabelledStatement
   ;

unlabelledStatement
   : simpleStatement
   | structuredStatement
   ;

simpleStatement
   : assignmentStatement
   | procedureStatement
   | emptyStatement_
   ;

assignmentStatement
   : variable ASSIGN expression
   ;

variable
   : (identifier) (LBRACK expression  RBRACK | LBRACK2 expression  RBRACK2 | DOT identifier)*
   ;

expression
   : simpleExpression (relationaloperator expression)?
   ;

relationaloperator
   : EQUAL
   | NOT_EQUAL
   | LT
   | LE
   | GE
   | GT
   | IN
   ;

simpleExpression
   : term (additiveoperator simpleExpression)?
   ;

additiveoperator
   : PLUS
   | MINUS
   | OR
   ;

term
   : signedFactor (multiplicativeoperator term)?
   ;

multiplicativeoperator
   : STAR
   | SLASH
   | DIV
   | MOD
   | AND
   ;

signedFactor
   : (type=(PLUS | MINUS))? factor
   ;

factor
   : variable                                                           #factVar
   | LPAREN expression RPAREN                                           #parExpr
   | functionDesignator                                                 #funcDesignator
   | unsignedConstant                                                   #unsignedConst
   | NOT factor                                                         #notFactor
   | bool_                                                              #factBool
   ;

unsignedConstant
   : unsignedNumber
   | constantChr
   | character
   | string
   | NIL
   ;

functionDesignator
   : identifier LPAREN parameterList RPAREN
   ;

parameterList
   : actualParameter (COMMA actualParameter)*
   ;

procedureStatement
   : write                                                              #writer
   | read                                                               #reader
   | identifier (LPAREN parameterList RPAREN)?                          #procStatement
   ;

write
   : WRITE (LPAREN writeContent writeVar RPAREN)
   ;

writeContent
    : (CONSTCHAR|CONSTSTR|NUM_INT|variable)
    ;

writeVar
    : (COMMA variable)?
    ;


read:
   READ (LPAREN identifier RPAREN)
   ;

actualParameter
   : expression parameterwidth*
   ;

parameterwidth
   : COLON expression
   ;

emptyStatement_
   :
   ;

structuredStatement
   : compoundStatement
   | conditionalStatement
   | repetetiveStatement
   ;

compoundStatement
   : BEGIN statements END
   ;

statements
   : statement (SEMI statement)*
   ;

conditionalStatement
   : ifStatement
   ;

ifStatement
   : IF expression THEN statement (: ELSE statement)?
   ;

repetetiveStatement
   : whileStatement
   | repeatStatement
   | forStatement
   ;

whileStatement
   : WHILE expression DO statement
   ;

repeatStatement
   : REPEAT statements UNTIL expression
   ;

forStatement
   : FOR identifier ASSIGN forList DO statement
   ;

forList
   : initialValue type=(TO | DOWNTO) finalValue
   ;

breakd: BREAKD SEMI;

continued: CONTINUED SEMI;

initialValue
   : expression
   ;

finalValue
   : expression
   ;

AND
   : 'AND'
   ;

BEGIN
   : 'BEGIN'
   ;

BOOLEAN
   : 'BOOLEAN'
   ;

CHAR
   : 'CHAR'
   ;

CHR
   : 'CHR'
   ;

CONST
   : 'CONST'
   ;

DIV
   : 'DIV'
   ;

BREAKD
   : 'break'
   ;

CONTINUED
   : 'continue'
   ;

DO
   : 'DO'
   ;

DOWNTO
   : 'DOWNTO'
   ;

ELSE
   : 'ELSE'
   ;

END
   : 'END'
   ;

FOR
   : 'FOR'
   ;

FUNCTION
   : 'FUNCTION'
   ;

PROCEDURE
   : 'PROCEDURE'
   ;

IF
   : 'IF'
   ;

IN
   : 'IN'
   ;

INTEGER
   : 'INTEGER'
   ;

MOD
   : 'MOD'
   ;

NIL
   : 'NIL'
   ;

NOT
   : 'NOT'
   ;

OF
   : 'OF'
   ;

OR
   : 'OR'
   ;

PROGRAM
   : 'PROGRAM'
   ;

REPEAT
   : 'REPEAT'
   ;

THEN
   : 'THEN'
   ;

TO
   : 'TO'
   ;

UNTIL
   : 'UNTIL'
   ;

VAR
   : 'VAR'
   ;

WHILE
   : 'WHILE'
   ;

PLUS
   : '+'
   ;

MINUS
   : '-'
   ;

STAR
   : '*'
   ;

SLASH
   : '/'
   ;

ASSIGN
   : ':='
   ;

COMMA
   : ','
   ;

SEMI
   : ';'
   ;

COLON
   : ':'
   ;

EQUAL
   : '='
   ;

NOT_EQUAL
   : '<>'
   ;

LT
   : '<'
   ;

LE
   : '<='
   ;

GE
   : '>='
   ;

GT
   : '>'
   ;

ARRAY
   : 'ARRAY'
   ;

LPAREN
   : '('
   ;

RPAREN
   : ')'
   ;

LBRACK
   : '['
   ;

LBRACK2
   : '(.'
   ;

RBRACK
   : ']'
   ;

RBRACK2
   : '.)'
   ;

DOT
   : '.'
   ;

DOTDOT
   : '..'
   ;

LCURLY
   : '{'
   ;

RCURLY
   : '}'
   ;

STRING
   : 'STRING'
   ;

TRUE
   : 'TRUE'
   ;

FALSE
   : 'FALSE'
   ;

WRITE
   : 'writeln'
   | 'write'
   ;

READ
   : 'read'
   | 'readln'
   ;

WS
   : [ \t\r\n] -> skip
   ;

COMMENT
   : '{' .*? '}' -> skip
   ;


IDENT
   : ('A' .. 'Z') ('A' .. 'Z' | '0' .. '9' | '_')*
   ;

CONSTCHAR
    : '\'' ('\'\'' | ~ ('\''))? '\''
    ;

CONSTSTR
   : '\'' ('\'\'' | ~ ('\''))* '\''
   ;

NUM_INT
   : ('0' .. '9') +
   ;

NUM_REAL
   : ('0' .. '9') + ('.' ('0' .. '9') + )
   ;