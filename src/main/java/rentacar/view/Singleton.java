package rentacar.view;

import rentacar.Operator;

public class Singleton {
	
    private static Singleton instance = new Singleton();
    private Operator logedOperator;
    
	public static Singleton getInstance() {
		return instance;
	}
	public static void setInstance(Singleton instance) {
		Singleton.instance = instance;
	}
	public Operator getLogedOperator() {
		return logedOperator;
	}
	public void setLogedOperator(Operator logedOperator) {
		this.logedOperator = logedOperator;
	}

    
    


}