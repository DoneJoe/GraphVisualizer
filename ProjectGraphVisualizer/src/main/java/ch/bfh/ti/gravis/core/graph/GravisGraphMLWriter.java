package ch.bfh.ti.gravis.core.graph;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

import org.apache.commons.collections15.Transformer;

import ch.bfh.ti.gravis.core.graph.item.edge.IEdge;
import ch.bfh.ti.gravis.core.graph.item.vertex.IVertex;
import edu.uci.ics.jung.graph.Hypergraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.io.GraphMLMetadata;
import edu.uci.ics.jung.io.GraphMLWriter;

/**
 * Writes graphs out in GraphML format.
 *
 * Current known issues: 
 * <ul>
 * <li/>Only supports one graph per output file.
 * <li/>Does not indent lines for text-format readability.
 * </ul>
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class GravisGraphMLWriter extends GraphMLWriter<IVertex, IEdge> {

	protected GravisGraphMLWriter() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.uci.ics.jung.io.GraphMLWriter#save(edu.uci.ics.jung.graph.Hypergraph,
	 * java.io.Writer)
	 */
	@Override
	public void save(final Hypergraph<IVertex, IEdge> graph, final Writer w)
			throws IOException {

		BufferedWriter bw = null;
		
		try {
			if (!(graph instanceof IGravisGraph)) {
				return;
			}
			
			IGravisGraph gravisGraph = (IGravisGraph) graph;
			bw = new BufferedWriter(w);
			
			// write out boilerplate header
			bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
			bw.write("<graphml xmlns=\"http://graphml.graphdrawing.org/xmlns\"\n"
					+ "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"  \n");
			bw.write("xsi:schemaLocation=\"http://graphml.graphdrawing.org/xmlns\n");
			bw.write("http://graphml.graphdrawing.org/xmlns/1.0/graphml.xsd\">\n");
			
			// write out data specifiers, including defaults
			for (String key : this.graph_data.keySet())
				this.writeKeySpecification(key, "graph",
						this.graph_data.get(key), bw);
			for (String key : this.vertex_data.keySet())
				this.writeKeySpecification(key, "node",
						this.vertex_data.get(key), bw);
			for (String key : this.edge_data.keySet())
				this.writeKeySpecification(key, "edge",
						this.edge_data.get(key), bw);
			
			// set edge default direction
			bw.write("<graph id=\"" + gravisGraph.getName()
					+ "\" edgedefault=\"");
			
			// this.directed = !(gravisGraph instanceof UndirectedGraph);
			this.directed = gravisGraph.getEdgeType() == EdgeType.DIRECTED;
			
			if (this.directed) {
				bw.write("directed\">\n");
			} else {
				bw.write("undirected\">\n");
			}
			
			// reads description from key-attribute
			// String desc = this.graph_desc.transform(gravisGraph);
			// if (desc != null)
			// bw.write("<desc>" + desc + "</desc>\n");
			
			// write graph data out if any
			for (String key : this.graph_data.keySet()) {
				Transformer<Hypergraph<IVertex, IEdge>, ?> t = this.graph_data
						.get(key).transformer;
				Object value = t.transform(gravisGraph);
				if (value != null)
					bw.write(format("data", "key", key, value.toString())
							+ "\n");
			}
			
			// write vertex information
			this.writeVertexData(gravisGraph, bw);
			
			// write edge information
			this.writeEdgeData(gravisGraph, bw);
			
			// close graph
			bw.write("</graph>\n");
			bw.write("</graphml>\n");
			bw.flush();
		} finally {
			if (bw != null) {
				bw.close();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.uci.ics.jung.io.GraphMLWriter#writeKeySpecification(java.lang.String,
	 * java.lang.String, edu.uci.ics.jung.io.GraphMLMetadata,
	 * java.io.BufferedWriter)
	 */
	@Override
	protected void writeKeySpecification(String key, String type,
			GraphMLMetadata<?> ds, BufferedWriter bw) throws IOException {
		
		bw.write("<key id=\"" + key + "\" for=\"" + type + "\"");
		boolean closed = false;
		
		// write out description if any
//		String desc = ds.description;
//		if (desc != null) {
//			if (!closed) {
//				bw.write(">\n");
//				closed = true;
//			}
//			bw.write("<desc>" + desc + "</desc>\n");
//		}
		
		// write out default if any
		Object def = ds.default_value;
		if (def != null) {
			if (!closed) {
				bw.write(">\n");
				closed = true;
			}
			bw.write("<default>" + def.toString() + "</default>\n");
		}
		
		if (!closed)
			bw.write("/>\n");
		else
			bw.write("</key>\n");
	}

}
