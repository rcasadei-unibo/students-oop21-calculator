/*
 * Copyright 2014 Frank Asseg
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
//took inspiration from 
//https://github.com/fasseg/exp4j/tree/master/src/main/java/net/objecthunter/exp4j/function
package utils.calculate;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import model.calculators.ScientificCalculatorModelFactory;

/**
 * It is used to get all the allowed functions.
 *
 */
public class Function {	
	private String name;
	private int numArgs;
	
	/**
     * 
     */
    public static final Map<String, Function> DICTFUNCTIONS = getFunctions().stream()
            .collect(Collectors.toMap(s -> s, s -> "pow".equals(s) ? new Function(s, 2) : new Function(s)));
	
	
	/**
	 * @return All functions
	 */
	public static Set<String> getFunctions() {
	   final var set = new HashSet<>(ScientificCalculatorModelFactory.create()
	           .getUnaryOpMap().keySet());
	           set.addAll(Set.of("abs", "acos", "asin", "atan", "cos",
	                   "exp", "log", "negate", "pow", "sin", "√", "sqrt", "tan", "csc", "cot", "sec", "root"));
	   return set;
	}
	
	/**
	 * @param name
	 * @param numArgs
	 */
	public Function(final String name, final int numArgs) {
		if (name.length() == 0 || numArgs < 0 || !getFunctions().contains(name)) {
			throw new IllegalArgumentException();
		}

		this.name = name;
		this.numArgs = numArgs;
	}
	
	/**
	 * @param name
	 */
	public Function(final String name) {
		this(name, 1);
	}
	
	/**
	 * @return the name of he function
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * @return number of arguments required
	 */
	public int getNumArgs() {
		return this.numArgs;
	}
	
	/**
	 * @param name
	 * @return if he function exists
	 */
	public static boolean isFunction(final String name) {
		return getFunctions().contains(name);
	}
}
