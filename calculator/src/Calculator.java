import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Stack;

// pt arborele de expresii
class TreeNode {
    String value;
    TreeNode left, right;

    public TreeNode(String value) {
        this.value = value;
        this.left = this.right = null;
    }
}

public class Calculator extends JFrame {
    JButton digits[] = {
            new JButton(" 0 "), new JButton(" 1 "), new JButton(" 2 "), new JButton(" 3 "), new JButton(" 4 "),
            new JButton(" 5 "), new JButton(" 6 "), new JButton(" 7 "), new JButton(" 8 "), new JButton(" 9 ")
    };

    JButton operators[] = {
            new JButton(" + "), new JButton(" - "), new JButton(" * "), new JButton(" / "),
            new JButton(" = "), new JButton(" C "), new JButton(" ( "), new JButton(" ) ")
    };

    String oper_values[] = {"+", "-", "*", "/", "=", "", "(", ")"};

    JTextArea area = new JTextArea(3, 5);

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        calculator.setSize(300, 300);
        calculator.setTitle("Calculator");
        calculator.setResizable(false);
        calculator.setVisible(true);
        calculator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public Calculator() {
        add(new JScrollPane(area), BorderLayout.NORTH);
        JPanel buttonpanel = new JPanel();
        buttonpanel.setLayout(new GridLayout(5, 4));

        for (int i = 0; i < 10; i++)
            buttonpanel.add(digits[i]);

        for (int i = 0; i < 8; i++)
            buttonpanel.add(operators[i]);

        add(buttonpanel, BorderLayout.CENTER);
        area.setForeground(Color.BLACK);
        area.setBackground(Color.WHITE);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setEditable(false);

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            digits[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    area.append(Integer.toString(finalI));
                }
            });
        }

        for (int i = 0; i < 8; i++) {
            int finalI = i;
            operators[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String text = area.getText();
                    if (finalI == 4) {  // "="
                        try {
                            String expression = area.getText();
                            double result = evaluateExpression(expression);
                            area.setText("= " + result);  // afiseaza rezultatul
                        } catch (Exception ex) {
                            area.setText("Probleme");
                        }
                    }
                    else if (finalI == 6) {  // "("
                        area.append("(");
                    }
                    else if (finalI == 7) {  // ")"
                        area.append(")");
                    }
                    else if (finalI == 5) {  // "C"
                        area.setText("");
                    }
                    else {
                        area.append(oper_values[finalI]);
                    }
                }
            });
        }
    }

    // fct care evalueaza expresia postfixata
    private double evaluateExpression(String expression) {
        // infix -> postfix
        String postfix = infixToPostfix(expression);

        // evaluare expresie postfix
        return evaluatePostfix(postfix);
    }

    // infix -> postfix
    private String infixToPostfix(String infix) {
        StringBuilder postfix = new StringBuilder();
        Stack<Character> operators = new Stack<>();
        StringBuilder currentNum = new StringBuilder();  // pt a construi numerele

        for (int i = 0; i < infix.length(); i++) {
            char c = infix.charAt(i);

            // numar -> il adaugam la currentNum
            if (Character.isDigit(c)) {
                currentNum.append(c);
            } else {
                // currentNum nu este gol -> adaugam numarul la postfix
                if (currentNum.length() > 0) {
                    postfix.append(currentNum.toString()).append(" ");  // spatiu pentru delimitare
                    currentNum.setLength(0);  // reset currentNum
                }

                // operator
                if (c == '+' || c == '-' || c == '*' || c == '/') {
                    while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(c)) {
                        postfix.append(operators.pop()).append(" ");
                    }
                    operators.push(c);
                }
                // paranteza deschisa
                else if (c == '(') {
                    operators.push(c);
                }
                // paranteza inchisa
                else if (c == ')') {
                    while (!operators.isEmpty() && operators.peek() != '(') {
                        postfix.append(operators.pop()).append(" ");
                    }
                    operators.pop();  // scot '(' din stiva
                }
            }
        }

        // mai exista un numar -> adaug la postfix
        if (currentNum.length() > 0) {
            postfix.append(currentNum.toString()).append(" ");
        }

        // scot toti operatorii ramasi din stiva
        while (!operators.isEmpty()) {
            postfix.append(operators.pop()).append(" ");
        }

        return postfix.toString();
    }

    // importanta operatorilor
    private int precedence(char operator) {
        if (operator == '+' || operator == '-') {
            return 1;
        } else if (operator == '*' || operator == '/') {
            return 2;
        }
        return -1;
    }

    // evaluare expresie postfixata
    private double evaluatePostfix(String postfix) {
        String[] tokens = postfix.split("\\s+");  // impart expr in tokenuri (numere si operatori)
        Stack<TreeNode> stack = new Stack<>();

        for (String token : tokens) {
            if (token.matches("\\d+")) {  // numar
                stack.push(new TreeNode(token));  // creez un nod cu val numarului
            }
            else if (token.matches("[+\\-*/]")) {  // operator
                TreeNode rightNode = stack.pop();  // nod drt
                TreeNode leftNode = stack.pop();  // nod stg
                TreeNode operatorNode = new TreeNode(token);  // creez un nod operator

                operatorNode.left = leftNode;  // leg nodul stang
                operatorNode.right = rightNode;  // leg nodul drept

                stack.push(operatorNode);  // adaug nodul operator la stiva
            }
        }

        // la final, un sg nod in stiva = rad
        TreeNode root = stack.pop();

        return evaluateTree(root);  // evaluare arbore pt a obtine rez
    }

    // fct recursiva pentru eval arborelui
    private double evaluateTree(TreeNode node) {
        if (node == null) {
            return 0;
        }

        // numar
        if (node.left == null && node.right == null) {
            return Double.parseDouble(node.value);
        }

        // eval subarborele stg si drt
        double leftValue = evaluateTree(node.left);
        double rightValue = evaluateTree(node.right);

        // aplica operatorul corespunzator
        switch (node.value) {
            case "+":
                return leftValue + rightValue;
            case "-":
                return leftValue - rightValue;
            case "*":
                return leftValue * rightValue;
            case "/":
                if (rightValue == 0) {
                    throw new ArithmeticException("Diviziune cu 0");
                }
                return leftValue / rightValue;
            default:
                throw new UnsupportedOperationException("Operator necunoscut: " + node.value);
        }
    }

}
