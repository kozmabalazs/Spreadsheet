package com.gmail.kozmazbalazs;

public enum Operator {
	ADD {
		public int calculate(int a, int b) {
			return a + b;
		}
	},
	SUBTRACT {
		public int calculate(int a, int b) {
			return a - b;
		}
	},
	MULTIPLY {
		public int calculate(int a, int b) {
			return a * b;
		}
	},
	DIVIDE {
		public int calculate(int a, int b) {
			if (b == 0)
				return 0;
			else
				return a / b;
		}
	};

	public abstract int calculate(int a, int b);
}
