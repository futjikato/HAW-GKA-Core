package de.futjikato.gka;

public class EdgeEntity {

    public enum DirectionType {
        DIRECTED("->"),
        UNDIRECTED("--");

        public String symbol;

        DirectionType(String symbol) {
            this.symbol = symbol;
        }

        public static DirectionType getBySymbol(String sym) {
            for(DirectionType type : values()) {
                if(type.symbol.equals(sym)) {
                    return type;
                }
            }

            return null;
        }
    }

    private String nodeA;

    private String nodeB;

    private String name;

    private int weight = 0;

    private DirectionType type;

    public String getNodeA() {
        return nodeA;
    }

    public void setNodeA(String nodeA) {
        this.nodeA = nodeA;
    }

    public String getNodeB() {
        return nodeB;
    }

    public void setNodeB(String nodeB) {
        this.nodeB = nodeB;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public DirectionType getType() {
        return type;
    }

    public void setType(DirectionType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if(nodeA != null) {
            sb.append("nodeA=");
            sb.append(nodeA);
            sb.append("; ");
        }
        if(nodeB != null) {
            sb.append("nodeB=");
            sb.append(nodeB);
            sb.append("; ");
        }
        if(name != null) {
            sb.append("name=");
            sb.append(name);
            sb.append("; ");
        }
        if(type != null) {
            sb.append("type=");
            sb.append(type.toString());
            sb.append("; ");
        }

        sb.append("weight=");
        sb.append(weight);
        sb.append("; ");
        sb.append("}");

        return sb.toString();
    }
}
