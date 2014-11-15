// Generated from /home/moritz/java/HAW-GKA-Core/antlr/GKA.g4 by ANTLR 4.1
package de.futjikato.gka.reader;

      import java.util.List;
      import java.util.ArrayList;
      import de.futjikato.gka.EdgeEntity;
      import de.futjikato.gka.GraphFactory;
      import de.futjikato.gka.Vertex;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link GKAParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface GKAVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link GKAParser#edge_weight}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEdge_weight(@NotNull GKAParser.Edge_weightContext ctx);

	/**
	 * Visit a parse tree produced by {@link GKAParser#node}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNode(@NotNull GKAParser.NodeContext ctx);

	/**
	 * Visit a parse tree produced by {@link GKAParser#edge}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEdge(@NotNull GKAParser.EdgeContext ctx);

	/**
	 * Visit a parse tree produced by {@link GKAParser#conn}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConn(@NotNull GKAParser.ConnContext ctx);

	/**
	 * Visit a parse tree produced by {@link GKAParser#edge_name}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEdge_name(@NotNull GKAParser.Edge_nameContext ctx);

	/**
	 * Visit a parse tree produced by {@link GKAParser#name}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitName(@NotNull GKAParser.NameContext ctx);

	/**
	 * Visit a parse tree produced by {@link GKAParser#weight}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWeight(@NotNull GKAParser.WeightContext ctx);

	/**
	 * Visit a parse tree produced by {@link GKAParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(@NotNull GKAParser.ProgContext ctx);
}