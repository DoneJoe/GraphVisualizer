package ch.bfh.ti.gravis.core.graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

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
	private static final String GRAPH_IO_EXCEPTION_LOAD = "I/O Exception in GraphML-file %s!";
	private static final String FILE_NOT_FOUND_EXCEPTION_LOAD = "GraphML-file not found: %s!";
	private static final String EXCEPTION_LOAD = "Exception while loading data from "
			+ "GraphML-file %s!";
	private static final String FILE_NOT_FOUND_EXCEPTION_SAVE = "Exception while creating GraphML-file %s!";
	private static final String IO_EXCEPTION_SAVE = "I/O Exception while saving GraphML-file %s!";
	private static final String EXCEPTION_SAVE = "Exception while storing data to GraphML-file %s!";

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
	 * @throws GravisGraphIOException
	 */
	public IGravisGraph loadGraph(final File file) throws GravisGraphIOException {
		try {
			GraphMLReader2<IGravisGraph, IVertex, IEdge> graphReader = new GraphMLReader2<>(
					new FileReader(file), this.graphTransformer,
					this.vertexTransformer, this.edgeTransformer,
					this.hyperEdgeTransformer);

			IGravisGraph newGraph = graphReader.readGraph();

			graphReader.close();

			return newGraph;
		} catch (GraphIOException e) {
			
			// TODO Exception handling verbessern
			
			throw new GravisGraphIOException(String.format(
					GRAPH_IO_EXCEPTION_LOAD, file.getName()), e);
		} catch (FileNotFoundException e) {
			throw new GravisGraphIOException(String.format(
					FILE_NOT_FOUND_EXCEPTION_LOAD, file.getName()), e);
		} catch (Exception e) {
			throw new GravisGraphIOException(String.format(EXCEPTION_LOAD,
					file.getName()), e);
		}
	}

	/**
	 * 
	 * @param graph
	 * @param file
	 * @throws GravisGraphIOException
	 */
	public void saveGraph(final IGravisGraph graph, final File file)
			throws GravisGraphIOException {
		try {
			GravisGraphMLWriter graphWriter = new GravisGraphMLWriter();
			PrintWriter writer = new PrintWriter(file);

			graphWriter.setVertexIDs(new VertexNameTransformer());
			graphWriter.setEdgeIDs(new EdgeNameTransformer());

			// graph name is default description
			graphWriter.addGraphData(GravisConstants.G_DESCRIPTION, "", graph.getName(),
					new GraphDescriptionTransformer());

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

			writer.close();
		} catch (FileNotFoundException e) {
			
			// TODO Exception handling verbessern
			
			throw new GravisGraphIOException(String.format(
					FILE_NOT_FOUND_EXCEPTION_SAVE, file.getName()), e);
		} catch (IOException e) {
			throw new GravisGraphIOException(String.format(IO_EXCEPTION_SAVE,
					file.getName()), e);
		} catch (Exception e) {
			throw new GravisGraphIOException(String.format(EXCEPTION_SAVE,
					file.getName()), e);
		}
	}

}
