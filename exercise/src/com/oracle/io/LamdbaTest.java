package com.oracle.io;

import java.util.Arrays;
import java.util.Comparator;

public class LamdbaTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] players = { "Rafael Nadal", "Novak Djokovic",
				"Stanislas Wawrinka", "David Ferrer", "Roger Federer",
				"Andy Murray", "Tomas Berdych", "Juan Martin Del Potro",
				"Richard Gasquet", "John Isner" };

		// 1.1 使用匿名内部类根据 name 排序 players
		Arrays.sort(players, new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				return (s1.compareTo(s2));
			}
		});

		Arrays.sort(players, (String s1, String s2) -> (s1.substring(s1
				.indexOf(" ")).compareTo(s2.substring(s2.indexOf(" ")))));

	}

}
