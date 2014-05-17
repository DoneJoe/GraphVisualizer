package ch.bfh.ti.gravis.core.graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

import ch.bfh.ti.gravis.core.graph.item.edge.IEdge;
import ch.bfh.ti.gravis.core.graph.item.vertex.IVertex;
import ch.bfh.ti.gravis.core.graph.transformer.EdgeColorStringTransformer;
import ch.bfh.ti.gravis.core.graph.transformer.EdgeNameTransformer;
import ch.bfh.ti.gravis.core.graph.transformer.EdgeTransformer;
import ch.bfh.ti.gravis.core.graph.transformer.EdgeWeightTransformer;
import ch.bfh.ti.gravis.core.graph.transformer.EndVertexTransformer;
import ch.bfh.ti.gravis.core.graph.transformer.GraphDescriptionTransformer;
import ch.bfh.ti.gravis.core.graph.transformer.GraphTransformer;
import ch.bfh.ti.gravis.core.graph.transformer.HyperEdgeTransformer;
import ch.bfh.ti.gravis.core.graph.transformer.StartVertexTransformer;
import ch.bfh.ti.gravis.core.graph.transformer.VertexColorStringTransformer;
import ch.bfh.ti.gravis.core.graph.transformer.VertexHeightTransformer;
import ch.bfh.ti.gravis.core.graph.transformer.VertexLocationXTransformer;
import ch.bfh.ti.gravis.core.graph.transformer.VertexLocationYTransformer;
import ch.bfh.ti.gravis.core.graph.transformer.VertexNameTransformer;
import ch.bfh.ti.gravis.core.graph.transformer.VertexTransformer;
import ch.bfh.ti.gravis.core.graph.transformer.VertexWidthTransformer;
import ch.bfh.ti.gravis.core.util.GravisConstants;
import ch.bfh.ti.gravis.core.util.ValueTransformer;
import edu.uci.ics.jung.io.GraphIOException;
import edu.uci.ics.jung.io.graphml.GraphMLReader2;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class GraphIOManager {
	
	private static final String NULL_POINTER_MSG = "Invalid parameter value in method "
			+ "GraphIOManager.%s(): %s == %s";

	// transformers:
	
	private final GraphTransformer graphTransformer;
	private final VertexTransformer vertexTransformer;
	private final EdgeTransformer edgeTransformer;
	private final HyperEdgeTransformer hyperEdgeTransformer;

	public GraphIOManager() {
		this.graphTransformer = new GraphTransformer();
		this.vertexTransformer = new VertexTransformer();
		this.edgeTransformer = new EdgeTransformer();
		this.hyperEdgeTransformer = new HyperEdgeTransformer();
	}

	/**
	 * 
	 * @param file
	 * @return IGravisGraph
	 * @throws GraphIOException
	 * @throws FileNotFoundException
	 */
	public IGravisGraph loadGraph(final File file) throws GraphIOException, FileNotFoundException {
		Objects.requireNonNull(file, String.format(NULL_POINTER_MSG, "loadGraph",
				"file", file));		
		GraphMLReader2<IGravisGraph, IVertex, IEdge> graphReader = null;
				
		try {
			graphReader = new GraphMLReader2<>(
					new FileReader(file), this.graphTransformer,
					this.vertexTransformer, this.edgeTransformer,
					this.hyperEdgeTransformer);
			return graphReader.readGraph();
		} finally {
			if (graphReader != null) {
				graphReader.close();
			}			
		}
	}

	/**
	 * 
	 * @param graph
	 * @param file
	 * @throws GraphIOException
	 * @throws FileNotFoundException 
	 */
	public void saveGraph(final IGravisGraph graph, final File file)
			throws GraphIOException, FileNotFoundException {
		
		Objects.requireNonNull(graph, String.format(NULL_POINTER_MSG, "saveGraph",
				"graph", graph));
		Objects.requireNonNull(file, String.format(NULL_POINTER_MSG, "saveGraph",
				"file", file));
		PrintWriter writer = null;
		
		try {						
			GravisGraphMLWriter graphWriter = new GravisGraphMLWriter();
			writer = new PrintWriter(file);

			graphWriter.setVertexIDs(new VertexNameTransformer());
			graphWriter.setEdgeIDs(new EdgeNameTransformer());

			// graph name is default description
			graphWriter.addGraphData(GravisConstants.G_DESCRIPTION, "", graph.getName(),
					new GraphDescriptionTransformer());

			// adds edge data:
			
			graphWriter
					.addEdgeData(
							GravisConstants.E_COLOR,
							"",
							ValueTransformer
									.colorToString(GravisConstants.E_COLOR_DEFAULT),
							new EdgeColorStringTransformer());
			graphWriter.addEdgeData(GravisConstants.E_WEIGHT, "",
					String.valueOf(GravisConstants.E_WEIGHT_DEFAULT),
					new EdgeWeightTransformer());

			// adds vertex data:
			
			graphWriter
					.addVertexData(
							GravisConstants.V_COLOR,
							"",
							ValueTransformer
									.colorToString(GravisConstants.V_FILL_COLOR_DEFAULT),
							new VertexColorStringTransformer());
			graphWriter.addVertexData(GravisConstants.V_START, "",
					String.valueOf(false),
					new StartVertexTransformer());
			graphWriter.addVertexData(GravisConstants.V_END, "",
					String.valueOf(false),
					new EndVertexTransformer());
			graphWriter.addVertexData(GravisConstants.V_LOC_X, "",
					String.valueOf(GravisConstants.V_LOC_X_DEFAULT),
					new VertexLocationXTransformer());
			graphWriter.addVertexData(GravisConstants.V_LOC_Y, "",
					String.valueOf(GravisConstants.V_LOC_Y_DEFAULT),
					new VertexLocationYTransformer());
			graphWriter.addVertexData(GravisConstants.V_WIDTH, "",
					String.valueOf(GravisConstants.V_WIDTH_DEFAULT),
					new VertexWidthTransformer());
			graphWriter.addVertexData(GravisConstants.V_HEIGHT, "",
					String.valueOf(GravisConstants.V_HEIGHT_DEFAULT),
					new VertexHeightTransformer());

			graphWriter.save(graph, writer);
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			GraphIOException e2 = new GraphIOException(e.getMessage(), e);
			e2.setStackTrace(e.getStackTrace());
			throw e2;
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

}
