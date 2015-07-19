package chapter2;

import javax.swing.JButton;

/**
 * 匿名内部类/Lambda引用的局部变量必须是final?
 * @author skywalker
 *
 */
public class LocalVariable {
	
	public static void main(String[] args) {
		String name = "skywalker";
		//name = "str";
		JButton button = new JButton();
		button.addActionListener(event -> System.out.println(name));
	}
	
}
