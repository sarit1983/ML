package com.iamtek;

import org.encog.Encog;
import org.encog.engine.network.activation.ActivationTANH;
import org.encog.ml.train.MLTrain;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.training.anneal.NeuralSimulatedAnnealing;
import org.encog.neural.pattern.FeedForwardPattern;

public class LunarLanderSA {

	public static BasicNetwork createNet(){
		FeedForwardPattern pattern = new FeedForwardPattern();
		pattern.setInputNeurons(3);
		pattern.setOutputNeurons(1);
		pattern.addHiddenLayer(50);
		pattern.setActivationFunction(new ActivationTANH());
		BasicNetwork network = (BasicNetwork) pattern.generate();
		network.reset();
		return network;
	}
	
	public static void main(String[] args) {
		BasicNetwork network = createNet();
		MLTrain train = new NeuralSimulatedAnnealing(network, new PilotScore(), 10, 2, 100);
		for (int i = 0; i < 50; i++) {
			train.iteration();
			System.out.println("Epoch #" + i + ", Error:" + train.getError());
		}
		train.finishTraining();
		System.out.println("\nHow the winning network landed:");
		network = (BasicNetwork) train.getMethod();
		NeuralPilot pilot = new NeuralPilot(network, true);
		System.out.println(pilot.scorePilot());
		Encog.getInstance().shutdown();
	}

}
