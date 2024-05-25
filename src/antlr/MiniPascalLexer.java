// Generated from MiniPascal.g4 by ANTLR 4.13.0

    package antlr;
    import java.util.HashMap;
    import java.util.Scanner;
    import java.util.Map;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class MiniPascalLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.13.0", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		AND=1, BEGIN=2, BOOLEAN=3, CHAR=4, CHR=5, CONST=6, DIV=7, BREAKD=8, CONTINUED=9, 
		DO=10, DOWNTO=11, ELSE=12, END=13, FOR=14, FUNCTION=15, PROCEDURE=16, 
		IF=17, IN=18, INTEGER=19, MOD=20, NIL=21, NOT=22, OF=23, OR=24, PROGRAM=25, 
		REPEAT=26, THEN=27, TO=28, UNTIL=29, VAR=30, WHILE=31, PLUS=32, MINUS=33, 
		STAR=34, SLASH=35, ASSIGN=36, COMMA=37, SEMI=38, COLON=39, EQUAL=40, NOT_EQUAL=41, 
		LT=42, LE=43, GE=44, GT=45, ARRAY=46, LPAREN=47, RPAREN=48, LBRACK=49, 
		LBRACK2=50, RBRACK=51, RBRACK2=52, DOT=53, DOTDOT=54, LCURLY=55, RCURLY=56, 
		STRING=57, TRUE=58, FALSE=59, WRITE=60, READ=61, WS=62, COMMENT=63, IDENT=64, 
		CONSTCHAR=65, CONSTSTR=66, NUM_INT=67, NUM_REAL=68;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"AND", "BEGIN", "BOOLEAN", "CHAR", "CHR", "CONST", "DIV", "BREAKD", "CONTINUED", 
			"DO", "DOWNTO", "ELSE", "END", "FOR", "FUNCTION", "PROCEDURE", "IF", 
			"IN", "INTEGER", "MOD", "NIL", "NOT", "OF", "OR", "PROGRAM", "REPEAT", 
			"THEN", "TO", "UNTIL", "VAR", "WHILE", "PLUS", "MINUS", "STAR", "SLASH", 
			"ASSIGN", "COMMA", "SEMI", "COLON", "EQUAL", "NOT_EQUAL", "LT", "LE", 
			"GE", "GT", "ARRAY", "LPAREN", "RPAREN", "LBRACK", "LBRACK2", "RBRACK", 
			"RBRACK2", "DOT", "DOTDOT", "LCURLY", "RCURLY", "STRING", "TRUE", "FALSE", 
			"WRITE", "READ", "WS", "COMMENT", "IDENT", "CONSTCHAR", "CONSTSTR", "NUM_INT", 
			"NUM_REAL"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'AND'", "'BEGIN'", "'BOOLEAN'", "'CHAR'", "'CHR'", "'CONST'", 
			"'DIV'", "'break'", "'continue'", "'DO'", "'DOWNTO'", "'ELSE'", "'END'", 
			"'FOR'", "'FUNCTION'", "'PROCEDURE'", "'IF'", "'IN'", "'INTEGER'", "'MOD'", 
			"'NIL'", "'NOT'", "'OF'", "'OR'", "'PROGRAM'", "'REPEAT'", "'THEN'", 
			"'TO'", "'UNTIL'", "'VAR'", "'WHILE'", "'+'", "'-'", "'*'", "'/'", "':='", 
			"','", "';'", "':'", "'='", "'<>'", "'<'", "'<='", "'>='", "'>'", "'ARRAY'", 
			"'('", "')'", "'['", "'(.'", "']'", "'.)'", "'.'", "'..'", "'{'", "'}'", 
			"'STRING'", "'TRUE'", "'FALSE'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "AND", "BEGIN", "BOOLEAN", "CHAR", "CHR", "CONST", "DIV", "BREAKD", 
			"CONTINUED", "DO", "DOWNTO", "ELSE", "END", "FOR", "FUNCTION", "PROCEDURE", 
			"IF", "IN", "INTEGER", "MOD", "NIL", "NOT", "OF", "OR", "PROGRAM", "REPEAT", 
			"THEN", "TO", "UNTIL", "VAR", "WHILE", "PLUS", "MINUS", "STAR", "SLASH", 
			"ASSIGN", "COMMA", "SEMI", "COLON", "EQUAL", "NOT_EQUAL", "LT", "LE", 
			"GE", "GT", "ARRAY", "LPAREN", "RPAREN", "LBRACK", "LBRACK2", "RBRACK", 
			"RBRACK2", "DOT", "DOTDOT", "LCURLY", "RCURLY", "STRING", "TRUE", "FALSE", 
			"WRITE", "READ", "WS", "COMMENT", "IDENT", "CONSTCHAR", "CONSTSTR", "NUM_INT", 
			"NUM_REAL"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	    Map<String, Double> arithVars = new HashMap<String, Double>();
	    Map<String, Boolean> boolVars = new HashMap<String, Boolean>();
	    Scanner scanner = new Scanner(System.in);
	    // Separate the variable name list into usable names
	    public String [] parseString(String variable_list){
	        String[] values = variable_list.split("\\s*,\\s*");
	        return values;
	    }


	public MiniPascalLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "MiniPascal.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000D\u01d0\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001"+
		"\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004"+
		"\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007"+
		"\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b"+
		"\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002"+
		"\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002"+
		"\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002"+
		"\u0015\u0007\u0015\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002"+
		"\u0018\u0007\u0018\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002"+
		"\u001b\u0007\u001b\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0002"+
		"\u001e\u0007\u001e\u0002\u001f\u0007\u001f\u0002 \u0007 \u0002!\u0007"+
		"!\u0002\"\u0007\"\u0002#\u0007#\u0002$\u0007$\u0002%\u0007%\u0002&\u0007"+
		"&\u0002\'\u0007\'\u0002(\u0007(\u0002)\u0007)\u0002*\u0007*\u0002+\u0007"+
		"+\u0002,\u0007,\u0002-\u0007-\u0002.\u0007.\u0002/\u0007/\u00020\u0007"+
		"0\u00021\u00071\u00022\u00072\u00023\u00073\u00024\u00074\u00025\u0007"+
		"5\u00026\u00076\u00027\u00077\u00028\u00078\u00029\u00079\u0002:\u0007"+
		":\u0002;\u0007;\u0002<\u0007<\u0002=\u0007=\u0002>\u0007>\u0002?\u0007"+
		"?\u0002@\u0007@\u0002A\u0007A\u0002B\u0007B\u0002C\u0007C\u0001\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0001\t\u0001\t\u0001\t\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\f\u0001\f\u0001\f\u0001\f\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001"+
		"\u000e\u0001\u000e\u0001\u000e\u0001\u000f\u0001\u000f\u0001\u000f\u0001"+
		"\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001"+
		"\u000f\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0011\u0001\u0011\u0001"+
		"\u0011\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001"+
		"\u0012\u0001\u0012\u0001\u0012\u0001\u0013\u0001\u0013\u0001\u0013\u0001"+
		"\u0013\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0015\u0001"+
		"\u0015\u0001\u0015\u0001\u0015\u0001\u0016\u0001\u0016\u0001\u0016\u0001"+
		"\u0017\u0001\u0017\u0001\u0017\u0001\u0018\u0001\u0018\u0001\u0018\u0001"+
		"\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0019\u0001"+
		"\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001"+
		"\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001b\u0001"+
		"\u001b\u0001\u001b\u0001\u001c\u0001\u001c\u0001\u001c\u0001\u001c\u0001"+
		"\u001c\u0001\u001c\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001"+
		"\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001"+
		"\u001f\u0001\u001f\u0001 \u0001 \u0001!\u0001!\u0001\"\u0001\"\u0001#"+
		"\u0001#\u0001#\u0001$\u0001$\u0001%\u0001%\u0001&\u0001&\u0001\'\u0001"+
		"\'\u0001(\u0001(\u0001(\u0001)\u0001)\u0001*\u0001*\u0001*\u0001+\u0001"+
		"+\u0001+\u0001,\u0001,\u0001-\u0001-\u0001-\u0001-\u0001-\u0001-\u0001"+
		".\u0001.\u0001/\u0001/\u00010\u00010\u00011\u00011\u00011\u00012\u0001"+
		"2\u00013\u00013\u00013\u00014\u00014\u00015\u00015\u00015\u00016\u0001"+
		"6\u00017\u00017\u00018\u00018\u00018\u00018\u00018\u00018\u00018\u0001"+
		"9\u00019\u00019\u00019\u00019\u0001:\u0001:\u0001:\u0001:\u0001:\u0001"+
		":\u0001;\u0001;\u0001;\u0001;\u0001;\u0001;\u0001;\u0001;\u0001;\u0001"+
		";\u0001;\u0001;\u0003;\u018a\b;\u0001<\u0001<\u0001<\u0001<\u0001<\u0001"+
		"<\u0001<\u0001<\u0001<\u0001<\u0003<\u0196\b<\u0001=\u0001=\u0001=\u0001"+
		"=\u0001>\u0001>\u0005>\u019e\b>\n>\f>\u01a1\t>\u0001>\u0001>\u0001>\u0001"+
		">\u0001?\u0001?\u0005?\u01a9\b?\n?\f?\u01ac\t?\u0001@\u0001@\u0001@\u0001"+
		"@\u0003@\u01b2\b@\u0001@\u0001@\u0001A\u0001A\u0001A\u0001A\u0005A\u01ba"+
		"\bA\nA\fA\u01bd\tA\u0001A\u0001A\u0001B\u0004B\u01c2\bB\u000bB\fB\u01c3"+
		"\u0001C\u0004C\u01c7\bC\u000bC\fC\u01c8\u0001C\u0001C\u0004C\u01cd\bC"+
		"\u000bC\fC\u01ce\u0001\u019f\u0000D\u0001\u0001\u0003\u0002\u0005\u0003"+
		"\u0007\u0004\t\u0005\u000b\u0006\r\u0007\u000f\b\u0011\t\u0013\n\u0015"+
		"\u000b\u0017\f\u0019\r\u001b\u000e\u001d\u000f\u001f\u0010!\u0011#\u0012"+
		"%\u0013\'\u0014)\u0015+\u0016-\u0017/\u00181\u00193\u001a5\u001b7\u001c"+
		"9\u001d;\u001e=\u001f? A!C\"E#G$I%K&M\'O(Q)S*U+W,Y-[.]/_0a1c2e3g4i5k6"+
		"m7o8q9s:u;w<y={>}?\u007f@\u0081A\u0083B\u0085C\u0087D\u0001\u0000\u001a"+
		"\u0002\u0000AAaa\u0002\u0000NNnn\u0002\u0000DDdd\u0002\u0000BBbb\u0002"+
		"\u0000EEee\u0002\u0000GGgg\u0002\u0000IIii\u0002\u0000OOoo\u0002\u0000"+
		"LLll\u0002\u0000CCcc\u0002\u0000HHhh\u0002\u0000RRrr\u0002\u0000SSss\u0002"+
		"\u0000TTtt\u0002\u0000VVvv\u0002\u0000KKkk\u0002\u0000UUuu\u0002\u0000"+
		"WWww\u0002\u0000FFff\u0002\u0000PPpp\u0002\u0000MMmm\u0002\u0000YYyy\u0003"+
		"\u0000\t\n\r\r  \u0002\u0000AZaz\u0004\u000009AZ__az\u0001\u0000\'\'\u01da"+
		"\u0000\u0001\u0001\u0000\u0000\u0000\u0000\u0003\u0001\u0000\u0000\u0000"+
		"\u0000\u0005\u0001\u0000\u0000\u0000\u0000\u0007\u0001\u0000\u0000\u0000"+
		"\u0000\t\u0001\u0000\u0000\u0000\u0000\u000b\u0001\u0000\u0000\u0000\u0000"+
		"\r\u0001\u0000\u0000\u0000\u0000\u000f\u0001\u0000\u0000\u0000\u0000\u0011"+
		"\u0001\u0000\u0000\u0000\u0000\u0013\u0001\u0000\u0000\u0000\u0000\u0015"+
		"\u0001\u0000\u0000\u0000\u0000\u0017\u0001\u0000\u0000\u0000\u0000\u0019"+
		"\u0001\u0000\u0000\u0000\u0000\u001b\u0001\u0000\u0000\u0000\u0000\u001d"+
		"\u0001\u0000\u0000\u0000\u0000\u001f\u0001\u0000\u0000\u0000\u0000!\u0001"+
		"\u0000\u0000\u0000\u0000#\u0001\u0000\u0000\u0000\u0000%\u0001\u0000\u0000"+
		"\u0000\u0000\'\u0001\u0000\u0000\u0000\u0000)\u0001\u0000\u0000\u0000"+
		"\u0000+\u0001\u0000\u0000\u0000\u0000-\u0001\u0000\u0000\u0000\u0000/"+
		"\u0001\u0000\u0000\u0000\u00001\u0001\u0000\u0000\u0000\u00003\u0001\u0000"+
		"\u0000\u0000\u00005\u0001\u0000\u0000\u0000\u00007\u0001\u0000\u0000\u0000"+
		"\u00009\u0001\u0000\u0000\u0000\u0000;\u0001\u0000\u0000\u0000\u0000="+
		"\u0001\u0000\u0000\u0000\u0000?\u0001\u0000\u0000\u0000\u0000A\u0001\u0000"+
		"\u0000\u0000\u0000C\u0001\u0000\u0000\u0000\u0000E\u0001\u0000\u0000\u0000"+
		"\u0000G\u0001\u0000\u0000\u0000\u0000I\u0001\u0000\u0000\u0000\u0000K"+
		"\u0001\u0000\u0000\u0000\u0000M\u0001\u0000\u0000\u0000\u0000O\u0001\u0000"+
		"\u0000\u0000\u0000Q\u0001\u0000\u0000\u0000\u0000S\u0001\u0000\u0000\u0000"+
		"\u0000U\u0001\u0000\u0000\u0000\u0000W\u0001\u0000\u0000\u0000\u0000Y"+
		"\u0001\u0000\u0000\u0000\u0000[\u0001\u0000\u0000\u0000\u0000]\u0001\u0000"+
		"\u0000\u0000\u0000_\u0001\u0000\u0000\u0000\u0000a\u0001\u0000\u0000\u0000"+
		"\u0000c\u0001\u0000\u0000\u0000\u0000e\u0001\u0000\u0000\u0000\u0000g"+
		"\u0001\u0000\u0000\u0000\u0000i\u0001\u0000\u0000\u0000\u0000k\u0001\u0000"+
		"\u0000\u0000\u0000m\u0001\u0000\u0000\u0000\u0000o\u0001\u0000\u0000\u0000"+
		"\u0000q\u0001\u0000\u0000\u0000\u0000s\u0001\u0000\u0000\u0000\u0000u"+
		"\u0001\u0000\u0000\u0000\u0000w\u0001\u0000\u0000\u0000\u0000y\u0001\u0000"+
		"\u0000\u0000\u0000{\u0001\u0000\u0000\u0000\u0000}\u0001\u0000\u0000\u0000"+
		"\u0000\u007f\u0001\u0000\u0000\u0000\u0000\u0081\u0001\u0000\u0000\u0000"+
		"\u0000\u0083\u0001\u0000\u0000\u0000\u0000\u0085\u0001\u0000\u0000\u0000"+
		"\u0000\u0087\u0001\u0000\u0000\u0000\u0001\u0089\u0001\u0000\u0000\u0000"+
		"\u0003\u008d\u0001\u0000\u0000\u0000\u0005\u0093\u0001\u0000\u0000\u0000"+
		"\u0007\u009b\u0001\u0000\u0000\u0000\t\u00a0\u0001\u0000\u0000\u0000\u000b"+
		"\u00a4\u0001\u0000\u0000\u0000\r\u00aa\u0001\u0000\u0000\u0000\u000f\u00ae"+
		"\u0001\u0000\u0000\u0000\u0011\u00b4\u0001\u0000\u0000\u0000\u0013\u00bd"+
		"\u0001\u0000\u0000\u0000\u0015\u00c0\u0001\u0000\u0000\u0000\u0017\u00c7"+
		"\u0001\u0000\u0000\u0000\u0019\u00cc\u0001\u0000\u0000\u0000\u001b\u00d0"+
		"\u0001\u0000\u0000\u0000\u001d\u00d4\u0001\u0000\u0000\u0000\u001f\u00dd"+
		"\u0001\u0000\u0000\u0000!\u00e7\u0001\u0000\u0000\u0000#\u00ea\u0001\u0000"+
		"\u0000\u0000%\u00ed\u0001\u0000\u0000\u0000\'\u00f5\u0001\u0000\u0000"+
		"\u0000)\u00f9\u0001\u0000\u0000\u0000+\u00fd\u0001\u0000\u0000\u0000-"+
		"\u0101\u0001\u0000\u0000\u0000/\u0104\u0001\u0000\u0000\u00001\u0107\u0001"+
		"\u0000\u0000\u00003\u010f\u0001\u0000\u0000\u00005\u0116\u0001\u0000\u0000"+
		"\u00007\u011b\u0001\u0000\u0000\u00009\u011e\u0001\u0000\u0000\u0000;"+
		"\u0124\u0001\u0000\u0000\u0000=\u0128\u0001\u0000\u0000\u0000?\u012e\u0001"+
		"\u0000\u0000\u0000A\u0130\u0001\u0000\u0000\u0000C\u0132\u0001\u0000\u0000"+
		"\u0000E\u0134\u0001\u0000\u0000\u0000G\u0136\u0001\u0000\u0000\u0000I"+
		"\u0139\u0001\u0000\u0000\u0000K\u013b\u0001\u0000\u0000\u0000M\u013d\u0001"+
		"\u0000\u0000\u0000O\u013f\u0001\u0000\u0000\u0000Q\u0141\u0001\u0000\u0000"+
		"\u0000S\u0144\u0001\u0000\u0000\u0000U\u0146\u0001\u0000\u0000\u0000W"+
		"\u0149\u0001\u0000\u0000\u0000Y\u014c\u0001\u0000\u0000\u0000[\u014e\u0001"+
		"\u0000\u0000\u0000]\u0154\u0001\u0000\u0000\u0000_\u0156\u0001\u0000\u0000"+
		"\u0000a\u0158\u0001\u0000\u0000\u0000c\u015a\u0001\u0000\u0000\u0000e"+
		"\u015d\u0001\u0000\u0000\u0000g\u015f\u0001\u0000\u0000\u0000i\u0162\u0001"+
		"\u0000\u0000\u0000k\u0164\u0001\u0000\u0000\u0000m\u0167\u0001\u0000\u0000"+
		"\u0000o\u0169\u0001\u0000\u0000\u0000q\u016b\u0001\u0000\u0000\u0000s"+
		"\u0172\u0001\u0000\u0000\u0000u\u0177\u0001\u0000\u0000\u0000w\u0189\u0001"+
		"\u0000\u0000\u0000y\u0195\u0001\u0000\u0000\u0000{\u0197\u0001\u0000\u0000"+
		"\u0000}\u019b\u0001\u0000\u0000\u0000\u007f\u01a6\u0001\u0000\u0000\u0000"+
		"\u0081\u01ad\u0001\u0000\u0000\u0000\u0083\u01b5\u0001\u0000\u0000\u0000"+
		"\u0085\u01c1\u0001\u0000\u0000\u0000\u0087\u01c6\u0001\u0000\u0000\u0000"+
		"\u0089\u008a\u0007\u0000\u0000\u0000\u008a\u008b\u0007\u0001\u0000\u0000"+
		"\u008b\u008c\u0007\u0002\u0000\u0000\u008c\u0002\u0001\u0000\u0000\u0000"+
		"\u008d\u008e\u0007\u0003\u0000\u0000\u008e\u008f\u0007\u0004\u0000\u0000"+
		"\u008f\u0090\u0007\u0005\u0000\u0000\u0090\u0091\u0007\u0006\u0000\u0000"+
		"\u0091\u0092\u0007\u0001\u0000\u0000\u0092\u0004\u0001\u0000\u0000\u0000"+
		"\u0093\u0094\u0007\u0003\u0000\u0000\u0094\u0095\u0007\u0007\u0000\u0000"+
		"\u0095\u0096\u0007\u0007\u0000\u0000\u0096\u0097\u0007\b\u0000\u0000\u0097"+
		"\u0098\u0007\u0004\u0000\u0000\u0098\u0099\u0007\u0000\u0000\u0000\u0099"+
		"\u009a\u0007\u0001\u0000\u0000\u009a\u0006\u0001\u0000\u0000\u0000\u009b"+
		"\u009c\u0007\t\u0000\u0000\u009c\u009d\u0007\n\u0000\u0000\u009d\u009e"+
		"\u0007\u0000\u0000\u0000\u009e\u009f\u0007\u000b\u0000\u0000\u009f\b\u0001"+
		"\u0000\u0000\u0000\u00a0\u00a1\u0007\t\u0000\u0000\u00a1\u00a2\u0007\n"+
		"\u0000\u0000\u00a2\u00a3\u0007\u000b\u0000\u0000\u00a3\n\u0001\u0000\u0000"+
		"\u0000\u00a4\u00a5\u0007\t\u0000\u0000\u00a5\u00a6\u0007\u0007\u0000\u0000"+
		"\u00a6\u00a7\u0007\u0001\u0000\u0000\u00a7\u00a8\u0007\f\u0000\u0000\u00a8"+
		"\u00a9\u0007\r\u0000\u0000\u00a9\f\u0001\u0000\u0000\u0000\u00aa\u00ab"+
		"\u0007\u0002\u0000\u0000\u00ab\u00ac\u0007\u0006\u0000\u0000\u00ac\u00ad"+
		"\u0007\u000e\u0000\u0000\u00ad\u000e\u0001\u0000\u0000\u0000\u00ae\u00af"+
		"\u0007\u0003\u0000\u0000\u00af\u00b0\u0007\u000b\u0000\u0000\u00b0\u00b1"+
		"\u0007\u0004\u0000\u0000\u00b1\u00b2\u0007\u0000\u0000\u0000\u00b2\u00b3"+
		"\u0007\u000f\u0000\u0000\u00b3\u0010\u0001\u0000\u0000\u0000\u00b4\u00b5"+
		"\u0007\t\u0000\u0000\u00b5\u00b6\u0007\u0007\u0000\u0000\u00b6\u00b7\u0007"+
		"\u0001\u0000\u0000\u00b7\u00b8\u0007\r\u0000\u0000\u00b8\u00b9\u0007\u0006"+
		"\u0000\u0000\u00b9\u00ba\u0007\u0001\u0000\u0000\u00ba\u00bb\u0007\u0010"+
		"\u0000\u0000\u00bb\u00bc\u0007\u0004\u0000\u0000\u00bc\u0012\u0001\u0000"+
		"\u0000\u0000\u00bd\u00be\u0007\u0002\u0000\u0000\u00be\u00bf\u0007\u0007"+
		"\u0000\u0000\u00bf\u0014\u0001\u0000\u0000\u0000\u00c0\u00c1\u0007\u0002"+
		"\u0000\u0000\u00c1\u00c2\u0007\u0007\u0000\u0000\u00c2\u00c3\u0007\u0011"+
		"\u0000\u0000\u00c3\u00c4\u0007\u0001\u0000\u0000\u00c4\u00c5\u0007\r\u0000"+
		"\u0000\u00c5\u00c6\u0007\u0007\u0000\u0000\u00c6\u0016\u0001\u0000\u0000"+
		"\u0000\u00c7\u00c8\u0007\u0004\u0000\u0000\u00c8\u00c9\u0007\b\u0000\u0000"+
		"\u00c9\u00ca\u0007\f\u0000\u0000\u00ca\u00cb\u0007\u0004\u0000\u0000\u00cb"+
		"\u0018\u0001\u0000\u0000\u0000\u00cc\u00cd\u0007\u0004\u0000\u0000\u00cd"+
		"\u00ce\u0007\u0001\u0000\u0000\u00ce\u00cf\u0007\u0002\u0000\u0000\u00cf"+
		"\u001a\u0001\u0000\u0000\u0000\u00d0\u00d1\u0007\u0012\u0000\u0000\u00d1"+
		"\u00d2\u0007\u0007\u0000\u0000\u00d2\u00d3\u0007\u000b\u0000\u0000\u00d3"+
		"\u001c\u0001\u0000\u0000\u0000\u00d4\u00d5\u0007\u0012\u0000\u0000\u00d5"+
		"\u00d6\u0007\u0010\u0000\u0000\u00d6\u00d7\u0007\u0001\u0000\u0000\u00d7"+
		"\u00d8\u0007\t\u0000\u0000\u00d8\u00d9\u0007\r\u0000\u0000\u00d9\u00da"+
		"\u0007\u0006\u0000\u0000\u00da\u00db\u0007\u0007\u0000\u0000\u00db\u00dc"+
		"\u0007\u0001\u0000\u0000\u00dc\u001e\u0001\u0000\u0000\u0000\u00dd\u00de"+
		"\u0007\u0013\u0000\u0000\u00de\u00df\u0007\u000b\u0000\u0000\u00df\u00e0"+
		"\u0007\u0007\u0000\u0000\u00e0\u00e1\u0007\t\u0000\u0000\u00e1\u00e2\u0007"+
		"\u0004\u0000\u0000\u00e2\u00e3\u0007\u0002\u0000\u0000\u00e3\u00e4\u0007"+
		"\u0010\u0000\u0000\u00e4\u00e5\u0007\u000b\u0000\u0000\u00e5\u00e6\u0007"+
		"\u0004\u0000\u0000\u00e6 \u0001\u0000\u0000\u0000\u00e7\u00e8\u0007\u0006"+
		"\u0000\u0000\u00e8\u00e9\u0007\u0012\u0000\u0000\u00e9\"\u0001\u0000\u0000"+
		"\u0000\u00ea\u00eb\u0007\u0006\u0000\u0000\u00eb\u00ec\u0007\u0001\u0000"+
		"\u0000\u00ec$\u0001\u0000\u0000\u0000\u00ed\u00ee\u0007\u0006\u0000\u0000"+
		"\u00ee\u00ef\u0007\u0001\u0000\u0000\u00ef\u00f0\u0007\r\u0000\u0000\u00f0"+
		"\u00f1\u0007\u0004\u0000\u0000\u00f1\u00f2\u0007\u0005\u0000\u0000\u00f2"+
		"\u00f3\u0007\u0004\u0000\u0000\u00f3\u00f4\u0007\u000b\u0000\u0000\u00f4"+
		"&\u0001\u0000\u0000\u0000\u00f5\u00f6\u0007\u0014\u0000\u0000\u00f6\u00f7"+
		"\u0007\u0007\u0000\u0000\u00f7\u00f8\u0007\u0002\u0000\u0000\u00f8(\u0001"+
		"\u0000\u0000\u0000\u00f9\u00fa\u0007\u0001\u0000\u0000\u00fa\u00fb\u0007"+
		"\u0006\u0000\u0000\u00fb\u00fc\u0007\b\u0000\u0000\u00fc*\u0001\u0000"+
		"\u0000\u0000\u00fd\u00fe\u0007\u0001\u0000\u0000\u00fe\u00ff\u0007\u0007"+
		"\u0000\u0000\u00ff\u0100\u0007\r\u0000\u0000\u0100,\u0001\u0000\u0000"+
		"\u0000\u0101\u0102\u0007\u0007\u0000\u0000\u0102\u0103\u0007\u0012\u0000"+
		"\u0000\u0103.\u0001\u0000\u0000\u0000\u0104\u0105\u0007\u0007\u0000\u0000"+
		"\u0105\u0106\u0007\u000b\u0000\u0000\u01060\u0001\u0000\u0000\u0000\u0107"+
		"\u0108\u0007\u0013\u0000\u0000\u0108\u0109\u0007\u000b\u0000\u0000\u0109"+
		"\u010a\u0007\u0007\u0000\u0000\u010a\u010b\u0007\u0005\u0000\u0000\u010b"+
		"\u010c\u0007\u000b\u0000\u0000\u010c\u010d\u0007\u0000\u0000\u0000\u010d"+
		"\u010e\u0007\u0014\u0000\u0000\u010e2\u0001\u0000\u0000\u0000\u010f\u0110"+
		"\u0007\u000b\u0000\u0000\u0110\u0111\u0007\u0004\u0000\u0000\u0111\u0112"+
		"\u0007\u0013\u0000\u0000\u0112\u0113\u0007\u0004\u0000\u0000\u0113\u0114"+
		"\u0007\u0000\u0000\u0000\u0114\u0115\u0007\r\u0000\u0000\u01154\u0001"+
		"\u0000\u0000\u0000\u0116\u0117\u0007\r\u0000\u0000\u0117\u0118\u0007\n"+
		"\u0000\u0000\u0118\u0119\u0007\u0004\u0000\u0000\u0119\u011a\u0007\u0001"+
		"\u0000\u0000\u011a6\u0001\u0000\u0000\u0000\u011b\u011c\u0007\r\u0000"+
		"\u0000\u011c\u011d\u0007\u0007\u0000\u0000\u011d8\u0001\u0000\u0000\u0000"+
		"\u011e\u011f\u0007\u0010\u0000\u0000\u011f\u0120\u0007\u0001\u0000\u0000"+
		"\u0120\u0121\u0007\r\u0000\u0000\u0121\u0122\u0007\u0006\u0000\u0000\u0122"+
		"\u0123\u0007\b\u0000\u0000\u0123:\u0001\u0000\u0000\u0000\u0124\u0125"+
		"\u0007\u000e\u0000\u0000\u0125\u0126\u0007\u0000\u0000\u0000\u0126\u0127"+
		"\u0007\u000b\u0000\u0000\u0127<\u0001\u0000\u0000\u0000\u0128\u0129\u0007"+
		"\u0011\u0000\u0000\u0129\u012a\u0007\n\u0000\u0000\u012a\u012b\u0007\u0006"+
		"\u0000\u0000\u012b\u012c\u0007\b\u0000\u0000\u012c\u012d\u0007\u0004\u0000"+
		"\u0000\u012d>\u0001\u0000\u0000\u0000\u012e\u012f\u0005+\u0000\u0000\u012f"+
		"@\u0001\u0000\u0000\u0000\u0130\u0131\u0005-\u0000\u0000\u0131B\u0001"+
		"\u0000\u0000\u0000\u0132\u0133\u0005*\u0000\u0000\u0133D\u0001\u0000\u0000"+
		"\u0000\u0134\u0135\u0005/\u0000\u0000\u0135F\u0001\u0000\u0000\u0000\u0136"+
		"\u0137\u0005:\u0000\u0000\u0137\u0138\u0005=\u0000\u0000\u0138H\u0001"+
		"\u0000\u0000\u0000\u0139\u013a\u0005,\u0000\u0000\u013aJ\u0001\u0000\u0000"+
		"\u0000\u013b\u013c\u0005;\u0000\u0000\u013cL\u0001\u0000\u0000\u0000\u013d"+
		"\u013e\u0005:\u0000\u0000\u013eN\u0001\u0000\u0000\u0000\u013f\u0140\u0005"+
		"=\u0000\u0000\u0140P\u0001\u0000\u0000\u0000\u0141\u0142\u0005<\u0000"+
		"\u0000\u0142\u0143\u0005>\u0000\u0000\u0143R\u0001\u0000\u0000\u0000\u0144"+
		"\u0145\u0005<\u0000\u0000\u0145T\u0001\u0000\u0000\u0000\u0146\u0147\u0005"+
		"<\u0000\u0000\u0147\u0148\u0005=\u0000\u0000\u0148V\u0001\u0000\u0000"+
		"\u0000\u0149\u014a\u0005>\u0000\u0000\u014a\u014b\u0005=\u0000\u0000\u014b"+
		"X\u0001\u0000\u0000\u0000\u014c\u014d\u0005>\u0000\u0000\u014dZ\u0001"+
		"\u0000\u0000\u0000\u014e\u014f\u0007\u0000\u0000\u0000\u014f\u0150\u0007"+
		"\u000b\u0000\u0000\u0150\u0151\u0007\u000b\u0000\u0000\u0151\u0152\u0007"+
		"\u0000\u0000\u0000\u0152\u0153\u0007\u0015\u0000\u0000\u0153\\\u0001\u0000"+
		"\u0000\u0000\u0154\u0155\u0005(\u0000\u0000\u0155^\u0001\u0000\u0000\u0000"+
		"\u0156\u0157\u0005)\u0000\u0000\u0157`\u0001\u0000\u0000\u0000\u0158\u0159"+
		"\u0005[\u0000\u0000\u0159b\u0001\u0000\u0000\u0000\u015a\u015b\u0005("+
		"\u0000\u0000\u015b\u015c\u0005.\u0000\u0000\u015cd\u0001\u0000\u0000\u0000"+
		"\u015d\u015e\u0005]\u0000\u0000\u015ef\u0001\u0000\u0000\u0000\u015f\u0160"+
		"\u0005.\u0000\u0000\u0160\u0161\u0005)\u0000\u0000\u0161h\u0001\u0000"+
		"\u0000\u0000\u0162\u0163\u0005.\u0000\u0000\u0163j\u0001\u0000\u0000\u0000"+
		"\u0164\u0165\u0005.\u0000\u0000\u0165\u0166\u0005.\u0000\u0000\u0166l"+
		"\u0001\u0000\u0000\u0000\u0167\u0168\u0005{\u0000\u0000\u0168n\u0001\u0000"+
		"\u0000\u0000\u0169\u016a\u0005}\u0000\u0000\u016ap\u0001\u0000\u0000\u0000"+
		"\u016b\u016c\u0007\f\u0000\u0000\u016c\u016d\u0007\r\u0000\u0000\u016d"+
		"\u016e\u0007\u000b\u0000\u0000\u016e\u016f\u0007\u0006\u0000\u0000\u016f"+
		"\u0170\u0007\u0001\u0000\u0000\u0170\u0171\u0007\u0005\u0000\u0000\u0171"+
		"r\u0001\u0000\u0000\u0000\u0172\u0173\u0007\r\u0000\u0000\u0173\u0174"+
		"\u0007\u000b\u0000\u0000\u0174\u0175\u0007\u0010\u0000\u0000\u0175\u0176"+
		"\u0007\u0004\u0000\u0000\u0176t\u0001\u0000\u0000\u0000\u0177\u0178\u0007"+
		"\u0012\u0000\u0000\u0178\u0179\u0007\u0000\u0000\u0000\u0179\u017a\u0007"+
		"\b\u0000\u0000\u017a\u017b\u0007\f\u0000\u0000\u017b\u017c\u0007\u0004"+
		"\u0000\u0000\u017cv\u0001\u0000\u0000\u0000\u017d\u017e\u0007\u0011\u0000"+
		"\u0000\u017e\u017f\u0007\u000b\u0000\u0000\u017f\u0180\u0007\u0006\u0000"+
		"\u0000\u0180\u0181\u0007\r\u0000\u0000\u0181\u0182\u0007\u0004\u0000\u0000"+
		"\u0182\u0183\u0007\b\u0000\u0000\u0183\u018a\u0007\u0001\u0000\u0000\u0184"+
		"\u0185\u0007\u0011\u0000\u0000\u0185\u0186\u0007\u000b\u0000\u0000\u0186"+
		"\u0187\u0007\u0006\u0000\u0000\u0187\u0188\u0007\r\u0000\u0000\u0188\u018a"+
		"\u0007\u0004\u0000\u0000\u0189\u017d\u0001\u0000\u0000\u0000\u0189\u0184"+
		"\u0001\u0000\u0000\u0000\u018ax\u0001\u0000\u0000\u0000\u018b\u018c\u0007"+
		"\u000b\u0000\u0000\u018c\u018d\u0007\u0004\u0000\u0000\u018d\u018e\u0007"+
		"\u0000\u0000\u0000\u018e\u0196\u0007\u0002\u0000\u0000\u018f\u0190\u0007"+
		"\u000b\u0000\u0000\u0190\u0191\u0007\u0004\u0000\u0000\u0191\u0192\u0007"+
		"\u0000\u0000\u0000\u0192\u0193\u0007\u0002\u0000\u0000\u0193\u0194\u0007"+
		"\b\u0000\u0000\u0194\u0196\u0007\u0001\u0000\u0000\u0195\u018b\u0001\u0000"+
		"\u0000\u0000\u0195\u018f\u0001\u0000\u0000\u0000\u0196z\u0001\u0000\u0000"+
		"\u0000\u0197\u0198\u0007\u0016\u0000\u0000\u0198\u0199\u0001\u0000\u0000"+
		"\u0000\u0199\u019a\u0006=\u0000\u0000\u019a|\u0001\u0000\u0000\u0000\u019b"+
		"\u019f\u0005{\u0000\u0000\u019c\u019e\t\u0000\u0000\u0000\u019d\u019c"+
		"\u0001\u0000\u0000\u0000\u019e\u01a1\u0001\u0000\u0000\u0000\u019f\u01a0"+
		"\u0001\u0000\u0000\u0000\u019f\u019d\u0001\u0000\u0000\u0000\u01a0\u01a2"+
		"\u0001\u0000\u0000\u0000\u01a1\u019f\u0001\u0000\u0000\u0000\u01a2\u01a3"+
		"\u0005}\u0000\u0000\u01a3\u01a4\u0001\u0000\u0000\u0000\u01a4\u01a5\u0006"+
		">\u0000\u0000\u01a5~\u0001\u0000\u0000\u0000\u01a6\u01aa\u0007\u0017\u0000"+
		"\u0000\u01a7\u01a9\u0007\u0018\u0000\u0000\u01a8\u01a7\u0001\u0000\u0000"+
		"\u0000\u01a9\u01ac\u0001\u0000\u0000\u0000\u01aa\u01a8\u0001\u0000\u0000"+
		"\u0000\u01aa\u01ab\u0001\u0000\u0000\u0000\u01ab\u0080\u0001\u0000\u0000"+
		"\u0000\u01ac\u01aa\u0001\u0000\u0000\u0000\u01ad\u01b1\u0005\'\u0000\u0000"+
		"\u01ae\u01af\u0005\'\u0000\u0000\u01af\u01b2\u0005\'\u0000\u0000\u01b0"+
		"\u01b2\b\u0019\u0000\u0000\u01b1\u01ae\u0001\u0000\u0000\u0000\u01b1\u01b0"+
		"\u0001\u0000\u0000\u0000\u01b1\u01b2\u0001\u0000\u0000\u0000\u01b2\u01b3"+
		"\u0001\u0000\u0000\u0000\u01b3\u01b4\u0005\'\u0000\u0000\u01b4\u0082\u0001"+
		"\u0000\u0000\u0000\u01b5\u01bb\u0005\'\u0000\u0000\u01b6\u01b7\u0005\'"+
		"\u0000\u0000\u01b7\u01ba\u0005\'\u0000\u0000\u01b8\u01ba\b\u0019\u0000"+
		"\u0000\u01b9\u01b6\u0001\u0000\u0000\u0000\u01b9\u01b8\u0001\u0000\u0000"+
		"\u0000\u01ba\u01bd\u0001\u0000\u0000\u0000\u01bb\u01b9\u0001\u0000\u0000"+
		"\u0000\u01bb\u01bc\u0001\u0000\u0000\u0000\u01bc\u01be\u0001\u0000\u0000"+
		"\u0000\u01bd\u01bb\u0001\u0000\u0000\u0000\u01be\u01bf\u0005\'\u0000\u0000"+
		"\u01bf\u0084\u0001\u0000\u0000\u0000\u01c0\u01c2\u000209\u0000\u01c1\u01c0"+
		"\u0001\u0000\u0000\u0000\u01c2\u01c3\u0001\u0000\u0000\u0000\u01c3\u01c1"+
		"\u0001\u0000\u0000\u0000\u01c3\u01c4\u0001\u0000\u0000\u0000\u01c4\u0086"+
		"\u0001\u0000\u0000\u0000\u01c5\u01c7\u000209\u0000\u01c6\u01c5\u0001\u0000"+
		"\u0000\u0000\u01c7\u01c8\u0001\u0000\u0000\u0000\u01c8\u01c6\u0001\u0000"+
		"\u0000\u0000\u01c8\u01c9\u0001\u0000\u0000\u0000\u01c9\u01ca\u0001\u0000"+
		"\u0000\u0000\u01ca\u01cc\u0005.\u0000\u0000\u01cb\u01cd\u000209\u0000"+
		"\u01cc\u01cb\u0001\u0000\u0000\u0000\u01cd\u01ce\u0001\u0000\u0000\u0000"+
		"\u01ce\u01cc\u0001\u0000\u0000\u0000\u01ce\u01cf\u0001\u0000\u0000\u0000"+
		"\u01cf\u0088\u0001\u0000\u0000\u0000\u000b\u0000\u0189\u0195\u019f\u01aa"+
		"\u01b1\u01b9\u01bb\u01c3\u01c8\u01ce\u0001\u0006\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}