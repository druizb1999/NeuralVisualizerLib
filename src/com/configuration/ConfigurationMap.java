package com.configuration;

import java.util.HashMap;

//Class containing all the configuration parameters
public class ConfigurationMap {
	
	//Black is a default color parameter so we declare it as a constant
	public static final String COLORBLACK = "#000000";
	
	//One hashmap per configuration type
	private HashMap<String, String> stringConfiguration;
	private HashMap<String, Integer> intConfiguration;
	private HashMap<String, Boolean> boolConfiguration;
	private HashMap<String, Double> doubleConfiguration;
	
	public ConfigurationMap() {
			this.boolConfiguration=new HashMap<>();
			this.intConfiguration=new HashMap<>();
			this.doubleConfiguration=new HashMap<>();
			this.stringConfiguration=new HashMap<>();

		
	    	setAltText(COLORBLACK);
	    	setCompactNumbers(true);
	    	setNumberSize(10);
	    	setFont("Verdana");
	    	setLogarithmicMultiplicator(10);
	    	setCubeAlpha(0.3);
	    	setPyramidAlpha(0.3);
	    	setKernelAlpha(0.3);
	    	setLogarithmicDistances(true);
	    	setDistanceBetweenLayers(50);
	    	setRandomKernelPos(true);
	    	setHeight(1000);
	    	setWidth(1000);
	    	setViewHeightini(0);
	    	setViewWidthini(0);
	        setViewHeight(1500);
	        setViewWidth(1500);
	    	setCubeFill("#EEEEEE");
	        setKernelFill("#99DDFF");
	        setPyramidFill("#FFBBBB");
	        setCubeLineWidth(0.3);
	        setPyramidStrokeWidth(0.3);
	        setKernelLineWidth(0.3);
	        setCompactNumbers(true);
	        setShowNumbers(true);
	        setNumberFill("#003300");
	        setDistanceBetweenSiameses(150);
	        setDistanceBetweenLevels(50);
	        setCubeColor(COLORBLACK);
	        setKernelColor(COLORBLACK);
	        setPyramidColor(COLORBLACK);
	    }
	
	public ConfigurationMap(ConfigurationMap c) {
		this.boolConfiguration= new HashMap<>();
		this.boolConfiguration.putAll(c.boolConfiguration);
		this.intConfiguration= new HashMap<>();
		this.intConfiguration.putAll(c.intConfiguration);
		this.doubleConfiguration= new HashMap<>();
		this.doubleConfiguration.putAll(c.doubleConfiguration);
		this.stringConfiguration= new HashMap<>();
		this.stringConfiguration.putAll(c.stringConfiguration);

    }
	
		//Getters and setters of all configurable parameters
	
	    public void setShowNumbers(boolean showNumbers) {
	        this.boolConfiguration.put("showNumbers", showNumbers);
	    }
	    
	    public void setAltText(String altText) {
	    	this.stringConfiguration.put("altText", altText);
	    }

	    public void setNumberFill(String numberFill) {
	    	this.stringConfiguration.put("numberFill",numberFill);
	    }
	    
	    public void setCompactNumbers(boolean format) {
	    	this.boolConfiguration.put("format",format);
	    }

	    public void setNumberSize(int numberSize) {
	    	this.intConfiguration.put("numberSize",numberSize);
	    }
	    
	    public void setDenseColor(String color) {
	    	this.stringConfiguration.put("denseColor",color);
	    }

	    public void setInputColor(String color) {
	    	this.stringConfiguration.put("inputColor",color);
	    }

	    public void setConvColor(String color) {
	    	this.stringConfiguration.put("convColor",color);
	    }

	    public void setFont(String font) {
	    	this.stringConfiguration.put("font",font);
	    }
	    
	    public void setLogarithmicMultiplicator(double logMult) {
	    	this.doubleConfiguration.put("logMult",logMult);
	    }

	    public void setCubeAlpha(double calpha) {
	    	this.doubleConfiguration.put("calpha",calpha);
	    }

	    public void setKernelAlpha(double kalpha) {
	    	this.doubleConfiguration.put("kalpha",kalpha);
	    }

	    public void setPyramidColor(String pcolor) {
	    	this.stringConfiguration.put("pcolor",pcolor);
	    }

	    public void setPyramidStrokeWidth(double psWidth) {
	    	this.doubleConfiguration.put("psWidth",psWidth);
	    }

	    public void setPyramidFill(String pfill) {
	    	this.stringConfiguration.put("pfill",pfill);
	    }

	    public void setPyramidAlpha(double palpha) {
	    	this.doubleConfiguration.put("palpha",palpha);
	    }

	    public void setRandomKernelPos(boolean rand){
	    	this.boolConfiguration.put("rand",rand);
	    }

	    public void setWidth(int width) {
	    	this.intConfiguration.put("width",width);
	    }

	    public void setHeight(int height) {
	    	this.intConfiguration.put("height",height);
	    }

	    public void setViewWidthini(int viewWidthini) {
	    	this.intConfiguration.put("viewWidthini",viewWidthini);
	    }

	    public void setViewHeightini(int viewHeightini) {
	    	this.intConfiguration.put("viewHeightini",viewHeightini);
	    }

	    public void setViewWidth(int viewWidth) {
	    	this.intConfiguration.put("viewWidth",viewWidth);
	    }

	    public void setViewHeight(int viewHeight) {
	    	this.intConfiguration.put("viewHeight",viewHeight);
	    }

	    public void setDistanceBetweenSiameses(double dist){
	    	this.doubleConfiguration.put("distW",dist);
	    }

	    public void setDistanceBetweenLevels(double dist){
	    	this.doubleConfiguration.put("distL",dist);
	    }

	    public void setDistanceBetweenLayers(double dist){
	    	this.doubleConfiguration.put("distG",dist);
	    }

	    public void setLogarithmicDistances(boolean log){
	    	this.boolConfiguration.put("log",log);
	    }

	    public void setCubeColor(String ccolor) {
	    	this.stringConfiguration.put("ccolor",ccolor);
	    }

	    public void setCubeLineWidth(double csWidth) {
	    	this.doubleConfiguration.put("csWidth",csWidth);
	    }

	    public void setCubeFill(String cfill) {
	    	this.stringConfiguration.put("cfill",cfill);
	    }

	    public void setKernelColor(String kcolor) {
	    	this.stringConfiguration.put("kcolor",kcolor);
	    }

	    public void setKernelLineWidth(double ksWidth) {
	    	this.doubleConfiguration.put("ksWidth",ksWidth);
	    }

	    public void setKernelFill(String kfill) {
	    	this.stringConfiguration.put("kfill",kfill);
	    }
	    
	    public void setPyramidAttributes(double height, double width) {
	    	this.doubleConfiguration.put("kHeight", height);
	    	this.doubleConfiguration.put("kWidth", width);
	    }
	    
	    public boolean getShowNumbers() {
	        return this.boolConfiguration.get("showNumbers");
	    }
	    
	    public String getAltText() {
	    	return this.stringConfiguration.get("altText");
	    }

	    public String getNumberFill() {
	    	return this.stringConfiguration.get("numberFill");
	    }
	    
	    public boolean getCompactNumbers() {
	    	return this.boolConfiguration.get("format");
	    }

	    public int getNumberSize() {
	    	return this.intConfiguration.get("numberSize");
	    }
	    
	    public String getDenseColor() {
	    	return this.stringConfiguration.get("denseColor");
	    }

	    public String getInputColor() {
	    	return this.stringConfiguration.get("inputColor");
	    }

	    public String getConvColor() {
	    	return this.stringConfiguration.get("convColor");
	    }

	    public String getFont() {
	    	return this.stringConfiguration.get("font");
	    }
	    
	    public double getLogarithmicMultiplicator() {
	    	return this.doubleConfiguration.get("logMult");
	    }

	    public double getCubeAlpha() {
	    	return this.doubleConfiguration.get("calpha");
	    }

	    public double getKernelAlpha() {
	    	return this.doubleConfiguration.get("kalpha");
	    }

	    public String getPyramidColor() {
	    	return this.stringConfiguration.get("pcolor");
	    }

	    public double getPyramidStrokeWidth() {
	    	return this.doubleConfiguration.get("psWidth");
	    }

	    public String getPyramidFill() {
	    	return this.stringConfiguration.get("pfill");
	    }

	    public double getPyramidAlpha() {
	    	return this.doubleConfiguration.get("palpha");
	    }

	    public boolean getRandomKernelPos(){
	    	return this.boolConfiguration.get("rand");
	    }

	    public int getWidth() {
	    	return this.intConfiguration.get("width");
	    }

	    public int getHeight() {
	    	return this.intConfiguration.get("height");
	    }

	    public int getViewWidthini() {
	    	return this.intConfiguration.get("viewWidthini");
	    }

	    public int getViewHeightini() {
	    	return this.intConfiguration.get("viewHeightini");
	    }

	    public int getViewWidth() {
	    	return this.intConfiguration.get("viewWidth");
	    }

	    public int getViewHeight() {
	    	return this.intConfiguration.get("viewHeight");
	    }

	    public double getDistanceBetweenSiameses(){
	    	return this.doubleConfiguration.get("distW");
	    }

	    public double getDistanceBetweenLevels(){
	    	return this.doubleConfiguration.get("distL");
	    }

	    public double getDistanceBetweenLayers(){
	    	return this.doubleConfiguration.get("distG");
	    }

	    public boolean getLogarithmicDistances(){
	    	return this.boolConfiguration.get("log");
	    }

	    public String getCubeColor() {
	    	return this.stringConfiguration.get("ccolor");
	    }

	    public double getCubeLineWidth() {
	    	return this.doubleConfiguration.get("csWidth");
	    }

	    public String getCubeFill() {
	    	return this.stringConfiguration.get("cfill");
	    }

	    public String getKernelColor() {
	    	return this.stringConfiguration.get("kcolor");
	    }

	    public double getKernelLineWidth() {
	    	return this.doubleConfiguration.get("ksWidth");
	    }

	    public String getKernelFill() {
	    	return this.stringConfiguration.get("kfill");
	    }
	    
	    public double getPyramidHeightNumber() {
	    	return this.doubleConfiguration.get("kHeight");
	    }
	    
	    public double getPyramidWidthNumber() {
	    	return this.doubleConfiguration.get("kWidth");
	    }
	    
	    public double getPyramidDepthNumber() {
	    	return this.doubleConfiguration.get("kDepth");
	    }
	    


}
