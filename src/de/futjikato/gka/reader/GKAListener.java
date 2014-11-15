// Generated from /home/moritz/java/HAW-GKA-Core/antlr/GKA.g4 by ANTLR 4.1
package de.futjikato.gka.reader;

      import java.util.List;
      import java.util.ArrayList;
      import de.futjikato.gka.EdgeEntity;
      import de.futjikato.gka.GraphFactory;
      import de.futjikato.gka.Vertex;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link GKAParser}.
 */
public interface GKAListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link GKAParser#edge_weight}.
	 * @param ctx the parse tree
	 */
	void enterEdge_weight(@NotNull GKAParser.Edge_weightContext ctx);
	/**
	 * Exit a parse tree produced by {@link GKAParser#edge_weight}.
	 * @param ctx the parse tree
	 */
	void exitEdge_weight(@NotNull GKAParser.Edge_weightContext ctx);

	/**
	 * Enter a parse tree produced by {@link GKAParser#node}.
	 * @param ctx the parse tree
	 */
	void enterNode(@NotNull GKAParser.NodeContext ctx);
	/**
	 * Exit a parse tree produced by {@link GKAParser#node}.
	 * @param ctx the parse tree
	 */
	void exitNode(@NotNull GKAParser.NodeContext ctx);

	/**
	 * Enter a parse tree produced by {@link GKAParser#edge}.
	 * @param ctx the parse tree
	 */
	void enterEdge(@NotNull GKAParser.EdgeContext ctx);
	/**
	 * Exit a parse tree produced by {@link GKAParser#edge}.
	 * @param ctx the parse tree
	 */
	void exitEdge(@NotNull GKAParser.EdgeContext ctx);

	/**
	 * Enter a parse tree produced by {@link GKAParser#conn}.
	 * @param ctx the parse tree
	 */
	void enterConn(@NotNull GKAParser.ConnContext ctx);
	/**
	 * Exit a parse tree produced by {@link GKAParser#conn}.
	 * @param ctx the parse tree
	 */
	void exitConn(@NotNull GKAParser.ConnContext ctx);

	/**
	 * Enter a parse tree produced by {@link GKAParser#edge_name}.
	 * @param ctx the parse tree
	 */
	void enterEdge_name(@NotNull GKAParser.Edge_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link GKAParser#edge_name}.
	 * @param ctx the parse tree
	 */
	void exitEdge_name(@NotNull GKAParser.Edge_nameContext ctx);

	/**
	 * Enter a parse tree produced by {@link GKAParser#name}.
	 * @param ctx the parse tree
	 */
	void enterName(@NotNull GKAParser.NameContext ctx);
	/**
	 * Exit a parse tree produced by {@link GKAParser#name}.
	 * @param ctx the parse tree
	 */
	void exitName(@NotNull GKAParser.NameContext ctx);

	/**
	 * Enter a parse tree produced by {@link GKAParser#weight}.
	 * @param ctx the parse tree
	 */
	void enterWeight(@NotNull GKAParser.WeightContext ctx);
	/**
	 * Exit a parse tree produced by {@link GKAParser#weight}.
	 * @param ctx the parse tree
	 */
	void exitWeight(@NotNull GKAParser.WeightContext ctx);

	/**
	 * Enter a parse tree produced by {@link GKAParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(@NotNull GKAParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link GKAParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(@NotNull GKAParser.ProgContext ctx);
}