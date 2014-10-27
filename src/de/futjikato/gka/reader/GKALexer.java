// Generated from /Users/moritzspindelhirn/HAW-GPA-Core/antlr/GKA.g4 by ANTLR 4.1
package de.futjikato.gka.reader;

      import java.util.List;
      import java.util.ArrayList;
      import de.futjikato.gka.Edge;
      import de.futjikato.gka.GraphFactory;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class GKALexer extends Lexer {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		LETTER=1, DIGIT=2, DIRECTED_EDGE=3, UNDIRECTED_EDGE=4, NAME_BORDER=5, 
		WEIGHT_BORDER=6, COMM_END=7, WS=8;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"LETTER", "DIGIT", "DIRECTED_EDGE", "'--'", "NAME_BORDER", "':'", "';'", 
		"WS"
	};
	public static final String[] ruleNames = {
		"LETTER", "DIGIT", "DIRECTED_EDGE", "UNDIRECTED_EDGE", "NAME_BORDER", 
		"WEIGHT_BORDER", "COMM_END", "WS"
	};


	          
	          private GraphFactory graphFactory = new GraphFactory();
	          
	          public GraphFactory getGraphFactory() {
	              return graphFactory;
	          }
	          


	public GKALexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "GKA.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 7: WS_action((RuleContext)_localctx, actionIndex); break;
		}
	}
	private void WS_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0: skip();  break;
		}
	}

	public static final String _serializedATN =
		"\3\uacf5\uee8c\u4f5d\u8b0d\u4a45\u78bd\u1b2f\u3378\2\n*\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\3\2\3\2\3\3\3\3"+
		"\3\4\3\4\3\4\3\4\5\4\34\n\4\3\5\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3"+
		"\t\3\t\3\t\2\n\3\3\1\5\4\1\7\5\1\t\6\1\13\7\1\r\b\1\17\t\1\21\n\2\3\2"+
		"\4\13\2C\\c|\u00c6\u00c6\u00d8\u00d8\u00de\u00de\u00e1\u00e1\u00e6\u00e6"+
		"\u00f8\u00f8\u00fe\u00fe\5\2\13\f\17\17\"\"*\2\3\3\2\2\2\2\5\3\2\2\2\2"+
		"\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2"+
		"\2\2\3\23\3\2\2\2\5\25\3\2\2\2\7\33\3\2\2\2\t\35\3\2\2\2\13 \3\2\2\2\r"+
		"\"\3\2\2\2\17$\3\2\2\2\21&\3\2\2\2\23\24\t\2\2\2\24\4\3\2\2\2\25\26\4"+
		"\62;\2\26\6\3\2\2\2\27\30\7/\2\2\30\34\7@\2\2\31\32\7>\2\2\32\34\7/\2"+
		"\2\33\27\3\2\2\2\33\31\3\2\2\2\34\b\3\2\2\2\35\36\7/\2\2\36\37\7/\2\2"+
		"\37\n\3\2\2\2 !\4*+\2!\f\3\2\2\2\"#\7<\2\2#\16\3\2\2\2$%\7=\2\2%\20\3"+
		"\2\2\2&\'\t\3\2\2\'(\3\2\2\2()\b\t\2\2)\22\3\2\2\2\4\2\33";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}