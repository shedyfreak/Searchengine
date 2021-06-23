module java.controller {
	exports bfh.ch.controller;
	exports bfh.ch.entities;
	exports bfh.ch.model;
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.media;
	requires javafx.graphics;
	requires com.fasterxml.jackson.databind;
	opens bfh.ch.entities;
	requires com.fasterxml.jackson.core;
	requires com.fasterxml.jackson.annotation;
	requires java.net.http;
	opens bfh.ch.model to com.sun.javafx;
	opens bfh.ch.controller to com.fasterxml.jackson.databind;
}




