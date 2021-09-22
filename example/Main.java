
import java.io.FileNotFoundException;
import java.util.LinkedList;
import com.configuration.ConfigurationMap;
import com.exceptions.LayerNotFoundException;
import com.exceptions.SplitErrorException;
import com.neuralvisualizer.utilities.modeler.Model;
import com.neuralvisualizer.utilities.modeler.Renderer;
import com.neuralvisualizer.utilities.resources.layers.Conv2D;
import com.neuralvisualizer.utilities.resources.layers.Dense;
import com.neuralvisualizer.utilities.resources.layers.Input;
import com.neuralvisualizer.utilities.resources.layers.Layers;

import java.io.FileNotFoundException;

public class Main {

	 public static void main(String[] args) throws SplitErrorException, LayerNotFoundException, FileNotFoundException {


	 	 //Example simple model
		 Model model= new Model();

		 //Add all the layers
		 model.add(new Input(224,224,3,11,11));
		 model.add(new Conv2D(55,55,96,5,5));
		 model.add(new Conv2D(27,27,256,3,3));
		 model.add(new Conv2D(13,13,384,3,3));
		 model.add(new Conv2D(13,13,384,3,3));
		 model.add(new Conv2D(13,13,256,3,3));
		 model.add(new Dense(2048));
		 model.add(new Dense(2048));
		 model.add(new Dense(1000));

		 //Create and modify configuration class
		 ConfigurationMap config = new ConfigurationMap();
		 config.setShowNumbers(true);
		 config.setCubeLineWidth(1);
		 config.setKernelLineWidth(0.5);
		 config.setPyramidStrokeWidth(0.3);
		 config.setNumberSize(12);
		 config.setNumberFill("Red");
		 config.setCubeFill("Yellow");
		 config.setKernelFill("Aqua");
		 config.setPyramidFill("Aquamarine");
		 config.setInputColor("Green");
		 config.setDenseColor("Orange");
		 config.setCubeAlpha(0.3);
		 config.setKernelAlpha(0.7);
		 config.setPyramidAlpha(0.4);
		 config.setViewHeight(1500);
		 config.setViewWidth(1500);

		 //Set the configuration class
		 model.setConfiguration(config);
		 Renderer renderer= new Renderer(model);
		 renderer.build();
		 renderer.scale(1.5, 1.5, 1.5);
		 renderer.rotateX(-0.2);
		 renderer.rotateY(0.6);
		 renderer.rotateZ(0.0);
		 renderer.translate(1200, 750, 0);

		 //Print the model
		 renderer.printModel();
		 //Send the model to a file in the location specified
		 renderer.toFile("C:\\Users\\TheBrightKing\\Desktop\\fileSimpleLine.svg");
	  }
}
