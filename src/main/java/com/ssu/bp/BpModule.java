// testCar.java
// NeuralNet Java classes: tester for Car Licence
package com.ssu.bp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;

import android.content.Context;
import android.util.Log;

/**
 * @author younghan
 *
 */
public class BpModule {
	Context mContext;

	Vector<Vector<char[]>> outNumber;


	final static int SIZE = 2500;

	final static int NUM = 2; //
	

	//Vector<Vector<char[]>> Chars = new Vector<>();

	Vector<char[]> input;

	int Inputs[] = new int[SIZE];
	
	int num_cap = 0;
	int active = 0; // if 1, then train network

	/**
	 * Neural network:
	 */
	Neural network;
	public BpModule(Context context){
		mContext = context;
	}

	// �ʱ�ȭ
	public void init() {
		
		outNumber = new Vector<Vector<char[]>>(); // ��� ����
		// ���ο� �Ű���� ���� ũ��� ����
		// SIZE : �Է� ��, 50: �������� ���� ��, NUM: �ν� ���� 3
		network = new Neural(SIZE, 50, NUM);
	}

	
	public void Load(String path){
		Log.i("BpModule","Load button pressed");
		network.Load(path);
	}

	
	public BpRes doRunButton(String path) {
		return PutChar(path); // test ����
	}

	//  �νİ�� ���
	public BpRes PutChar(String path) {
		// Special case:Mode==1 for testing:

		Vector<char[]> inputNumber = new Vector<char[]>();

	//	String path = "E:/CDP/image_code/" + choice2.getSelectedItem();
		File file = new File(path);
		BufferedReader bfr = null;
		try {
			bfr = new BufferedReader(new FileReader(file));
			while (true) {
				String str = bfr.readLine();
				if (str == null)
					break;
				inputNumber.add(str.toCharArray());
			}
			bfr.close();
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		int ic = 0, idx = 0;
		Enumeration<char[]> enumer = inputNumber.elements();
		while (enumer.hasMoreElements()) {
			char[] t = enumer.nextElement();
			// for (int x = 0; x < 100; x++) {
			for (int x = 0; x < 100; x += 2) {
				// if (x > 0 && x < 99) {
				if (idx % 2 == 0) {
					if (t[x] == '1') {
						network.Inputs[ic++] = +0.4f;
						// System.out.print("!");
					} else {
						network.Inputs[ic++] = -0.4f;
						// System.out.print("@");
					}
				}
			}
			idx++;
		}

		// �Է� ������ ������ �������� ���ؼ� ��� �������� ���
		network.ForwardPass();

		// ��� ������ �� �� �ִ� ���� ã��
		int index = 0;
		float maxVal = -99f;
		for (int j = 0; j < NUM; j++) {
			// P("forward: " + Float.toString(network.Outputs[j]) + "\n");
			if (network.Outputs[j] > maxVal) {
				maxVal = network.Outputs[j];
				index = j;
			}
		}


	/*	if (maxVal < 0.0) {
			Toast.makeText(mContext, "Character recognized. But Maybe not correct: " + index + "  : "+ Float.toString(maxVal),Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(mContext, "Character recognized: " + index + "  : "+ Float.toString(maxVal),Toast.LENGTH_SHORT).show();
		}
	*/
		BpRes bpRes = new BpRes(index, maxVal);
		return bpRes;
	}
}
