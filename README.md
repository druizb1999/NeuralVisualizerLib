# NeuralVisualizerLib
This library is designed to represent convolutional neural networks in Alexnet Style. It is desgned to represent resnet networks, siamese networks and encoder-decoder networks with a great variety of aesthetic options.
# Setting up an example project
In order to set up an example project, clone the repository and navigate to the example folder. There you will find a main.java class with all the imports necessary for the library to run and the library in a jar file.

Open the command interpreter on that folder and compile the main file with the following command:

``` 
javac -cp NVisualizer.jar main.java
```

This will create a .class file in the same folder, this file is the one we will use to run the file. To run it the command is the following one:

``` 
java -cp "NVisualizer.jar;" Main
```

On linux you will need to do it without the ";":

``` 
java -cp "NVisualizer.jar" Main
```

The library will generate a SVG file on the folder we are working with. Additionaly, by changing the route of the file specified in the "toFile" command of the main.java file and recompiling and rerunning it you can change the destination folder of the SVG file.
# Commands
There are several commands available, we provide here a simple description of each one of them:

## Models and Layers
The user can declare several models and layers, for the model, nothing else needs to be specified, the syntax is:

``` 
Model model= new Model();
```
The user can declare three types of layers; Dense layers, Input layers and Conv2D layers. Both input and Conv2D layers take the following parameters:

**Width:** the width of the layer.\
**Height:** the height of the layer.\
**Depth:** the depth of the layer.\
**KernelWidth:** the width of the kernel.\
**KernelHeigh:** the height of the kernel.

Dense layers only take as parameter their height since they don't have a kernel.

The syntax for declaring an input layer would be:

``` 
Input input = new Input(224,224,3,11,11);
```

For a Conv2D layer:

``` 
Conv2D conv = new Conv2D(55,55,96,5,5);
```

For a Dense layer:

``` 
Dense dense = new Dense(2048);
```

## Add
The add command allows the user to add a layer to a model, layers can't be represented by themselves alone, they need to be in a model
The add command has the following syntax: 

``` 
model.add(layer);
```

Where "model" is the previously initialized model where the user adds a layer and layer is the layer added. The layer can be initialized in the add command, we can also add several layers to a model to create a small neural net:

``` 
model.add(new Input(224,224,3,11,11));
model.add(new Input(224,224,3,11,11));
model.add(new Input(224,224,3,11,11));
```

In order to visualize this, we need to create a Renderer using as parameter the model we want to visualize:

``` 
Renderer renderer= new Renderer(model);
```

We also need to use the build command on the renderer for it to build the 3D model, then we can specify the rotation on the X,Y and Z axis, the scale and translate it if needed by the following commands:

``` 
 renderer.build();
 renderer.scale(1.5, 1.5, 1.5);
 renderer.rotateX(-0.2);
 renderer.rotateY(0.6);
 renderer.rotateZ(0.0);
 renderer.translate(1200, 750, 0);
``` 

Then we generatethe SVG file specifying the file in which we want to save it:

``` 
renderer.toFile("fileSimpleLine.svg");
``` 

The result is the following:


## Join

The join commands allows the user to join several models into one layer, the join command takes two types of parameters:

**modelList:** a list containing the models to join to the main model.\
**layer:** the layer in which to join the main model and the models of the list.\

The join command has the following syntax:

``` 
model.join(modelList,layer);
``` 

For example, we can declare a new model in our main file and add several layers to it:

``` 
Model model2=new Model();
model2.add(new Input(224,224,3,11,11));
model2.add(new Conv2D(55,55,96,5,5));
model2.add(new Conv2D(27,27,256,3,3));
model2.add(new Conv2D(13,13,384,3,3));
model2.add(new Conv2D(13,13,384,3,3));
model2.add(new Conv2D(13,13,256,3,3));
``` 

Then we create a list containing this model and join it to the model we created previously:

``` 
List<Model> list= New LinkedList<>();
list.add(model2);
model.join(model2,new Conv2D(13,13,384,3,3));
``` 

This will make the model have 2 siamese layers the one in the list and the one of the model where we join the list, if the list had 2 models, there would be 3 siameses: the two in the list and the one of the model we join them in.

After this, we can keep adding layers to the model:

``` 
model.add(new Dense(2048));
model.add(new Dense(2048));
model.add(new Dense(1000));
``` 

If we run this code with the same parameters as before, the result is the following:


## Split

The split command allows the user to split a model into several models specified in a list.

Split only takes one parameter; the list of models to split the current model into, being its syntax the following:

``` 
model.split(modelList);
``` 

For the sake of example we can split the previous model into two different models after the join command, for this we declare both models, add layers to them and add them to a list, then we use the split command on the list:

``` 
Model model3=new Model();
model3.add(new Conv2D(55,55,96,5,5));
model3.add(new Conv2D(27,27,256,3,3));

Model model4=new Model();
model4.add(new Conv2D(55,55,96,5,5));
model4.add(new Conv2D(27,27,256,3,3));	

List<Model> listSplit= new LinkedList<>();
listSplit.add(model3);	
listSplit.add(model4);	
model.split(listSplit);

model.add(new Dense(2048));
model.add(new Dense(2048));
model.add(new Dense(1000));
``` 

If we afterwards add any layer to the resulting model with two siameses (like we do in the screenshot below and code above, adding dense layers afterwards) these layers will only be added to the first of the models we did split the model into, they won't be added to both models. For both models to have those layers we need to add them to each of the models before we split them and not add them afterwards.


if after this, we just want one line of dense layers we can use the join command without any linkedList parameter, this will join all the loose ends of the model into a specified layer, like this:

``` 
model.join(new Conv2D(13,13,384,3,3));

model.add(new Dense(2048));
model.add(new Dense(2048));
model.add(new Dense(1000));
``` 

Then we can add the dense layers we want and they will appear in a single lane. We also rotate X a bit more (-0.3 from -0.2) in order for it to be seen better, the result is the following:





