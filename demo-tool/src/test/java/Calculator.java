import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                continue;
            }
            try {
                double result = eval(input);
                System.out.printf("%.3f\n", result);
            } catch (IllegalArgumentException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    /**
     * 将表达式字符串解析为AST，并计算结果
     *
     * @param expression 表达式字符串
     * @return 计算结果
     * @throws IllegalArgumentException 如果表达式无法计算或存在非法字符
     */
    public static double eval(String expression) throws IllegalArgumentException {
        Tokenizer tokenizer = new Tokenizer(expression);
        ASTNode node = parseExpression(tokenizer);
        if (tokenizer.hasNext()) {
            throw new IllegalArgumentException("Unexpected token: " + tokenizer.next());
        }
        return node.eval();
    }

    /**
     * 将表达式字符串解析为AST表示，支持 +、-、*、/、() 运算
     *
     * @param tokenizer 分词器
     * @return AST表示
     * @throws IllegalArgumentException 如果表达式无法计算或存在非法字符
     */
    private static ASTNode parseExpression(Tokenizer tokenizer) throws IllegalArgumentException {
        ASTNode node = parseTerm(tokenizer);
        while (true) {
            if (tokenizer.hasNext("+")) {
                tokenizer.next();
                node = new AddNode(node, parseTerm(tokenizer));
            } else if (tokenizer.hasNext("-")) {
                tokenizer.next();
                node = new SubtractNode(node, parseTerm(tokenizer));
            } else {
                return node;
            }
        }
    }

    /**
     * 将表达式字符串解析为AST表示，支持 *、/ 运算
     *
     * @param tokenizer 分词器
     * @return AST表示
     * @throws IllegalArgumentException 如果表达式无法计算或存在非法字符
     */
    private static ASTNode parseTerm(Tokenizer tokenizer) throws IllegalArgumentException {
        ASTNode node = parseFactor(tokenizer);
        while (true) {
            if (tokenizer.hasNext("*")) {
                tokenizer.next();
                node = new MultiplyNode(node, parseFactor(tokenizer));
            } else if (tokenizer.hasNext("/")) {
                tokenizer.next();
                node = new DivideNode(node, parseFactor(tokenizer));
            } else {
                return node;
            }
        }
    }

    /**
     * 将表达式字符串解析为AST表示，支持数字和括号
     *
     * @param tokenizer 分词器
     * @return AST表示
     * @throws IllegalArgumentException 如果表达式无法计算或存在非法字符
     */
    private static ASTNode parseFactor(Tokenizer tokenizer) throws IllegalArgumentException {
        if (tokenizer.hasNext("(")) {
            tokenizer.next();
            ASTNode node = parseExpression(tokenizer);
            if (!tokenizer.hasNext(")")) {
                throw new IllegalArgumentException("Missing closing parenthesis");
            }
            tokenizer.next();
            return node;
        } else if (tokenizer.hasNextNumber()) {
            double value = tokenizer.nextNumber();
            return new NumberNode(value);
        } else {
            throw new IllegalArgumentException("Unexpected token: " + tokenizer.next());
        }
    }

    /**
     * 分词器，用于将表达式字符串拆分为标记
     */
    private static class Tokenizer {
        private final String input;
        private int position;

        public Tokenizer(String input) {
            this.input = input.trim();
            this.position = 0;
        }

        public boolean hasNext() {
            return position < input.length();
        }

        public boolean hasNext(String token) {
            return input.startsWith(token, position);
        }

        public boolean hasNextNumber() {
            return Character.isDigit(input.charAt(position)) || input.charAt(position) == '.';
        }

        public char next() {
            return input.charAt(position++);
        }

        public double nextNumber() {
            int start = position;
            while (position < input.length() && (Character.isDigit(input.charAt(position)) || input.charAt(position) == '.')) {
                position++;
            }
            return Double.parseDouble(input.substring(start, position));
        }
    }

    /**
     * 抽象语法树的节点
     */
    private static abstract class ASTNode {
        public abstract double eval();
    }

    /**
     * 数字节点
     */
    private static class NumberNode extends ASTNode {
        private final double value;

        public NumberNode(double value) {
            this.value = value;
        }

        @Override
        public double eval() {
            return value;
        }
    }

    /**
     * 操作符节点：加法
     */
    private static class AddNode extends ASTNode {
        private final ASTNode left;
        private final ASTNode right;

        public AddNode(ASTNode left, ASTNode right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public double eval() {
            return left.eval() + right.eval();
        }
    }

    /**
     * 操作符节点：减法
     */
    private static class SubtractNode extends ASTNode {
        private final ASTNode left;
        private final ASTNode right;

        public SubtractNode(ASTNode left, ASTNode right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public double eval() {
            return left.eval() - right.eval();
        }
    }

    /**
     * 操作符节点：乘法
     */
    private static class MultiplyNode extends ASTNode {
        private final ASTNode left;
        private final ASTNode right;

        public MultiplyNode(ASTNode left, ASTNode right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public double eval() {
            return left.eval() * right.eval();
        }
    }

    /**
     * 操作符节点：除法
     */
    private static class DivideNode extends ASTNode {
        private final ASTNode left;
        private final ASTNode right;

        public DivideNode(ASTNode left, ASTNode right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public double eval() {
            double denominator = right.eval();
            if (Math.abs(denominator) < 1e-6) {
                throw new IllegalArgumentException("Division by zero");
            }
            return left.eval() / denominator;
        }
    }
}