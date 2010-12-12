package net.markguerra.android.glwallpapertest;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

public class GLCube {
	private final int _nrOfVertices = 8;

	private FloatBuffer _vertexBuffer;

	private FloatBuffer _normalBuffer;

	private ShortBuffer frontFaceIndices;
	private ShortBuffer rightFaceIndices;
	private ShortBuffer leftFaceIndices;
	private ShortBuffer backFaceIndices;
	private ShortBuffer topFaceIndices;
	private ShortBuffer bottomFaceIndices;

	public GLCube() {
		this.init();
	}

	private void init() {
		// 3 is the number of coordinates to each vertex.
		_vertexBuffer = BufferFactory.createFloatBuffer(_nrOfVertices * 3);

		//We have defined 108 floats for the normals.
		//108 = 3 coordinates in a vertex
		//    * 3 vertexes in a triangle
		//    * 2 triangle in a face
		//    * 6 faces in a cube
		_normalBuffer = BufferFactory.createFloatBuffer(108);

		// Each face has 6 indices, because it is made of two different triangles
		frontFaceIndices =  BufferFactory.createShortBuffer(6);
		rightFaceIndices =  BufferFactory.createShortBuffer(6);
		leftFaceIndices =   BufferFactory.createShortBuffer(6);
		backFaceIndices =   BufferFactory.createShortBuffer(6);
		topFaceIndices =    BufferFactory.createShortBuffer(6);
		bottomFaceIndices = BufferFactory.createShortBuffer(6);

		// Coordinates for the vertexes of the cube.
		float[] vertexCoords = {
				1f,  1f,  0f, // Vertex 0
				0f,  1f,  0f, // Vertex 1
				0f,  0f,  0f, // Vertex 2
				1f,  0f,  0f, // Vertex 3
				1f,  0f, -1f, // Vertex 4
				1f,  1f, -1f, // Vertex 5
				0f,  1f, -1f, // Vertex 6
				0f,  0f, -1f, // Vertex 7
		};

		short[] frontFaceIndicesArray = {0, 1, 2,   0, 2, 3};
		short[] rightFaceIndicesArray = {3, 4, 0,   4, 5, 0};
		short[] leftFaceIndicesArray =  {7, 2, 6,   6, 2, 1};
		short[] backFaceIndicesArray =  {4, 7, 5,   7, 6, 5};
		short[] topFaceIndicesArray =   {1, 0, 6,   6, 0, 5};
		short[] bottomFaceIndicesArray= {2, 7, 3,   7, 4, 3};

		//Coordinates for Normal Vector. Used for Lighting calculations
		float[] normalCoords = {
				 0f, 0f, 1f,   0f, 0f, 1f,   0f, 0f, 1f,   0f, 0f, 1f,   0f, 0f, 1f,   0f, 0f, 1f,   //Front Face
				 1f, 0f, 0f,   1f, 0f, 0f,   1f, 0f, 0f,   1f, 0f, 0f,   1f, 0f, 0f,   1f, 0f, 0f,   //Right Face
				-1f, 0f, 0f,  -1f, 0f, 0f,  -1f, 0f, 0f,  -1f, 0f, 0f,  -1f, 0f, 0f,  -1f, 0f, 0f,   //Left Face
				 0f, 0f,-1f,   0f, 0f,-1f,   0f, 0f,-1f,   0f, 0f,-1f,   0f, 0f,-1f,   0f, 0f,-1f,   //Back Face
				 0f, 1f, 0f,   0f, 1f, 0f,   0f, 1f, 0f,   0f, 1f, 0f,   0f, 1f, 0f,   0f, 1f, 0f,   //Top Face
				 0f,-1f, 0f,   0f,-1f, 0f,   0f,-1f, 0f,   0f,-1f, 0f,   0f,-1f, 0f,   0f,-1f, 0f    //Bottom Face
		};

		_vertexBuffer.put(vertexCoords);

		_normalBuffer.put(normalCoords);

		frontFaceIndices.put(frontFaceIndicesArray);
		rightFaceIndices.put(rightFaceIndicesArray);
		leftFaceIndices.put(leftFaceIndicesArray);
		backFaceIndices.put(backFaceIndicesArray);
		topFaceIndices.put(topFaceIndicesArray);
		bottomFaceIndices.put(bottomFaceIndicesArray);

		_vertexBuffer.position(0);
		_normalBuffer.position(0);
		frontFaceIndices.position(0);
		rightFaceIndices.position(0);
		leftFaceIndices.position(0);
		backFaceIndices.position(0);
		topFaceIndices.position(0);
		bottomFaceIndices.position(0);
	}

	public void draw(GL10 gl) {
		// 3 coordinates in each vertex
		// 0 is the space between each vertex. They are densely packed in the array, so the value is 0
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, _vertexBuffer);

		// 0 is the space between each vertex. They are densely packed in the array, so the value is 0
		gl.glNormalPointer(GL10.GL_FLOAT, 0, _normalBuffer);

		// Draw the faces of the cube.
		// Each face has 6 indices, because it is made of two different triangles.
		//Draw front face
		gl.glColor4f(1.0f, 0f, 0f, 1f);		//Red
		gl.glDrawElements(GL10.GL_TRIANGLES, 6, GL10.GL_UNSIGNED_SHORT, frontFaceIndices);

		//Draw right face
		gl.glColor4f(1f, 0.4f, 0f, 1f);		//Orange
		gl.glDrawElements(GL10.GL_TRIANGLES, 6, GL10.GL_UNSIGNED_SHORT, rightFaceIndices);

		//Draw left face
		gl.glColor4f(1f, 0.4f, 0.4f, 1f);	//Salmon
		gl.glDrawElements(GL10.GL_TRIANGLES, 6, GL10.GL_UNSIGNED_SHORT, leftFaceIndices);

		//Draw back face
		gl.glColor4f(0.6f, 0.1f, 0.6f, 1f);	//Purple
		gl.glDrawElements(GL10.GL_TRIANGLES, 6, GL10.GL_UNSIGNED_SHORT, backFaceIndices);

		//Draw top face
		gl.glColor4f(0.8f, 0.8f, 0.8f, 1f);	//Off White
		gl.glDrawElements(GL10.GL_TRIANGLES, 6, GL10.GL_UNSIGNED_SHORT, topFaceIndices);

		//Draw bottom face
		gl.glColor4f(0.3f, 0.3f, 0.3f, 1f);	//Dark grey
		gl.glDrawElements(GL10.GL_TRIANGLES, 6, GL10.GL_UNSIGNED_SHORT, bottomFaceIndices);
	}
}
