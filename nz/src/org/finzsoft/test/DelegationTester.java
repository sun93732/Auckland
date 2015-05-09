package org.finzsoft.test;

import java.util.ArrayList;
import java.util.List;

class RealPrinter {
	public List<Integer> print() { // the "delegate"
		List<Integer> ls = new ArrayList<Integer>();
		for (int i = 0; i < 10; i++) {
			ls.add(Integer.valueOf(i));
		}
		return ls;
	}
}

class Printer { // the "delegator"

	RealPrinter p = new RealPrinter();

	void print() {
		p.print();
	}
}

public class DelegationTester {
	// to the outside world it looks like Printer actually prints.
	public void main(String[] args) {
		Printer printer = new Printer();
		printer.print();
	}
}