import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Stack;


class Node { // pt arborele de expresii
    String value;
    Node left, right;

    public Node(String value) {
        this.value = value;
        this.left = this.right = null;
    }
}

public class Calculator extends JFrame {

    JButton digits[] = { // 10 cifre
            new JButton(" 0 "), new JButton(" 1 "), new JButton(" 2 "), new JButton(" 3 "), new JButton(" 4 "),
            new JButton(" 5 "), new JButton(" 6 "), new JButton(" 7 "), new JButton(" 8 "), new JButton(" 9 ")
    };


    JButton operators[] = { // 8 operatori
            new JButton(" + "), new JButton(" - "), new JButton(" * "), new JButton(" / "),
            new JButton(" = "), new JButton(" C "), new JButton(" ( "), new JButton(" ) ")
    };

    String oper_values[] = {"+", "-", "*", "/", "=", "", "(", ")"};

    JTextArea area = new JTextArea(4, 5); // folosit pt afișarea expresiilor introduse și a rezultatelor

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

        // fiecare buton are un ActionListener asociat care adaugă cifra sau operatorul corespunzător în JTextArea
        for (int i = 0; i < 10; i++) { // 10 cifre
            int finalI = i;
            digits[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    area.append(Integer.toString(finalI));
                }
            });
        }
        for (int i = 0; i < 8; i++) { // 8 operatori
            int finalI = i;
            operators[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String text = area.getText();
                    if (finalI == 4) {  // "=" evalueaza expresia
                        try {
                            String expression = area.getText();
                            double result = evalExpr(expression);    // fct pt evaluarea expresiei postfixate
                            area.setText("= " + result);  // afiseaza rezultatul
                        } catch (Exception ex) {
                            area.setText("Probleme"); // nu suporta numere negative direct in expresii si nu gestioneaza erori de sintaxa
                        }
                    }
                    else if (finalI == 5) {  // "C"
                        area.setText(""); // sterge continutul
                    }
                    else if (finalI == 6) {  // "("
                        area.append("(");
                    }
                    else if (finalI == 7) {  // ")"
                        area.append(")");
                    }
                    else {
                        area.append(oper_values[finalI]); // adauga operatorul
                    }
                }
            });
        }
    }

    private double evalExpr(String expr) {
        String postfix = inToPost(expr); // fct care face conversia unei expresii infixate in una postfixata, deoarece e mai usor sa evaluez o expresie postfixata

        return evalPostFix(postfix);    // fct care evalueaza expresie postfix
    }

    private String inToPost(String infix) {
        StringBuilder postfix = new StringBuilder();    // construieste expresia postfix
        Stack<Character> operators = new Stack<>();    // stiva pentru operatori si paranteze
        StringBuilder num = new StringBuilder();  // construieste numerele cu mai multe cifre

        for (int i = 0; i < infix.length(); i++) {
            char c = infix.charAt(i);

            if (Character.isDigit(c)) { // daca e cifra -> il adaug la num
                num.append(c);
            } 
            else {
                if (num.length() > 0) { // num nu este gol -> adaug numarul din num la expresia postfix
                    postfix.append(num.toString()).append(" ");  // spatiu pentru delimitare
                    num.setLength(0);  // reset num pt viitoarele numere
                }

                if (c == '+' || c == '-' || c == '*' || c == '/') { // operator
                    while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(c)) { // scot operatorii cu prioritate mai mare sau egala din stiva si le adaug la expresia postfix
                        postfix.append(operators.pop()).append(" ");
                    }
                    operators.push(c);    // adaug operatorul curent in stiva
                }
                    else if (c == '(') {  // paranteza deschisa
                        operators.push(c);
                    }
                        else if (c == ')') {  // paranteza inchisa
                            while (!operators.isEmpty() && operators.peek() != '(') { // scot toti operatorii pana la paranteza deschisa
                                        postfix.append(operators.pop()).append(" ");
                                    }
                            operators.pop();  // scot '(' din stiva
                        }
            }
        }

        if (num.length() > 0) { // mai exista un numar -> adaug la expresia postfix
            postfix.append(num.toString()).append(" ");
        }

        while (!operators.isEmpty()) {  // scot toti operatorii ramasi din stiva
            postfix.append(operators.pop()).append(" ");
        }

        return postfix.toString();
    }

    private int precedence(char operator) { // importanta operatorilor
        if (operator == '+' || operator == '-') {
            return 1;
        } else if (operator == '*' || operator == '/') {
            return 2;
        }
        return -1;
    }


    private double evalPostFix(String postfix) { // evaluare expresie postfixata
        String[] tokens = postfix.split("\\s+");  // se imparte expresia in tokenuri (numere si operatori), folosind split("\\s+"), care separa expresia după spatii
        Stack<Node> stack = new Stack<>(); // stiva pentru a construi arborele de expresii

        for (String token : tokens) { // for each
            if (token.matches("\\d+")) {  // numar
                stack.push(new Node(token));  // creez un nod cu val numarului in stiva
            }
            else if (token.matches("[+\\-*/]")) {  // operator
                Node rightNode = stack.pop();  // scot nod drt, al doilea operand
                Node leftNode = stack.pop();  // scot nod stg, primul operand
                Node operatorNode = new Node(token);  // creez un nod operator

                // leg nodurile stânga și dreapta de nodul operator
                operatorNode.left = leftNode;
                operatorNode.right = rightNode;

                stack.push(operatorNode);  // adaug nodul operator la stiva
            }
        }

        Node root = stack.pop(); // la final, stiva contine un singur nod: radacina arborelui de expresii

        return evalTree(root);  // functie care evalueaza arborele de expresii pt a obtine rez
    }

    private double evalTree(Node node) { // fct recursiva pentru eval arborelui
        if (node == null) {
            return 0; // caz de baza: nod null
        }

        if (node.left == null && node.right == null) { // numar
            return Double.parseDouble(node.value); // returneaza valoarea numerică
        }

        // eval subarborele stg si drt
        double leftValue = evalTree(node.left);
        double rightValue = evalTree(node.right);

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
                    throw new ArithmeticException("Impartire la 0");
                }
                return leftValue / rightValue;
            default:
                throw new UnsupportedOperationException("Operator necunoscut: " + node.value);
        }
    }

}
