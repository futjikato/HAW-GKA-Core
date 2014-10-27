/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

grammar GKA;

@header {
      import java.util.List;
      import java.util.ArrayList;
      import de.futjikato.gka.Edge;
      import de.futjikato.gka.GrapgFactory;
}

@members {
          
          private GrapgFactory graphFactory = new GrapgFactory();
          
          public GrapgFactory getGraphFactory() {
              return graphFactory;
          }
          
}

prog	:	edge+;

edge	:	a=node (c=conn b=node n=edge_name? w=edge_weight?)? COMM_END
                {
                    Edge edge = new Edge();
                    edge.setNodeA($a.text);
                    if($b.text != null) {
                        edge.setNodeB($b.text);
                    }
                    if($n.text != null) {
                        edge.setName($b.text);
                    }
                    if($w.text != null) {
                        edge.setWeight(Integer.valueOf($w.retVal));
                    }
                    if($c.text != null) {
                        Edge.DirectionType dt = Edge.DirectionType.getBySymbol($c.text);
                        if(dt != null) {
                            edge.setType(dt);
                        }
                    }
                    
                    graphFactory.addEdge(edge);
                };

conn	:	DIRECTED_EDGE|UNDIRECTED_EDGE;

edge_name
	:	NAME_BORDER name NAME_BORDER;
	
edge_weight returns[String retVal]
	:	WEIGHT_BORDER weight
                {
                    $retVal = $weight.text;
                };
	
weight	:	DIGIT+;
	
node	:	name;

name	:	LETTER (LETTER|DIGIT)*;

LETTER	:	('a'..'z'|'A'..'Z'|'ü'|'ä'|'ö'|'ß'|'Ü'|'Ä'|'Ö');

DIGIT	:	'0'..'9';

DIRECTED_EDGE
	:	'->'|'<-';

UNDIRECTED_EDGE
	:	'--';

NAME_BORDER
        :       '('|')';

WEIGHT_BORDER
        :       ':';
	
COMM_END:	';';
	
WS	:	(' '|'\t'|'\n'|'\r') -> skip;