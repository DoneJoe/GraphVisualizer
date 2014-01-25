package ch.bfh.bti7301.hs2013.gravis.core.graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import ch.bfh.bti7301.hs2013.gravis.core.graph.item.edge.IEdge;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IVertex;
import ch.bfh.bti7301.hs2013.gravis.core.util.GravisConstants;
import ch.bfh.bti7301.hs2013.gravis.core.util.transformer.EdgeColorStringTransformer;
import ch.bfh.bti7301.hs2013.gravis.core.util.transformer.EdgeIDTransformer;
import ch.bfh.bti7301.hs2013.gravis.core.util.transformer.EdgeTransformer;
import ch.bfh.bti7301.hs2013.gravis.core.util.transformer.EdgeWeightTransformer;
import ch.bfh.bti7301.hs2013.gravis.core.util.transformer.EndVertexTransformer;
import ch.bfh.bti7301.hs2013.gravis.core.util.transformer.GraphDescriptionTransformer;
import ch.bfh.bti7301.hs2013.gravis.core.util.transformer.GraphTransformer;
import ch.bfh.bti7301.hs2013.gravis.core.util.transformer.HyperEdgeTransformer;
import ch.bfh.bti7301.hs2013.gravis.core.util.transformer.StartVertexTransformer;
import ch.bfh.bti7301.hs2013.gravis.core.util.transformer.VertexColorStringTransformer;
import ch.bfh.bti7301.hs2013.gravis.core.util.transformer.VertexHeightTransformer;
import ch.bfh.bti7301.hs2013.gravis.core.util.transformer.VertexIDTransformer;
import ch.bfh.bti7301.hs2013.gravis.core.util.transformer.VertexLocationXTransformer;
import ch.bfh.bti7301.hs2013.gravis.core.util.transformer.VertexLocationYTransformer;
import ch.bfh.bti7301.hs2013.gravis.core.util.transformer.VertexTransformer;
import ch.bfh.bti7301.hs2013.gravis.core.util.transformer.VertexWidthTransformer;
import edu.uci.ics.jung.io.GraphIOException;
import edu.uci.ics.jung.io.graphml.GraphMLReader2;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class GraphManager implements IGraphManager {

	private static final String GRAPH_IO_EXCEPTION_LOAD = "I/O Exception in GraphML-file %s!";
	private static final String FILE_NOT_FOUND_EXCEPTION_LOAD = "GraphML-file not found: %s!";
	private static final String EXCEPTION_LOAD = "Exception while loading data from "
			+ "GraphML-file %s!";
	private static final String FILE_NOT_FOUND_EXCEPTION_SAVE = 
			"Exception while creating GraphML-file %s!";
	private static final String IO_EXCEPTION_SAVE = "I/O Exception while saving GraphML-file %s!";
	private static final String EXCEPTION_SAVE = "Exception while storing data to GraphML-file %s!";

	private final GraphTransformer graphTransformer;
	private final VertexTransformer vertexTransformer;
	private final EdgeTransformer edgeTransformer;
	private final HyperEdgeTransformer hyperEdgeTransformer;

	protected GraphManager() {
		this.graphTransformer = new GraphTransformer();
		this.vertexTransformer = new VertexTransformer();
		this.edgeTransformer = new EdgeTransformer();
		this.hyperEdgeTransformer = new HyperEdgeTransformer();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IGraphManager#importGraph(java
	 * .io.File)
	 */
	@Override
	public IGravisGraph importGraph(File file) throws GraphException {
		try {
			GraphMLReader2<IGravisGraph, IVertex, IEdge> graphReader = new GraphMLReader2<>(
					new FileReader(file), this.graphTransformer,
					this.vertexTransformer, this.edgeTransformer,
					this.hyperEdgeTransformer);

			IGravisGraph newGraph = graphReader.readGraph();

			graphReader.close();

			return newGraph;
		} catch (GraphIOException e) {
			throw new GraphException(String.format(GRAPH_IO_EXCEPTION_LOAD,
					file.getName()), e);
		} catch (FileNotFoundException e) {
			throw new GraphException(String.format(FILE_NOT_FOUND_EXCEPTION_LOAD,
					file.getName()), e);
		} catch (Exception e) {
			throw new GraphException(String.format(EXCEPTION_LOAD,
							file.getName()), e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IGraphManager#saveGraph(ch.bfh
	 * .bti7301.hs2013.gravis.core.graph.IGravisGraph, java.io.File)
	 */
	@Override
	public void saveGraph(IGravisGraph graph, File file) throws GraphException {
		try {
			GravisGraphMLWriter graphWriter = new GravisGraphMLWriter();
			PrintWriter writer = new PrintWriter(file);

			graphWriter.setVertexIDs(new VertexIDTransformer());
			graphWriter.setEdgeIDs(new EdgeIDTransformer());

			graphWriter.addGraphData(GravisConstants.G_DESCRIPTION, "", "",
					new GraphDescriptionTransformer());

			graphWriter.addEdgeData(GravisConstants.E_COLOR, "",
					GravisConstants.BLACK, new EdgeColorStringTransformer());
			graphWriter.addEdgeData(GravisConstants.E_WEIGHT, "",
					String.valueOf(GravisConstants.E_WEIGHT_DEFAULT),
					new EdgeWeightTransformer());

			graphWriter.addVertexData(GravisConstants.V_COLOR, "",
					GravisConstants.RED, new VertexColorStringTransformer());
			graphWriter.addVertexData(GravisConstants.V_START, "",
					String.valueOf(GravisConstants.V_START_DEFAULT),
					new StartVertexTransformer());
			graphWriter.addVertexData(GravisConstants.V_END, "",
					String.valueOf(GravisConstants.V_END_DEFAULT),
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
			throw new GraphException(String.format(FILE_NOT_FOUND_EXCEPTION_SAVE,
					file.getName()), e);
		} catch (IOException e) {
			throw new GraphException(String.format(IO_EXCEPTION_SAVE,
					file.getName()), e);
		} catch (Exception e) {
			throw new GraphException(String.format(EXCEPTION_SAVE, file.getName()), e);
		}
	}

}
