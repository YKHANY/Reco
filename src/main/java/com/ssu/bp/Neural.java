// Neural Network Java classes
package com.ssu.bp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.StringTokenizer;

class Neural extends Object {

	protected int NumInputs;
	protected int NumHidden;
	protected int NumOutputs;

	protected int NumTraining;
	protected int WeightsFlag;
	protected int SpecialFlag;

	public float Inputs[];
	protected float Hidden[];
	public float Outputs[];

	protected float W1[][];
	protected float W2[][];

	protected float output_errors[];
	protected float hidden_errors[];

	protected float InputTraining[];
	protected float OutputTraining[];

	// mask of training examples to ignore (true -> ignore):
	public boolean IgnoreTraining[] = null;
	// mask of Input neurons to ignore:
	public boolean IgnoreInput[] = null;

	Neural() {
		NumInputs = NumHidden = NumOutputs = 0;
	}


	Neural(int i, int h, int o) {
		System.out.println("In BackProp constructor");
		Inputs = new float[i];
		Hidden = new float[h];
		Outputs = new float[o];
		W1 = new float[i][h];
		W2 = new float[h][o];
		NumInputs = i;
		NumHidden = h;
		NumOutputs = o;
		output_errors = new float[NumOutputs];
		hidden_errors = new float[NumHidden];

		// Randomize weights here:
		randomizeWeights();
	}
	
	void Load(String input_file){
		System.out.println("Entered ReadFile");
		
		try {
			File file = new File(input_file);
			BufferedReader in = new BufferedReader(new FileReader(file));
			
			StringTokenizer tokenizer = new StringTokenizer(in.readLine()," ");
			StringTokenizer tokenizer2 = new StringTokenizer(in.readLine()," ");
			int i=0, h=0, o=0;
			while(tokenizer.hasMoreElements()){
				String w1 = tokenizer.nextToken();
	
				W1[i][h] = Float.valueOf(w1);
		//		System.out.print(W1[i][h] + " ");
				
				h++;
				if(h % NumHidden == 0){
					i++; 
					h = 0;
				}
			}
			System.out.println("h to o");
			i=0; h=0;
			while(tokenizer2.hasMoreElements()){
				String w2 = tokenizer2.nextToken();
				
				W2[h][o] = Float.valueOf(w2);
		//		System.out.print(W2[h][o] + " ");
				
				o++;
				if(o % NumOutputs == 0){
					h++;
					o = 0;
				}
				
			}	
			in.close();
		//	System.out.println();
			System.out.println("Done loading input data file");
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
	}

	void Save(String output_file) {
		try{
			FileOutputStream f = new FileOutputStream(output_file);
			PrintStream ps = new PrintStream(f);
		//	 ps.println("\n# Input layer to hidden layer weights:\n");
			for (int i = 0; i < NumInputs; i++) {
				for (int h = 0; h < NumHidden; h++) {
					ps.print(W1[i][h] + " ");
				}
			}
			ps.println();
		//	ps.println("\n# Hidden layer to output layer weights:\n");
			for (int h = 0; h < NumHidden; h++) {
				for (int o = 0; o < NumOutputs; o++) {
					ps.print(W2[h][o] + " ");
				}
			}
			System.out.println("Done writing to output file.");
			ps.close();
			f.close();
			
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
	}

	public void randomizeWeights() {
		// Randomize weights here:
		for (int ii = 0; ii < NumInputs; ii++)
			for (int hh = 0; hh < NumHidden; hh++)
				W1[ii][hh] = 0.1f * (float) Math.random() - 0.05f;
		for (int hh = 0; hh < NumHidden; hh++)
			for (int oo = 0; oo < NumOutputs; oo++)
				W2[hh][oo] = 0.1f * (float) Math.random() - 0.05f;
	}

	public void ForwardPass() {
		int i, h, o;
		for (h = 0; h < NumHidden; h++) {
			Hidden[h] = 0.0f;
		}
		for (i = 0; i < NumInputs; i++) {
			for (h = 0; h < NumHidden; h++) {
				Hidden[h] += Inputs[i] * W1[i][h];
			}
		}
		for (o = 0; o < NumOutputs; o++)
			Outputs[o] = 0.0f;
		for (h = 0; h < NumHidden; h++) {
			for (o = 0; o < NumOutputs; o++) {
				Outputs[o] += Sigmoid(Hidden[h]) * W2[h][o];
			}
		}
		for (o = 0; o < NumOutputs; o++)
			Outputs[o] = Sigmoid(Outputs[o]);
	}

	public float Train() {
		return Train(InputTraining, OutputTraining, NumTraining);
	}

	public float Train(float ins[], float outs[], int num_cases) {
		int i, h, o;
		int in_count = 0, out_count = 0;
		float error = 0.0f;
		for (int example = 0; example < num_cases; example++) {
			if (IgnoreTraining != null)
				if (IgnoreTraining[example])
					continue; // skip this case
			// zero out error arrays:
			for (h = 0; h < NumHidden; h++)
				hidden_errors[h] = 0.0f;
			for (o = 0; o < NumOutputs; o++)
				output_errors[o] = 0.0f;
			// copy the input values:
			for (i = 0; i < NumInputs; i++) {
				Inputs[i] = ins[in_count++];
			}

			if (IgnoreInput != null) {
				for (int ii = 0; ii < NumInputs; ii++) {
					if (IgnoreInput[ii]) {
						for (int hh = 0; hh < NumHidden; hh++) {
							W1[ii][hh] = 0;
						}
					}
				}
			}

			// perform a forward pass through the network:

			ForwardPass();

			for (o = 0; o < NumOutputs; o++) {
				output_errors[o] = (outs[out_count++] - Outputs[o])
						* SigmoidP(Outputs[o]);
			}
			for (h = 0; h < NumHidden; h++) {
				hidden_errors[h] = 0.0f;
				for (o = 0; o < NumOutputs; o++) {
					hidden_errors[h] += output_errors[o] * W2[h][o];
				}
			}
			for (h = 0; h < NumHidden; h++) {
				hidden_errors[h] = hidden_errors[h] * SigmoidP(Hidden[h]);
			}
			// update the hidden to output weights:
			for (o = 0; o < NumOutputs; o++) {
				for (h = 0; h < NumHidden; h++) {
					W2[h][o] += 0.5 * output_errors[o] * Hidden[h];
				}
			}
			// update the input to hidden weights:
			for (h = 0; h < NumHidden; h++) {
				for (i = 0; i < NumInputs; i++) {
					W1[i][h] += 0.5 * hidden_errors[h] * Inputs[i];
				}
			}
			for (o = 0; o < NumOutputs; o++)
				error += Math.abs(output_errors[o]);
		}
		return error;
	}

	protected float Sigmoid(float x) {
		return (float) ((1.0f / (1.0f + Math.exp((double) (-x)))) - 0.5f);
	}

	protected float SigmoidP(float x) {
		double z = Sigmoid(x) + 0.5f;
		return (float) (z * (1.0f - z));
	}
	
	public void printW(){
		for(int i=0; i<NumInputs; i++){
			for(int h=0; h<NumHidden; h++){
				System.out.print(W1[i][h]);
			}
		}
		System.out.println();
		for(int h=0; h<NumHidden; h++){
			for(int o=0; o<NumOutputs; o++){
				System.out.print(W2[h][o]);
			}
		}
		System.out.println();
	}

}
