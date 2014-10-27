// Generated from /Users/moritzspindelhirn/HAW-GPA-Core/antlr/GKA.g4 by ANTLR 4.1
package de.futjikato.gka.reader;

      import java.util.List;
      import java.util.ArrayList;
      import de.futjikato.gka.Edge;
      import de.futjikato.gka.GrapgFactory;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class GKAParser extends Parser {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		LETTER=1, DIGIT=2, DIRECTED_EDGE=3, UNDIRECTED_EDGE=4, NAME_BORDER=5, 
		WEIGHT_BORDER=6, COMM_END=7, WS=8;
	public static final String[] tokenNames = {
		"<INVALID>", "LETTER", "DIGIT", "DIRECTED_EDGE", "'--'", "NAME_BORDER", 
		"':'", "';'", "WS"
	};
	public static final int
		RULE_prog = 0, RULE_edge = 1, RULE_conn = 2, RULE_edge_name = 3, RULE_edge_weight = 4, 
		RULE_weight = 5, RULE_node = 6, RULE_name = 7;
	public static final String[] ruleNames = {
		"prog", "edge", "conn", "edge_name", "edge_weight", "weight", "node", 
		"name"
	};

	@Override
	public String getGrammarFileName() { return "GKA.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public ATN getATN() { return _ATN; }


	          
	          private GrapgFactory graphFactory = new GrapgFactory();
	          
	          public GrapgFactory getGraphFactory() {
	              return graphFactory;
	          }
	          

	public GKAParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ProgContext extends ParserRuleContext {
		public List<EdgeContext> edge() {
			return getRuleContexts(EdgeContext.class);
		}
		public EdgeContext edge(int i) {
			return getRuleContext(EdgeContext.class,i);
		}
		public ProgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prog; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GKAListener ) ((GKAListener)listener).enterProg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GKAListener ) ((GKAListener)listener).exitProg(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GKAVisitor ) return ((GKAVisitor<? extends T>)visitor).visitProg(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgContext prog() throws RecognitionException {
		ProgContext _localctx = new ProgContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_prog);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(17); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(16); edge();
				}
				}
				setState(19); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==LETTER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EdgeContext extends ParserRuleContext {
		public NodeContext a;
		public ConnContext c;
		public NodeContext b;
		public Edge_nameContext n;
		public Edge_weightContext w;
		public ConnContext conn() {
			return getRuleContext(ConnContext.class,0);
		}
		public Edge_nameContext edge_name() {
			return getRuleContext(Edge_nameContext.class,0);
		}
		public Edge_weightContext edge_weight() {
			return getRuleContext(Edge_weightContext.class,0);
		}
		public TerminalNode COMM_END() { return getToken(GKAParser.COMM_END, 0); }
		public NodeContext node(int i) {
			return getRuleContext(NodeContext.class,i);
		}
		public List<NodeContext> node() {
			return getRuleContexts(NodeContext.class);
		}
		public EdgeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_edge; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GKAListener ) ((GKAListener)listener).enterEdge(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GKAListener ) ((GKAListener)listener).exitEdge(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GKAVisitor ) return ((GKAVisitor<? extends T>)visitor).visitEdge(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EdgeContext edge() throws RecognitionException {
		EdgeContext _localctx = new EdgeContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_edge);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(21); ((EdgeContext)_localctx).a = node();
			setState(30);
			_la = _input.LA(1);
			if (_la==DIRECTED_EDGE || _la==UNDIRECTED_EDGE) {
				{
				setState(22); ((EdgeContext)_localctx).c = conn();
				setState(23); ((EdgeContext)_localctx).b = node();
				setState(25);
				_la = _input.LA(1);
				if (_la==NAME_BORDER) {
					{
					setState(24); ((EdgeContext)_localctx).n = edge_name();
					}
				}

				setState(28);
				_la = _input.LA(1);
				if (_la==WEIGHT_BORDER) {
					{
					setState(27); ((EdgeContext)_localctx).w = edge_weight();
					}
				}

				}
			}

			setState(32); match(COMM_END);

			                    Edge edge = new Edge();
			                    edge.setNodeA((((EdgeContext)_localctx).a!=null?_input.getText(((EdgeContext)_localctx).a.start,((EdgeContext)_localctx).a.stop):null));
			                    if((((EdgeContext)_localctx).b!=null?_input.getText(((EdgeContext)_localctx).b.start,((EdgeContext)_localctx).b.stop):null) != null) {
			                        edge.setNodeB((((EdgeContext)_localctx).b!=null?_input.getText(((EdgeContext)_localctx).b.start,((EdgeContext)_localctx).b.stop):null));
			                    }
			                    if((((EdgeContext)_localctx).n!=null?_input.getText(((EdgeContext)_localctx).n.start,((EdgeContext)_localctx).n.stop):null) != null) {
			                        edge.setName((((EdgeContext)_localctx).b!=null?_input.getText(((EdgeContext)_localctx).b.start,((EdgeContext)_localctx).b.stop):null));
			                    }
			                    if((((EdgeContext)_localctx).w!=null?_input.getText(((EdgeContext)_localctx).w.start,((EdgeContext)_localctx).w.stop):null) != null) {
			                        edge.setWeight(Integer.valueOf(((EdgeContext)_localctx).w.retVal));
			                    }
			                    if((((EdgeContext)_localctx).c!=null?_input.getText(((EdgeContext)_localctx).c.start,((EdgeContext)_localctx).c.stop):null) != null) {
			                        Edge.DirectionType dt = Edge.DirectionType.getBySymbol((((EdgeContext)_localctx).c!=null?_input.getText(((EdgeContext)_localctx).c.start,((EdgeContext)_localctx).c.stop):null));
			                        if(dt != null) {
			                            edge.setType(dt);
			                        }
			                    }
			                    
			                    graphFactory.addEdge(edge);
			                
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConnContext extends ParserRuleContext {
		public TerminalNode UNDIRECTED_EDGE() { return getToken(GKAParser.UNDIRECTED_EDGE, 0); }
		public TerminalNode DIRECTED_EDGE() { return getToken(GKAParser.DIRECTED_EDGE, 0); }
		public ConnContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conn; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GKAListener ) ((GKAListener)listener).enterConn(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GKAListener ) ((GKAListener)listener).exitConn(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GKAVisitor ) return ((GKAVisitor<? extends T>)visitor).visitConn(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConnContext conn() throws RecognitionException {
		ConnContext _localctx = new ConnContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_conn);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(35);
			_la = _input.LA(1);
			if ( !(_la==DIRECTED_EDGE || _la==UNDIRECTED_EDGE) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Edge_nameContext extends ParserRuleContext {
		public TerminalNode NAME_BORDER(int i) {
			return getToken(GKAParser.NAME_BORDER, i);
		}
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public List<TerminalNode> NAME_BORDER() { return getTokens(GKAParser.NAME_BORDER); }
		public Edge_nameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_edge_name; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GKAListener ) ((GKAListener)listener).enterEdge_name(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GKAListener ) ((GKAListener)listener).exitEdge_name(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GKAVisitor ) return ((GKAVisitor<? extends T>)visitor).visitEdge_name(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Edge_nameContext edge_name() throws RecognitionException {
		Edge_nameContext _localctx = new Edge_nameContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_edge_name);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(37); match(NAME_BORDER);
			setState(38); name();
			setState(39); match(NAME_BORDER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Edge_weightContext extends ParserRuleContext {
		public String retVal;
		public WeightContext weight;
		public WeightContext weight() {
			return getRuleContext(WeightContext.class,0);
		}
		public TerminalNode WEIGHT_BORDER() { return getToken(GKAParser.WEIGHT_BORDER, 0); }
		public Edge_weightContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_edge_weight; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GKAListener ) ((GKAListener)listener).enterEdge_weight(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GKAListener ) ((GKAListener)listener).exitEdge_weight(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GKAVisitor ) return ((GKAVisitor<? extends T>)visitor).visitEdge_weight(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Edge_weightContext edge_weight() throws RecognitionException {
		Edge_weightContext _localctx = new Edge_weightContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_edge_weight);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(41); match(WEIGHT_BORDER);
			setState(42); ((Edge_weightContext)_localctx).weight = weight();

			                    ((Edge_weightContext)_localctx).retVal =  (((Edge_weightContext)_localctx).weight!=null?_input.getText(((Edge_weightContext)_localctx).weight.start,((Edge_weightContext)_localctx).weight.stop):null);
			                
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WeightContext extends ParserRuleContext {
		public TerminalNode DIGIT(int i) {
			return getToken(GKAParser.DIGIT, i);
		}
		public List<TerminalNode> DIGIT() { return getTokens(GKAParser.DIGIT); }
		public WeightContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_weight; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GKAListener ) ((GKAListener)listener).enterWeight(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GKAListener ) ((GKAListener)listener).exitWeight(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GKAVisitor ) return ((GKAVisitor<? extends T>)visitor).visitWeight(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WeightContext weight() throws RecognitionException {
		WeightContext _localctx = new WeightContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_weight);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(46); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(45); match(DIGIT);
				}
				}
				setState(48); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==DIGIT );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NodeContext extends ParserRuleContext {
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public NodeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_node; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GKAListener ) ((GKAListener)listener).enterNode(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GKAListener ) ((GKAListener)listener).exitNode(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GKAVisitor ) return ((GKAVisitor<? extends T>)visitor).visitNode(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NodeContext node() throws RecognitionException {
		NodeContext _localctx = new NodeContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_node);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(50); name();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NameContext extends ParserRuleContext {
		public List<TerminalNode> LETTER() { return getTokens(GKAParser.LETTER); }
		public TerminalNode DIGIT(int i) {
			return getToken(GKAParser.DIGIT, i);
		}
		public TerminalNode LETTER(int i) {
			return getToken(GKAParser.LETTER, i);
		}
		public List<TerminalNode> DIGIT() { return getTokens(GKAParser.DIGIT); }
		public NameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_name; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GKAListener ) ((GKAListener)listener).enterName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GKAListener ) ((GKAListener)listener).exitName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GKAVisitor ) return ((GKAVisitor<? extends T>)visitor).visitName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NameContext name() throws RecognitionException {
		NameContext _localctx = new NameContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_name);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(52); match(LETTER);
			setState(56);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==LETTER || _la==DIGIT) {
				{
				{
				setState(53);
				_la = _input.LA(1);
				if ( !(_la==LETTER || _la==DIGIT) ) {
				_errHandler.recoverInline(this);
				}
				consume();
				}
				}
				setState(58);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\uacf5\uee8c\u4f5d\u8b0d\u4a45\u78bd\u1b2f\u3378\3\n>\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\3\2\6\2\24\n\2\r\2"+
		"\16\2\25\3\3\3\3\3\3\3\3\5\3\34\n\3\3\3\5\3\37\n\3\5\3!\n\3\3\3\3\3\3"+
		"\3\3\4\3\4\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\7\6\7\61\n\7\r\7\16\7\62"+
		"\3\b\3\b\3\t\3\t\7\t9\n\t\f\t\16\t<\13\t\3\t\2\n\2\4\6\b\n\f\16\20\2\4"+
		"\3\2\5\6\3\2\3\4;\2\23\3\2\2\2\4\27\3\2\2\2\6%\3\2\2\2\b\'\3\2\2\2\n+"+
		"\3\2\2\2\f\60\3\2\2\2\16\64\3\2\2\2\20\66\3\2\2\2\22\24\5\4\3\2\23\22"+
		"\3\2\2\2\24\25\3\2\2\2\25\23\3\2\2\2\25\26\3\2\2\2\26\3\3\2\2\2\27 \5"+
		"\16\b\2\30\31\5\6\4\2\31\33\5\16\b\2\32\34\5\b\5\2\33\32\3\2\2\2\33\34"+
		"\3\2\2\2\34\36\3\2\2\2\35\37\5\n\6\2\36\35\3\2\2\2\36\37\3\2\2\2\37!\3"+
		"\2\2\2 \30\3\2\2\2 !\3\2\2\2!\"\3\2\2\2\"#\7\t\2\2#$\b\3\1\2$\5\3\2\2"+
		"\2%&\t\2\2\2&\7\3\2\2\2\'(\7\7\2\2()\5\20\t\2)*\7\7\2\2*\t\3\2\2\2+,\7"+
		"\b\2\2,-\5\f\7\2-.\b\6\1\2.\13\3\2\2\2/\61\7\4\2\2\60/\3\2\2\2\61\62\3"+
		"\2\2\2\62\60\3\2\2\2\62\63\3\2\2\2\63\r\3\2\2\2\64\65\5\20\t\2\65\17\3"+
		"\2\2\2\66:\7\3\2\2\679\t\3\2\28\67\3\2\2\29<\3\2\2\2:8\3\2\2\2:;\3\2\2"+
		"\2;\21\3\2\2\2<:\3\2\2\2\b\25\33\36 \62:";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}